package com.leyou.user.service;

import com.leyou.common.pojo.UserQueryByPageParameter;
import com.leyou.common.vo.PageResult;
import com.leyou.user.pojo.User;

public interface UserService {
    /**
      * @Description 登录
      * @Param [username, password]
      * @return com.leyou.user.pojo.User
      **/
    User login(String username, String password);

    /**
      * @Description 分页
      * @Param [userQueryByPageParameter]
      * @return com.leyou.common.vo.PageResult<com.leyou.user.pojo.User>
      **/
    PageResult<User> queryUserByPage(UserQueryByPageParameter userQueryByPageParameter);

    /**
      * @Description 添加user
      * @Param [user]
      * @return void
      **/
    void addUser(User user);
}
