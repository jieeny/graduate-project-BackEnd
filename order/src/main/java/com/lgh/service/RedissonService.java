package com.lgh.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author 李广辉
 * @date 2023/3/5 20:51
 */

@Slf4j
@Service
public class RedissonService {

    @Resource
    RedissonClient redissonClient;

    private final static String LOCK_KEY="RESOURCE_KEY";
    int n=500;

    public void seckill(){
        //定义锁
        RLock lock= redissonClient.getLock(LOCK_KEY);
        try{
            //尝试加锁,最大等待时间300毫秒,上锁30毫秒自动解锁
            lock.lock(10,TimeUnit.SECONDS);
            log.info("线程: "+Thread.currentThread().getName()+"获得子锁");
            log.info("剩余数量:{}",--n);
        } catch (Exception e) {
            log.error("程序执行异常:{}",e);
            throw new RuntimeException(e);
        }finally {
            log.info("线程: "+Thread.currentThread().getName()+"准备释放锁!");
            //释放锁
            lock.unlock();
        }
    }

}
