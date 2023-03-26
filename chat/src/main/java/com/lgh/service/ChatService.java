package com.lgh.service;

import com.lgh.entity.Message;

import java.util.List;

/**
 * @author 李广辉
 * @date 2023/3/19 20:40
 */
public interface ChatService {

    //根据订单编号获取聊天记录
    List<Message> getMessageListByOrderID(String orderId);

    //添加一条聊天消息
    boolean addMessageObj(Message message);

}
