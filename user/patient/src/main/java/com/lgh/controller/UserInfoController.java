package com.lgh.controller;

import com.lgh.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李广辉
 * @date 2023/3/25 15:48
 */

@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("getUsername")
    public String getUsernameByUserId(String id){
        String username = userInfoService.getUsernameByUserId(id);
        return username;
    }

}
