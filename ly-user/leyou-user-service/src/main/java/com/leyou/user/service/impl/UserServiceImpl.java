package com.leyou.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.pojo.UserQueryByPageParameter;
import com.leyou.common.vo.PageResult;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @description:
 * @author: furong
 * @date: 2019/5/7 15:42
 * @Version: 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
      * @Description 登录
      * @Param [username, password]
      * @return com.leyou.user.pojo.User
      **/
    @Override
    public User login(String username, String password) {
        User record = new User();
        record.setUsername(username);
        User user = userMapper.selectOne(record);
        if(user == null){
            throw new LyException(ExceptionEnum.USER_NOT_FOUND);
        }
        return user;
    }

    /**
      * @Description 分页查询
      * @Param [userQueryByPageParameter]
      * @return com.leyou.common.vo.PageResult<com.leyou.user.pojo.User>
      **/
    @Override
    public PageResult<User> queryUserByPage(UserQueryByPageParameter userQueryByPageParameter) {
        //分页
        PageHelper.startPage(userQueryByPageParameter.getPage(),userQueryByPageParameter.getRows());
        Example example = new Example(User.class);
        //排序
        if(StringUtils.isNotBlank(userQueryByPageParameter.getSortBy())){
            String orderByClause = userQueryByPageParameter.getSortBy() + (userQueryByPageParameter.getDesc() ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<User> list = userMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.USER_NOT_FOUND);
        }
        //解析分页结果
        PageInfo<User> info = new PageInfo<>(list);
        return new PageResult<>(info.getTotal(), list);
    }

    /**
      * @Description 添加用户
      * @Param [user]
      * @return void
      **/
    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }
}
