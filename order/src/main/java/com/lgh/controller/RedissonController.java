package com.lgh.controller;

import com.lgh.service.RedissonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李广辉
 * @date 2023/3/5 20:59
 */

@RestController()
public class RedissonController {

    @Autowired
    private RedissonService redissonService;

    @RequestMapping("/redissonService")
    public void TestRedisson(){
        for (int i=100;i<600;i++){
            RedissonThread redissonThread=new RedissonThread(redissonService,
                    "redissonService->"+i);
            redissonThread.start();
        }
    }

}


class RedissonThread extends Thread{

    private RedissonService redissonService;

    public RedissonThread(RedissonService redissonService,String threadName){
        super(threadName);
        this.redissonService=redissonService;
    }

    @Override
    public void run() {
        redissonService.seckill();
    }
}
