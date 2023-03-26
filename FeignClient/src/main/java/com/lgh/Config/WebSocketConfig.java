package com.lgh.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author 李广辉
 * @date 2023/2/12 15:38
 */

@Configuration
public class WebSocketConfig {

    //注入一个ServerEndpointExporter
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
