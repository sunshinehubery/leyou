package com.leyou.user.web;
import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: furong
 * @date: 2019/5/7 15:51
 * @Version: 1.0
 **/
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public ResponseEntity<User> login(@RequestParam(value = "username") String username,@RequestParam(value = "password") String password){
        return ResponseEntity.ok(userService.login(username,password));
    }

}
