package com.lgh.service.Impl;

import com.lgh.entity.User;
import com.lgh.mapper.UserMapper;
import com.lgh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 李广辉
 * @date 2023/1/7 16:36
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User selectUserByUserName(String username) {
        User user = userMapper.selectUserByUserName(username);
        return user;
    }

    public User selectUserByUserNameAndPassword(String username, String password) {
        User user = userMapper.selectUserByUserNameAndPassword(username, password);
        return user;
    }

    @Override
    public int insertUser(User user) {
        int i = userMapper.insertUser(user);
        return i;
    }

}
