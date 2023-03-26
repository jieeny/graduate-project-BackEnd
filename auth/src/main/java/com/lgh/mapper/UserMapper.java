package com.lgh.mapper;

import com.lgh.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 李广辉
 * @date 2023/1/7 16:35
 */

@Mapper
public interface UserMapper {
    //新增用户

    //更新用户信息

    //删除用户

    //判断用户是否存在
    User selectUserByUserName(String username);

    //验证用户名和密码
    User selectUserByUserNameAndPassword(String username,String password);

    //新增用户
    int insertUser(User user);
}
