package com.lgh.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 李广辉
 * @date 2023/3/5 16:28
 */

@Data
public class PatientInfo {

    private String orderId;         //挂号(订单)编号
    private String patientName;
    private Date orderTime;
    private String age;
    private String sex;

}
