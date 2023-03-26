package com.lgh.service;

import com.lgh.entity.DoctorInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author 李广辉
 * @date 2023/3/4 15:03
 */
public interface DoctorService {
    DoctorInfo queryDoctorInfo(String doctorId);

    Integer queryDoctorState(String doctorId);

    Integer updateDoctorState(@Param("doctorId")String doctorId, @Param("state")Integer state);
}
