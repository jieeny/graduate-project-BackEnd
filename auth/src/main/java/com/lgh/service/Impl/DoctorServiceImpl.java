package com.lgh.service.Impl;

/**
 * @author 李广辉
 * @date 2023/3/4 20:35
 */

import com.lgh.entity.Doctor;
import com.lgh.mapper.DoctorMapper;
import com.lgh.service.DoctorService;
import com.mongodb.DBObjectCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public Doctor queryDoctorByDoctorName(String doctorName) {

        Doctor doctor = doctorMapper.queryDoctorByDoctorName(doctorName);
        return doctor;
    }

    @Override
    public Integer insertDoctor(Doctor doctor) {
        int i = doctorMapper.insertDoctor(doctor);
        return i;
    }
}
