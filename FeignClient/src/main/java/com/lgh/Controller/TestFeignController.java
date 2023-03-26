package com.lgh.Controller;

import com.lgh.Feign.TestFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李广辉
 * @date 2023/2/12 10:57
 */

@RestController
public class TestFeignController {

    @Autowired
    private TestFeign testFeign;

    @PostMapping("/client/postPerson")
    public String postPerson(String name){
        return testFeign.getHost(name);
    }

}
