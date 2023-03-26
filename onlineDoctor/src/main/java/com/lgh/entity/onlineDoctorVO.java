package com.lgh.entity;

import lombok.Data;

/**
 * 将医生信息传输到患者端
 * @author 李广辉
 * @date 2023/2/21 22:37
 */

@Data
public class onlineDoctorVO {
    private String type;
    private String doctorId;
    private String doctorName;
    private String post;
    private String department;
    private String hospital;
    private int number;
}
