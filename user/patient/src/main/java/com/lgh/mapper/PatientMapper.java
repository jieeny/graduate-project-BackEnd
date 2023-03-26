package com.lgh.mapper;

import com.lgh.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 李广辉
 * @date 2023/3/25 15:26
 */

@Mapper
public interface PatientMapper {

    //根据用户id查询用户信息
    String queryUsernameByUserId(String id);

    //根据用户名查询用户信息


}
