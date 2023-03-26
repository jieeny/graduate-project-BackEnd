package com.lgh.controller;

import com.lgh.WebSocket.MyWebSocket;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李广辉
 * @date 2023/2/21 21:05
 */

@RestController
public class HomeController {

    /**
     * 测试websocket
     */
    @GetMapping("/broadcast")
    public void broadcast(){
        MyWebSocket.broadcast();
    }

}
