package com.lgh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author 李广辉
 * @date 2023/2/24 11:11
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 添加这个Endpoint,这样在网页可以通过websocket连接上服务
     * 也就是我们配置websocket的服务地址,并且可以指定是否使用socketJS
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * 1.将ws/ep注册为stomp的端点,用户连接了这个端点就可以进行websocket通讯,支持socketJS
         * 2.setAllowOrigins("*"): 允许跨域
         * 3.withSocketJS(): 支持socketJS访问
         */
        registry.addEndpoint("/ws/ep").setAllowedOrigins("*").withSockJS();
    }

    /**
     * 配置消息代理
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //配置代理域,可以配置多个,配置代理目的地前缀为/queue.可以在配置域上向客户端推送消息
        registry.enableSimpleBroker("/queue");
    }
}
