package com.lgh;

import cn.dev33.satoken.SaManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 李广辉
 * @date 2023/1/6 21:37
 */

@SpringBootApplication
public class authApplication {
    public static void main(String[] args) {
        SpringApplication.run(authApplication.class, args);
        System.out.println("sa-token的配置如下: "+ SaManager.getConfig());
    }
}
