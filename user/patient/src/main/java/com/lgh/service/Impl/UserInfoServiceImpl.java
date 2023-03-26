package com.lgh.service.Impl;

import com.lgh.mapper.PatientMapper;
import com.lgh.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 李广辉
 * @date 2023/3/25 15:50
 */

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private PatientMapper patientMapper;

    @Override
    public String getUsernameByUserId(String id) {
        return patientMapper.queryUsernameByUserId(id);
    }
}
