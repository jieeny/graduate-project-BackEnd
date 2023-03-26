package com.lgh.Filters;

import helper.jwtHelper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author 李广辉
 * @date 2023/2/7 11:33
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

    private static final String AUTHORIZE_TOKEN="token";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1、获取请求
//        ServerHttpRequest request= (ServerHttpRequest) exchange.getRequest();
        ServerHttpRequest request = exchange.getRequest();
        //2、获取响应
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getURI().getPath();
        if(path.equals("/auth/register")){
            System.out.println("是注册请求!");
            return chain.filter(exchange);
        }
        if(path.equals("/auth/login")){
            System.out.println("是登陆请求!");
            return chain.filter(exchange);
        }
        if(path.equals("/auth/captcha")){
            System.out.println("是验证码请求!");
            return chain.filter(exchange);
        }

        if(path.equals("/auth/logout")){
            System.out.println("是注销登录请求!");
            return chain.filter(exchange);
        }

        //3、如果是登录请求则放行
//        if(request.getURI().getPath().contains("/user/auth/login")){
//            System.out.println("是登陆请求!");
//            return chain.filter(exchange);
//        }
        System.out.println("不是登陆||注册||验证码请求!");
        //4、获取请求头
        HttpHeaders headers = request.getHeaders();
        //5、请求头中获取令牌
        String token=headers.getFirst(AUTHORIZE_TOKEN);
        System.out.println("token: "+token);
        //6、判断请求头中是否含有令牌
        if(StringUtils.isEmpty(token)){     //不含有token
            //7、响应中放入返回的状态码,没有权限访问
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            System.out.println("token为空!");
            //8、返回
            return response.setComplete();
        }

        //9、请求头中含有令牌则进行解析
        boolean b = jwtHelper.checkToken(token);
        System.out.println(b);
        if(!b){
            //10、解析令牌出错,说明令牌过期或者是伪造的
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            System.out.println("token过期");
            //11、返回
            return response.setComplete();
        }

        //12、放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

