package com.lgh.mapper;

import com.lgh.entity.Doctor;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 李广辉
 * @date 2023/3/4 20:33
 */


@Mapper
public interface DoctorMapper {

    //查询医生
    Doctor queryDoctorByDoctorName(String doctorName);

    //插入一条医生
    int insertDoctor(Doctor doctor);

}
