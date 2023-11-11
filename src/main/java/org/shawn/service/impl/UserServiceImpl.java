package org.shawn.service.impl;

import lombok.RequiredArgsConstructor;
import org.shawn.mapper.UserMapper;
import org.shawn.pojo.User;
import org.shawn.service.UserService;
import org.shawn.utils.Md5Util;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        User user = userMapper.findByUserName(username);
        return user;
    }

    @Override
    public void register(String username, String password) {
        //加密密码
        String encryptedPwd = Md5Util.getMD5String(password);
        //添加用户
        userMapper.add(username,encryptedPwd);

    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }
}
