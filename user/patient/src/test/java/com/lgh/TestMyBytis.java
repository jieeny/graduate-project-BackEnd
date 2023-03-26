package com.lgh;

import com.lgh.mapper.PatientMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 李广辉
 * @date 2023/3/25 15:37
 */

@SpringBootTest
public class TestMyBytis {

    @Autowired
    private PatientMapper patientMapper;


    @Test
    public void testQueryUsernameByUserId(){
        String s = patientMapper.queryUsernameByUserId("1");
        System.out.println(s);
    }

}
