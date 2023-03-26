package com.lgh.controller;

import com.lgh.service.DoctorService;
import helper.jwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import result.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李广辉
 * @date 2023/3/5 10:38
 */

@RestController
//@RequestMapping("doctor")
public class DoctorStateController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/getState")
    public Result doctorState(HttpServletRequest request){
        //1、获取请求中的token
        String token = request.getHeader("token");
        //2、解析token,获取token中的doctorId
        String doctorId = jwtHelper.getUserId(token);
        Integer state = doctorService.queryDoctorState(doctorId);
        //6、将查询结果存储到map中
        Map<String,Object>resultMap=new HashMap<>();
        resultMap.put("state",state);
        return Result.ok(resultMap);
    }

    @GetMapping("setState")
    public Result changeDoctorState(HttpServletRequest request, @RequestParam("state")String state1){
        //1、获取请求中的token
        String token = request.getHeader("token");
        //2、解析token,获取token中的doctorId
        String doctorId = jwtHelper.getUserId(token);
        Integer state= Integer.valueOf(state1);
        Integer integer = doctorService.updateDoctorState(doctorId, state);
        if(integer<=0){
            Map<String,Object>resultMap=new HashMap<>();
            String message="服务器错误!";
            resultMap.put("message",message);
            return Result.fail(resultMap);
        }else {
            Map<String,Object>resultMap=new HashMap<>();
            String message="操作成功!";
            resultMap.put("message",message);
            Integer newState = doctorService.queryDoctorState(doctorId);
            System.out.println("newState为: "+newState);
            resultMap.put("state",newState);
            return Result.ok(resultMap);
        }
    }


}
