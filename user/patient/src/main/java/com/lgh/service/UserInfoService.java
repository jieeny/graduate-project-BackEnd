package com.lgh.service;

/**
 * @author 李广辉
 * @date 2023/3/25 15:50
 */
public interface UserInfoService {

    /**
     * 通过用户id获取用户名
     * @param id
     * @return
     */
    public String  getUsernameByUserId(String id);

}
