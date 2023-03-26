package com.lgh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket的配置类
 * @author 李广辉
 * @date 2023/2/21 16:43
 */

@Configuration
public class WebSocketConfig {

    //注入一个ServerEndpointExporter
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
