package com.lgh.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 李广辉
 * @date 2023/3/4 13:08
 */

@Data
public class DoctorInfo {

    private String doctorId;
    private String doctorImg;
    private String doctorName;
    private Integer sex;
    private Date birthday;
    private String hospital;
    private String department;
    private Integer authentication;
    private String post;

}
