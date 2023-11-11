package org.shawn.service;

import lombok.RequiredArgsConstructor;
import org.shawn.mapper.UserMapper;
import org.shawn.pojo.User;


public interface UserService {

    //
    User findByUserName(String username);

    void register(String username, String password);

    void update(User user);
}
