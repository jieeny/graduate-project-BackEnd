package com.lgh.service;

import com.lgh.entity.onlineDoctorInfo;
import com.lgh.entity.onlineDoctorVO;
import org.apache.ibatis.annotations.Param;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author 李广辉
 * @date 2023/2/20 19:52
 */
public interface onlineDoctorService {

    /**
     * 将上线的医生加入到MongoDB中,上线
     * @param doctorInfo
     * @return
     */
    public boolean insertDoctorToMongoDB(onlineDoctorInfo doctorInfo) throws ParseException;

    public Boolean doctorOnline(String doctorId) throws Exception;

    /**
     * 将一名医生从MongoDB中移除,下线
     * @param doctorId
     * @return
     */
    public boolean deleteOnlineDoctorFromMongoDB(String doctorId);

    /**
     * 根据科室查询医生列表
     * @param department
     * @return
     */
    public List<onlineDoctorVO> queryOnlineDoctorByDepartment(String department);



}
