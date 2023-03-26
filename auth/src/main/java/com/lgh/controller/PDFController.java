package com.lgh.controller;

import com.lgh.entity.UpLoadPdf;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import result.Result;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李广辉
 * @date 2023/2/9 21:39
 *
 * 接收前端传过来的pdf文件
 */

@RestController
@RequestMapping("pdf")
public class PDFController {

//    @PostMapping("UpLoad")
//    public Result UpLoadPDF(@RequestBody UpLoadPdf upLoadPdf){
//        String message;
//        if(upLoadPdf==null){
//            message="没有收到前端传过来的值!";
//            Map<String,Object> resultMap=new HashMap<>();
//            resultMap.put("message",message);
//            return Result.fail(resultMap);
//        }
//        String id= upLoadPdf.getPdfId();
//        System.out.println("id: "+id);
//        MultipartFile file = upLoadPdf.getFile();
//        System.out.println("file.getSize(): "+file.getSize());
//        message="上传成功!";
//        Map<String,Object> resultMap=new HashMap<>();
//        resultMap.put("message",message);
//        return Result.fail(resultMap);
//    }

}
