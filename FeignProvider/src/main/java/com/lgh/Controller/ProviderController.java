package com.lgh.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李广辉
 * @date 2023/2/12 15:13
 */

@RestController
public class ProviderController {

    @GetMapping("/demo/getHost")
    public String getHost(String name){
        return "Provider"+name;
    }

    @GetMapping("/demo/chat")
    public String chat(String type,String receiverId,String senderId,String message){
        return "provider: "+type+receiverId+senderId+message;
    }

}
