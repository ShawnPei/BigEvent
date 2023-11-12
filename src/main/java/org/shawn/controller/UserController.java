package org.shawn.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;
import org.shawn.pojo.Result;
import org.shawn.pojo.User;
import org.shawn.service.UserService;
import org.shawn.utils.JwtUtil;
import org.shawn.utils.Md5Util;
import org.shawn.utils.ThreadLocalUtil;

import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@Api(tags = "用户相关接口")
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    @ApiOperation("用户注册接口")
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

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @ApiOperation("用户登录接口")
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

    /**
     * 用户查询
     * @return
     */
    @ApiOperation("用户查询接口")
    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        //根据用户名查询用户
        /**
         * 接口文档中，这个接口无方法参数，但是所有的请求都会携带token，而我们在token中存放了用户id和姓名
         * 可以使用token中的信息，来查询用户
         */
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        //根据用户名查询用户
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    /**
     * 用户更新信息
     * @param user
     * @return
     */
    @ApiOperation("用户更新信息接口")
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    /**
     * 用户更新头像
     * @param avatarUrl
     * @return
     */
    @ApiOperation("用户更新头像接口")
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    /**
     * 用户更新密码
     * @param map
     * @return
     */
    @ApiOperation("用户更新密码接口")
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> map) {
        //参数校验
        String oldPwd = map.get("old_pwd");//用户输入的，未加密
        String newPwd = map.get("new_pwd");
        String rePwd = map.get("re_pwd");

        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必备信息");
        }

        //从Threadlocal中获取用户信息，再获取用户名。
        Map<String,Object> info = ThreadLocalUtil.get();
        String username = (String) info.get("username");
        //根据用户名查询用户
        User user = userService.findByUserName(username);
        String password = user.getPassword();
        if (!Md5Util.checkPassword(map.get("old_pwd"),password)) {
            return Result.error("旧密码不正确");
        }

        if(!newPwd.equals(rePwd)) {
            return Result.error("输入的新密码不一致");
        }
        userService.updatePwd(newPwd);
        return Result.success();
    }

}
