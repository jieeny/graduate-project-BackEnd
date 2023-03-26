package com.lgh.controller;

import com.lgh.entity.UpLoadPdf;
import org.springframework.web.bind.annotation.*;
import result.Result;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author 李广辉
 * @date 2023/2/6 21:21
 */

@RestController
@RequestMapping("auth")
public class TestController {

    @GetMapping("/token")
    public Result Test(@RequestParam("token") String token){
        System.out.println("token: "+token);
        System.out.println("有人请求了test");
        String message="请求成功";
        return Result.ok(message);
    }

    @GetMapping("/test")
    public Result Test(){
        String message="请求了Test!";
        return Result.ok(message);
    }



    @PostMapping("/testPdf")
    public Result TestPdf(@RequestBody UpLoadPdf upLoadPdf){
//        if (attachments == null) {
//            String message="请求失败1!";
//            return Result.fail(message);
//        }
        System.out.println("upLoadPdf: "+upLoadPdf);
//        BASE64Decoder decoder = new BASE64Decoder();
//        try {
//            // Base64解码
//            byte[] b = decoder.decodeBuffer(attachments);
//            for (int i = 0; i < b.length; ++i) {
//                // 调整异常数据
//                if (b[i] < 0) {
//                    b[i] += 256;
//                }
//            }
//            String imgFilePath = "D://hello.pdf";
//            OutputStream out = new FileOutputStream(imgFilePath);
//            out.write(b);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
////            logger.error(e.getMessage(), e);
//            String message="请求失败2!";
//            return Result.fail(message);
//        }
//        System.out.println(attachments);
        String message="上传成功!";
        return Result.ok(message);
    }

}
