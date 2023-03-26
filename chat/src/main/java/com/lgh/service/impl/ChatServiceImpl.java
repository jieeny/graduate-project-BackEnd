package com.lgh.service.impl;

import com.lgh.entity.Message;
import com.lgh.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李广辉
 * @date 2023/3/19 20:44
 */

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Message> getMessageListByOrderID(String orderId) {
        Query query = Query.query(Criteria.where("messageID").is(orderId));
        List<Message> messageList = mongoTemplate.find(query, Message.class, "ChatMessages");
        return messageList;
    }

    @Override
    public boolean addMessageObj(Message message) {
        Message chatMessages = mongoTemplate.save(message, "ChatMessages");
        if(chatMessages!=null){
            return true;
        }else {
            return false;
        }
    }
}
