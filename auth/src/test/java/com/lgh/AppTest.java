package com.lgh;

import com.lgh.entity.Doctor;
import com.lgh.entity.User;
import com.lgh.mapper.DoctorMapper;
import com.lgh.mapper.UserMapper;
import helper.UUIDHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Unit test for simple App.
 */

@SpringBootTest
public class AppTest 
{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testUserMapper(){
//        int i = userMapper.selectUserByUserName("admin");
//        System.out.println(i);
//        String username="admin";
//        User user = userMapper.selectUserByUserName(username);
//        System.out.println(user);

//        User user = userMapper.selectUserByUserNameAndPassword("admin", "admin");
//        System.out.println(user);

        User user=new User();
        user.setEmail("test@qq.com");
        user.setPassword("test");
        user.setUsername("test");
        int i = userMapper.insertUser(user);
        System.out.println(i);
    }

    @Test
    public void testDoctorMapper(){
//        String doctorName="张三";
//        Doctor doctor = doctorMapper.queryDoctorByDoctorName(doctorName);
//        System.out.println(doctor);
        Doctor doctor=new Doctor();
        doctor.setDoctorId("16");
        doctor.setDoctorName("郭大夫");
        doctor.setPassword("12345");
        int i = doctorMapper.insertDoctor(doctor);
        System.out.println(i);
    }

    @Test
    public void testRedis(){
        System.out.println(redisTemplate.opsForValue().get("3"));
    }



}
