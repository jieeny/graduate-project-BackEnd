package com.lgh.service;

/**
 * @author 李广辉
 * @date 2023/3/5 16:26
 */
public interface patientService {

    //查询当前待接诊的患者数量
    Integer patientNumber(String doctorId);
    //查询当前待接诊的患者列表


    //查询已经接诊的患者数量

}
