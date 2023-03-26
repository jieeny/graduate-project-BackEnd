package com.lgh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import result.Result;

/**
 * @author 李广辉
 * @date 2023/3/12 19:00
 */

@RestController
@RequestMapping("Order")
public class TestController {

    @GetMapping("Test")
    public Result test(){
        String message="操作成功!";
        return Result.ok(message);
    }

}
