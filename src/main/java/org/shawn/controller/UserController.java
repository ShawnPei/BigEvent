package org.shawn.controller;

import jakarta.annotation.Resource;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.shawn.pojo.Result;
import org.shawn.pojo.User;
import org.shawn.service.UserService;
import org.shawn.utils.JwtUtil;
import org.shawn.utils.Md5Util;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$")  String password) {
        //参数校验
        //查询用户
        User user = userService.findByUserName(username);
        if(user == null) {
            //注册
            userService.register(username,password);
            return Result.success();
        }else {
            return Result.error("用户名已被占用");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$")  String password) {
        //查询用户是否存在
        User user = userService.findByUserName(username);
        //如果存在
        if(user == null) {
            return Result.error("用户不存在");
        }
        String md5String = user.getPassword();
        if (Md5Util.checkPassword(password,md5String)) {
            //登陆成功
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("id",user.getId());
            claims.put("username",user.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("用户密码不正确");

    }
}
