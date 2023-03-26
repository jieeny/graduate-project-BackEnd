package com.lgh.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 李广辉
 * @date 2023/3/5 20:44
 */

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson(){
        Config config=new Config();
        config.useSingleServer().setAddress("redis://"+host+":"+port).setPassword(password);
        return Redisson.create(config);
    }


}
