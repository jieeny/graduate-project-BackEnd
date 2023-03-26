package com.lgh.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author 李广辉
 * @date 2023/2/20 19:49
 */

@Data
@Document(collection = "OnlineDoctors")
public class onlineDoctorInfo {

//    @Id
//    private String id;
    private String doctorId;
    private String doctorName;
    private String post;
    private String department;
    private String hospital;

}
