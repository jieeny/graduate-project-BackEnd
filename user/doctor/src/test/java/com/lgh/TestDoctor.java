package com.lgh;

import com.lgh.entity.DoctorInfo;
import com.lgh.mapper.DoctorMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import util.RedisKeyUtil;
import util.RedisServiceUtil;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author 李广辉
 * @date 2023/3/4 13:22
 */

@SpringBootTest
public class TestDoctor {

    @Autowired
    private DoctorMapper doctorMapper;

    @Test
    public void testQueryDoctorInfo(){
        String doctorId="1";
        DoctorInfo doctorInfo = doctorMapper.queryDoctorInfo(doctorId);
        System.out.println(doctorInfo);
    }


    @Test
    public void testQueryDoctorState(){
        String doctorId="1";
        Integer integer = doctorMapper.queryDoctorState(doctorId);
        System.out.println(integer);
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis(){
        String doctorId="1";
        String doctorStateKey = RedisKeyUtil.getDoctorStateKey(doctorId);
        Object o = redisTemplate.opsForValue().get(doctorStateKey);
        if(o==null){
            System.out.println("不存在!");
            redisTemplate.opsForValue().set(doctorStateKey,1,60*10, TimeUnit.HOURS);
        }else{
            System.out.println(o);
        }
    }

    @Test
    public void testError(){
        String[] doctorIds={"1","2","3","4","5","6","7","8","9","10"};
        for (String id:doctorIds) {
            String doctorRegistrationNumberKey1 = RedisKeyUtil.getDoctorRegistrationNumberKey(id);
            System.out.println(doctorRegistrationNumberKey1);
//            redisTemplate.opsForValue().set(doctorRegistrationNumberKey1,50);
            Integer o = (Integer) redisTemplate.opsForValue().get(doctorRegistrationNumberKey1);
            System.out.println(o);
        }
    }

    @Test
    public void testUpdateDoctorState(){

        Integer integer = doctorMapper.updateDoctorState("1",1);
        System.out.println(integer);
        Integer state = doctorMapper.queryDoctorState("1");
        System.out.println(state);
    }

//    @Resource
//    private RedisServiceUtil util;

    @Test
    public void testRedisTemplate(){
//        RedisServiceUtil util=new RedisServiceUtil();
//        util.testSet("1");
//        Object o = util.testGet("1");
//        System.out.println(o);
//        util.testSet("1");
//        Object o = util.testGet("1");
//        System.out.println(o);
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
        redisTemplate.opsForValue().set("12",3);
    }
}
