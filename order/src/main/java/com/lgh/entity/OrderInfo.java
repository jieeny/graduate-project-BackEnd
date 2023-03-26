package com.lgh.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 李广辉
 * @date 2023/3/5 16:38
 */

@Data
public class OrderInfo {

    private String orderId;
    private long orderTime;
    private String departmentName;
    private String doctorId;
    private String doctorName;
    private String username;        //下单者姓名
    private String userId;          //下单者编号
//    private String patientId;
    private String patientName;
    private String patientAge;
    private String patientSex;
    private String description;
    private String allergies;
    private Integer orderState;          //订单的状态,0表示待接诊,1表示完成接诊

}
