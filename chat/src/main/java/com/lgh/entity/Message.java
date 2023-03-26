package com.lgh.entity;

import lombok.Data;
import org.apache.catalina.Service;

import java.io.Serializable;

/**
 * @author 李广辉
 * @date 2023/3/19 20:27
 */

@Data
public class Message implements Serializable {
    private static final long serialVersionUID=12L;
    private String messageID;       //消息编号,即订单编号
    private String time;            //消息创建的时间
    private String toId;            //消息接收者的编号
    private String toName;          //消息接收者的名字
    private String fromId;          //消息发送者的编号
    private String fromName;        //消息发送者的名字
    private String content;         //消息内容
}
