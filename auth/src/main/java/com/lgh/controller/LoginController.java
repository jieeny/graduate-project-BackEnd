package com.lgh.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.lgh.entity.Doctor;
import com.lgh.entity.LoginInfo;
import com.lgh.entity.User;
import com.lgh.service.DoctorService;
import com.lgh.service.UserService;
import helper.UUIDHelper;
import helper.jwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import result.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author 李广辉
 * @date 2023/1/7 16:16
 */

@RestController()
//@RequestMapping("auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;

    //注册操作
    @PostMapping("register")
    public Result UserRegister(@RequestBody LoginInfo loginInfo,
                               HttpServletRequest request){
        if(loginInfo==null){
            String message="登录失败,未收到有效参数";
            return Result.fail(message);
        }
        System.out.println(loginInfo);
        String username=loginInfo.getUsername();
        String password=loginInfo.getPassword();
        String code=loginInfo.getCode();
        String type= loginInfo.getRadio();
        System.out.println("type: "+type);

        //1、首先判断验证码是否正确
        String captcha= (String) request.getSession().getAttribute("code");
        System.out.println("captcha: "+captcha);
        System.out.println("code: "+code);
        if(code.isEmpty()||!captcha.equalsIgnoreCase(code)){
            String message="登录失败,验证码错误";
            return Result.fail(message);
        }
        //2、判断是医生登录还是用户登录
        if(type.equals("1")){       //医生登录
            Doctor d = doctorService.queryDoctorByDoctorName(username);
            if(d!=null){
                String message="注册失败,该用户已经存在!";
                return Result.fail(message);
            }else{
                Doctor doctor=new Doctor();
                doctor.setDoctorId(UUIDHelper.getUsernameUUID(username));
                doctor.setDoctorName(username);
                doctor.setPassword(password);
                Integer integer = doctorService.insertDoctor(doctor);
                if(integer<1){
                    String message="注册失败,服务器错误!";
                    return Result.fail(message);
                }
                //登录成功,生成Token
                String token = jwtHelper.createToken(doctor.getDoctorId(), doctor.getDoctorName(),"1");
                String message="登录成功!";
                Map<String,Object>resultMap=new HashMap<>();
                resultMap.put("token",token);
                resultMap.put("name",doctor.getDoctorName());
                resultMap.put("message",message);
                resultMap.put("type",loginInfo.getRadio());
                return Result.ok(resultMap);
            }
        }else{                      //患者登录
            User u = userService.selectUserByUserName(username);
            if(u!=null){
                String message="注册失败,用户不存在!";
                return Result.fail(message);
            }else{
                User user1=new User();
                user1.setUsername(username);
                user1.setPassword(password);
                String userId= UUIDHelper.getUsernameUUID(username);
                user1.setUserid(userId);
                int i = userService.insertUser(user1);
                if(i>0){
                    User user = userService.selectUserByUserName(username);
                    String message="注册成功!";
                    Map<String,Object>resultMap=new HashMap<>();
                    String token = jwtHelper.createToken(user.getUserid(), user.getUsername(),"2");
                    resultMap.put("token",token);
                    resultMap.put("name",username);
                    resultMap.put("message",message);
                    resultMap.put("type",loginInfo.getRadio());
                    return Result.ok(resultMap);
                }else{
                    String message="注册失败,服务器错误!";
                    return Result.fail(message);
                }
            }
        }
    }

    //登陆操作
    @PostMapping("login")
    public Result UserLogin(@RequestBody LoginInfo loginInfo,
                            HttpServletRequest request){
//        User user = userService.selectUserByUserNameAndPassword(username, password);
//        if(user!=null){
//            Integer id=user.getUserid();
//            StpUtil.login(id);
//            Object loginId = StpUtil.getLoginId();
//            String tokenValue = StpUtil.getTokenValue();
//            String message="登录成功";
//            Map<String,Object> resultMap=new HashMap<>();
//            resultMap.put("message",message);
//            resultMap.put("token",tokenValue);
//            resultMap.put("id",loginId);
//            return Result.ok(resultMap);
//        }else{
//            String message="登录失败,密码不正确!";
//            return Result.fail(message);
//        }

        //首先判断是否存在该用户

        if(loginInfo==null){
            String message="登录失败,未收到有效参数";
            return Result.fail(message);
        }
        System.out.println(loginInfo);
        String username=loginInfo.getUsername();
        String password=loginInfo.getPassword();
        String code=loginInfo.getCode();
        String type= loginInfo.getRadio();
        System.out.println("type: "+type);

        //1、首先判断验证码是否正确
        String captcha= (String) request.getSession().getAttribute("code");
        System.out.println("captcha: "+captcha);
        System.out.println("code: "+code);
        if(code.isEmpty()||!captcha.equalsIgnoreCase(code)){
            String message="登录失败,验证码错误";
            return Result.fail(message);
        }
        //2、判断是医生登录还是用户登录
        if(type.equals("1")){       //医生登录
            Doctor doctor = doctorService.queryDoctorByDoctorName(username);
            if(doctor==null){
                String message="登录失败,用户不存在!";
                return Result.fail(message);
            }
            String doctorPassword= doctor.getPassword();
            if(!doctorPassword.equals(password)){
                String message="登录失败,密码不正确!";
                return Result.fail(message);
            }
            //登录成功,生成Token
            String token = jwtHelper.createToken(doctor.getDoctorId(), doctor.getDoctorName(),"1");
            String message="登录成功!";
            Map<String,Object>resultMap=new HashMap<>();
            resultMap.put("token",token);
            resultMap.put("name",doctor.getDoctorName());
            resultMap.put("message",message);
            resultMap.put("type",loginInfo.getRadio());
            return Result.ok(resultMap);
        }else{                      //患者登录
            User user = userService.selectUserByUserName(username);
            if(user==null){
                String message="登录失败,用户不存在!";
                return Result.fail(message);
            }else{
                String userPassword=user.getPassword();
                if(userPassword.equals(password)){
                    String message="登录成功!";
                    Map<String,Object>resultMap=new HashMap<>();
                    String token = jwtHelper.createToken(user.getUserid(), user.getUsername(),"2");
                    resultMap.put("token",token);
                    resultMap.put("name",username);
                    resultMap.put("message",message);
                    resultMap.put("type",loginInfo.getRadio());
                    return Result.ok(resultMap);
                }else{
                    String message="登录失败,密码不正确!";
                    return Result.fail(message);
                }
            }
        }


    }

    /**
     * 前端发送注销登录请求给后端,后端接收到请求之后直接返回成功,前端根据后端的返回值直接将token设置为无效即可
     * @return
     */
    //注销登录
    @PostMapping("/logout")
    public Result logout(){
        String message="注销登录成功!";
        return Result.ok(message);
    }

    //判断token是否已经失效
    @GetMapping("testToken")
    public Result token(String token){
        System.out.println("正在测试token是否过期!");
        boolean b = jwtHelper.checkToken(token);
        if(b){      //证明还没有过期
            String message="欢迎回来!";
            return Result.ok(message);
        }else{
            String message="会话已过期,请重新登录!";
            return Result.fail(message);
        }
    }

    //获取当前登录用户的个人信息


    //用户永久删除个人信息


}
