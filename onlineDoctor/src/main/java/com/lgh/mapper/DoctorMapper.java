package com.lgh.mapper;

import com.lgh.entity.onlineDoctorInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 李广辉
 * @date 2023/3/26 17:14
 */

@Mapper
public interface DoctorMapper {

    /**
     * 根据doctorId查询医生信息
     * @param doctorId
     * @return
     */
    onlineDoctorInfo getOnlineDoctorInfo(String doctorId);

    /**
     * 查询所有的医生的医生信息
     * @return
     */
    List<onlineDoctorInfo> getOnlineDoctorInfoList();

    //查询当前医生的状态
    Integer queryDoctorState(String doctorId);


    //修改当前医生的状态
    Integer updateDoctorState(@Param("doctorId")String doctorId, @Param("state")Integer state);

}
