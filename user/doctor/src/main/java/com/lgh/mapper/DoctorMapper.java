package com.lgh.mapper;

import com.lgh.entity.DoctorInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 李广辉
 * @date 2023/3/4 13:14
 */

@Mapper
public interface DoctorMapper {

    //查询医生的个人信息
    DoctorInfo queryDoctorInfo(String doctorId);

    //查询当前医生的状态
    Integer queryDoctorState(String doctorId);


    //修改当前医生的状态
    Integer updateDoctorState(@Param("doctorId")String doctorId, @Param("state")Integer state);


}
