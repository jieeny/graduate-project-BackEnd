package com.lgh.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 李广辉
 * @date 2023/2/12 10:54
 */

@FeignClient(value = "FeignProvider")
public interface TestFeign {

    @RequestMapping(value = "/demo/getHost",method = RequestMethod.GET)
    public String getHost(@RequestParam("name") String name);



}
