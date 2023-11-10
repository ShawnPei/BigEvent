package org.shawn.controller;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.shawn.pojo.Result;
import org.shawn.pojo.User;
import org.shawn.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    @PostMapping("/register")
    public Result register(String username, String password) {
        //查询用户
        User user = userService.findByUserName(username);
        if(user == null) {
            //注册
            userService.register(username,password);
            return Result.success(user);
        }else {
            return Result.error("用户名已被占用");
        }

    }
}
