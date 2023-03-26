package com.lgh.controller;

import com.lgh.entity.DoctorInfo;
import com.lgh.service.DoctorService;
import helper.jwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import result.Result;

import javax.management.relation.RelationSupport;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李广辉
 * @date 2023/3/4 15:05
 */

@RestController
//@RequestMapping("doctor")
public class DoctorInfoController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/GetDoctorInfo")
    public Result getInfo(HttpServletRequest request){
        //
        System.out.println("请求了/doctor/GetDoctorInfo");

        //1、获取请求中的token
        String token = request.getHeader("token");

        //2、解析token,获取token中的doctorId
        String doctorId = jwtHelper.getUserId(token);

        //3、根据doctorId获取医生信息
        //3.1首先从Redis中获取(toDo)
        //3.2如果Redis中不存在则从Mysql中获取
        DoctorInfo doctorInfo = doctorService.queryDoctorInfo(doctorId);
        Map<String,Object>ResultMap=new HashMap<>();
        ResultMap.put("DoctorInfo",doctorInfo);
        return Result.ok(ResultMap);
    }

}
