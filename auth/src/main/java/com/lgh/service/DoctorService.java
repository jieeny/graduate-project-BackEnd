package com.lgh.service;

import com.lgh.entity.Doctor;

/**
 * @author 李广辉
 * @date 2023/3/4 20:35
 */



public interface DoctorService {

    Doctor queryDoctorByDoctorName(String doctorName);

    Integer insertDoctor(Doctor doctor);

}
