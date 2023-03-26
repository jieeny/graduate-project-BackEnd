package com.lgh.entity;

import lombok.Data;

/**
 * @author 李广辉
 * @date 2023/3/26 22:51
 */

@Data
public class UpdateOrderInfo {
    private String orderId;
    private String age;
    private String allergies;
    private String description;
    private String name;
    private String sex;
}
