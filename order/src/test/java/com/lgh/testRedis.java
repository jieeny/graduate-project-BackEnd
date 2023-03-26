package com.lgh;

import com.lgh.entity.OrderInfo;
import helper.UUIDHelper;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import util.RedisKeyUtil;
import util.RedisServiceUtil;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author 李广辉
 * @date 2023/2/21 15:04
 */

@SpringBootTest
public class testRedis {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void insertRedis(){
//        String redisKey="test";
//        redisTemplate.opsForValue().set(redisKey,1);
//        System.out.println(redisTemplate.opsForValue().get(redisKey));
//        System.out.println(redisTemplate.opsForValue().increment(redisKey));
//        System.out.println(redisTemplate.opsForValue().decrement(redisKey));

        String redisKey="lgh";
        System.out.println(redisTemplate.opsForValue().get(redisKey));

    }


    @Test
    public void testRedisZSet(){
        OrderInfo order1=new OrderInfo();
        OrderInfo order2=new OrderInfo();

        order1.setOrderTime(System.currentTimeMillis());
        order1.setOrderId(UUIDHelper.getRandomUUID());
        order1.setAllergies("order1");
        order1.setDoctorName("张三");
        Boolean add = redisTemplate.opsForZSet().add("1", order1, order1.getOrderTime());
        System.out.println(add);
        order2.setOrderTime(System.currentTimeMillis());
        order2.setOrderId(UUIDHelper.getRandomUUID());
        order2.setAllergies("order2");
        order2.setDoctorName("李四");
        Boolean add1 = redisTemplate.opsForZSet().add("1", order2, order2.getOrderTime());
        System.out.println(add1);


//        Set<OrderInfo> range = redisTemplate.boundZSetOps("1").range(0, -1);
//        System.out.println(range);
//        for(OrderInfo order:range){
//            if(order.getDoctorName().equals("张三")){
//                redisTemplate.opsForZSet().remove("1",order);
//            }
//        }
//        Set<OrderInfo> range1 = redisTemplate.boundZSetOps("1").range(0, -1);
//        System.out.println(range1);

    }

    @Test
    public void testRedisString(){
//        Boolean delete = redisTemplate.delete("123456789");
//        System.out.println(delete);
        String RedisKey="redis12345";
        redisTemplate.opsForValue().set(RedisKey,1);
    }

    @Resource
    RedissonClient redissonClient;

    @Test
    public void testRedisson(){
        String doctorId="2";
        RLock lock = redissonClient.getLock(doctorId);
        lock.lock(100, TimeUnit.SECONDS);
        try{
            System.out.println("获取到锁");
        }finally {
            lock.unlock();
        }
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
//            redisTemplate.opsForValue().set(doctorRegistrationNumberKey1,50);
            Integer o = (Integer) redisTemplate.opsForValue().get(doctorRegistrationNumberKey1);
            System.out.println(o);

        }


    }



    @Test
    public void test2(){
        String key="12345";
//        redisTemplate.opsForValue().set(key,1);

        Integer val= (Integer) redisTemplate.opsForValue().get(key);
        System.out.println(val);
    }

    @Test
    public void TestRedisString(){
        redisTemplate.opsForValue().set("2","1");
        System.out.println(redisTemplate.opsForValue().get("2"));
    }

    @Test
    public void TestRedisInteger(){
//        redisTemplate.opsForValue().set("3",1);
//        System.out.println();
        System.out.println(redisTemplate.opsForValue().get("21"));
    }

    @Test
    public void TestRedisObject(){
//        OrderInfo order1=new OrderInfo();
//        order1.setOrderTime(System.currentTimeMillis());
//        order1.setOrderId(UUIDHelper.getRandomUUID());
//        order1.setAllergies("order1");
//        order1.setDoctorName("张三");
//        redisTemplate.opsForValue().set("123",order1);
//        System.out.println(redisTemplate.opsForValue().get("123"));

//        String doctorPatientWaitingHealSet =
//                RedisKeyUtil.getDoctorPatientWaitingHealSet("1");
//        Set<String> members = redisTemplate.opsForSet().members(doctorPatientWaitingHealSet);
//        System.out.println(members);

//        String doctorRegistrationNumberKey =
//                RedisKeyUtil.getDoctorRegistrationNumberKey("1");
//        Integer doctorRegistrationNumber
//                = (Integer) redisTemplate.opsForValue().get(doctorRegistrationNumberKey);
//        System.out.println(doctorRegistrationNumber);

        String doctorPatientWaitingHealSet
                = RedisKeyUtil.getDoctorPatientWaitingHealSet("1");
        Set<String> members = redisTemplate.opsForSet().members(doctorPatientWaitingHealSet);
        System.out.println(members);
    }



}
