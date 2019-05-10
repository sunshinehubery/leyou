package com.leyou.user.mapper;

import com.leyou.user.pojo.User;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    @Select("select * from tb_user where username=#{username} and password=#{password}")
    User login(String username,String password);
}
