package com.lgh;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import util.RedisKeyUtil;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.o;

/**
 * @author 李广辉
 * @date 2023/2/22 15:59
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testHash(){
        redisTemplate.opsForHash().put("doctorNumber","12345",51);
        System.out.println(redisTemplate.opsForHash().get("doctorNumber","12345"));
    }

    @Test
    public void test1(){

        if(redisTemplate.opsForHash().get("doctorNumber",11)!=null){
            int i = (int) redisTemplate.opsForHash().get("doctorNumber",11);
            System.out.println(i);
        }else{
            System.out.println(0);
        }

    }


    @Test
    public void test(){
//        String[] doctorIds={"1","2","3","4","5","6","7","8","9","10"};
//        for (String id:doctorIds){
//            String doctorRegistrationNumberKey = RedisKeyUtil.getDoctorRegistrationNumberKey(id);
//            int i= (int) redisTemplate.opsForValue().get(doctorRegistrationNumberKey);
//            System.out.println(i);
//        }

        Object o1 = redisTemplate.opsForValue().get("DoctorState:1");
        System.out.println(o1);


    }

    @Test
    public void testError(){
//        String doctorId="1";
//        String doctorRegistrationNumberKey =
//                RedisKeyUtil.getDoctorRegistrationNumberKey(doctorId);
//        Integer doctorRegistrationNumber
//                = (Integer) redisTemplate.opsForValue().get(doctorRegistrationNumberKey);
//        System.out.println(doctorRegistrationNumber);

        String[] doctorIds={"1","2","3","4","5","6","7","8","9","10"};
        for (String id:doctorIds){
            String doctorRegistrationNumberKey1 = RedisKeyUtil.getDoctorRegistrationNumberKey(id);
            System.out.println(doctorRegistrationNumberKey1);
            Integer o = (Integer) redisTemplate.opsForValue().get(doctorRegistrationNumberKey1);
            System.out.println(o);

        }
    }

    @Test
    public void test2(){
//        String key="12345";
////        redisTemplate.opsForValue().set(key,1);
//
//        Integer val= (Integer) redisTemplate.opsForValue().get(key);
//        System.out.println(val);

        String[] doctorIds={"1","2","3","4","5","6","7","8","9","10"};
        for (String id:doctorIds){
            String doctorRegistrationNumberKey1 = RedisKeyUtil.getDoctorRegistrationNumberKey(id);
            System.out.println(doctorRegistrationNumberKey1);
//            redisTemplate.opsForValue().set(doctorRegistrationNumberKey1,50);
            Integer o = (Integer) redisTemplate.opsForValue().get(doctorRegistrationNumberKey1);
            System.out.println(o);

        }

    }

    @Test
    public void TestRedisString(){
//        redisTemplate.opsForValue().set("1","1");
        System.out.println(redisTemplate.opsForValue().get("2"));
    }

    @Test
    public void TestRedisInteger(){
//        redisTemplate.opsForValue().set("2",1);
//        System.out.println();
        System.out.println(redisTemplate.opsForValue().get("2"));
    }

    @Test
    public void testRedis(){
//        System.out.println(redisTemplate.opsForValue().get("3"));
        redisTemplate.opsForValue().set("21",1);
    }

}
