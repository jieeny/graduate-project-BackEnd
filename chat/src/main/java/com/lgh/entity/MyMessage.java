package com.lgh.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 李广辉
 * @date 2023/2/10 21:37
 */
@Data
public class MyMessage implements Serializable {
    private static final long serialVersionUID=1L;
    private String userId;
    private String message;     //消息内容
    private String messageType;     //消息类型,1-代表单聊,2-代表群聊
}
