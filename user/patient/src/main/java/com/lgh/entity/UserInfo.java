package com.lgh.entity;

import lombok.Data;

/**
 * @author 李广辉
 * @date 2023/3/25 15:32
 */

@Data
public class UserInfo {

    String userid;
    String username;
    String password;
    String email;
    String phone;
    String age;
    Integer state;
    String sex;

}
