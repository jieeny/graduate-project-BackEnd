package com.lgh.controller;

import com.lgh.entity.onlineDoctorInfo;
import com.lgh.entity.onlineDoctorVO;
import com.lgh.service.onlineDoctorService;
import helper.jwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import result.Result;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李广辉
 * @date 2023/2/22 16:56
 */

@RestController
//@RequestMapping("onlineDoctor")
public class OnlineDoctorController {

    @Autowired
    private onlineDoctorService doctorService;


//    public Result doctorOnline(@RequestBody onlineDoctorInfo doctorInfo) throws ParseException {
//        boolean b = doctorService.insertDoctorToMongoDB(doctorInfo);
//        if(b){
//            String message="医生上线成功!";
//            return Result.ok(message);
//        }else{
//            String message="医生上线失败!";
//            return Result.fail(message);
//        }
//    }

    @PostMapping("online")
    public Result doctorOnline(HttpServletRequest request) throws Exception {
        //1、获取请求中的token
        String token = request.getHeader("token");
        //2、解析token,获取token中的doctorId
        String doctorId = jwtHelper.getUserId(token);
        //3、处理医生上线逻辑
        Boolean aBoolean = doctorService.doctorOnline(doctorId);
        if(aBoolean){
            Map<String,Object> ResultMap=new HashMap<>();
            ResultMap.put("message","操作成功!");
            return Result.ok(ResultMap);
        }else {
            Map<String,Object> ResultMap=new HashMap<>();
            ResultMap.put("message","操作失败!");
            return Result.fail(ResultMap);
        }
    }

    @GetMapping("left")
    public Result doctorLeft(String doctorId){
        boolean b = doctorService.deleteOnlineDoctorFromMongoDB(doctorId);
        if(b){
            String message="医生下线成功!";
            return Result.ok(message);
        }else{
            String message="医生下线失败!";
            return Result.fail(message);
        }
    }

    @GetMapping("onlineDoctorList")
    public Result doctorListByDepartment(@RequestParam("department")String department){
        List<onlineDoctorVO> voList = doctorService.queryOnlineDoctorByDepartment(department);
        return Result.ok(voList);
    }

}
