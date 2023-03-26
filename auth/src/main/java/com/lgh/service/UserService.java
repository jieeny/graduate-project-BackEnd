package com.lgh.service;

import com.lgh.entity.User;
import com.lgh.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 李广辉
 * @date 2023/1/7 16:35
 */


public interface UserService {

    User selectUserByUserName(String username);

    User selectUserByUserNameAndPassword(String username,String password);

    int insertUser(User user);

}
