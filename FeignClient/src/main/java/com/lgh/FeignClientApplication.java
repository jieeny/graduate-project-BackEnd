package com.lgh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 李广辉
 * @date ${DATE} ${TIME}
 */

@SpringBootApplication
@EnableFeignClients(basePackages = "com.lgh.Feign")
public class FeignClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignClientApplication.class, args);
    }
}