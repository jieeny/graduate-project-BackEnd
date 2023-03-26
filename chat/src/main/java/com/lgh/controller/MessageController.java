package com.lgh.controller;

import com.lgh.entity.Message;
import com.lgh.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.web.bind.annotation.*;
import result.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李广辉
 * @date 2023/3/19 20:58
 */

@RestController
@RequestMapping("chat")
public class MessageController {

    @Autowired
    private ChatService chatService;

    @GetMapping("getMessagesList")
    public Result GetMessages(@RequestParam("orderId")String orderId){
        List<Message> messagesList = chatService.getMessageListByOrderID(orderId);
        Map<String,Object>resultMap=new HashMap<>();
        resultMap.put("message","操作成功!");
        resultMap.put("messagesList",messagesList);
        return Result.ok(resultMap);
    }

    @PostMapping("addMessage")
    public Result AddMessage(@RequestBody Message message){
        boolean b = chatService.addMessageObj(message);
        if(b==true){
            Map<String,Object>resultMap=new HashMap<>();
            resultMap.put("message","操作成功!");
            return Result.ok(resultMap);
        }else {
            Map<String,Object>resultMap=new HashMap<>();
            resultMap.put("message","操作失败!");
            return Result.fail(resultMap);
        }
    }

}
