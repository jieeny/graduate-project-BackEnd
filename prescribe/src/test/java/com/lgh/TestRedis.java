package com.lgh;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author 李广辉
 * @date 2023/3/18 9:55
 */

@SpringBootTest
public class TestRedis {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis(){
//        System.out.println(redisTemplate.opsForValue().get("3"));
        redisTemplate.opsForValue().set("12",3);
    }



}
