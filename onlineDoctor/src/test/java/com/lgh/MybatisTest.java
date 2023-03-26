package com.lgh;

import com.lgh.entity.onlineDoctorInfo;
import com.lgh.mapper.DoctorMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author 李广辉
 * @date 2023/3/26 17:46
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MybatisTest {

    @Autowired
    private DoctorMapper doctorMapper;

    @Test
    public void testGetOnlineDoctorInfoList(){
        List<onlineDoctorInfo> list = doctorMapper.getOnlineDoctorInfoList();
        for(onlineDoctorInfo o:list){
            System.out.println(o);
        }
    }

    @Test
    public void testQueryDoctorState(){
        Integer i = doctorMapper.queryDoctorState("1");
        System.out.println(i);
    }

}
