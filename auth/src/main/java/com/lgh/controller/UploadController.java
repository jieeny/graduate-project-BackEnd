package com.lgh.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import result.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李广辉
 * @date 2023/2/9 11:42
 */

@RestController
@CrossOrigin
public class UploadController {

    @ResponseBody
    @RequestMapping(value = "/testPicUpload", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Result testPicUpload(@RequestParam("file") MultipartFile[] file) {
        LoggerFactory.getLogger(getClass()).debug("长度"+file.length);
        System.out.println("这个方法有被调用");
        System.out.println(file.length);
        if(file.length>0){
            String message="上传成功!";
            Map<String,Object> resultMap=new HashMap<>();
            resultMap.put("message",message);
            return Result.ok(resultMap);
        }else{
            String message="上传失败!";
            Map<String,Object> resultMap=new HashMap<>();
            resultMap.put("message",message);
            return Result.fail(resultMap);
        }
    }

}
