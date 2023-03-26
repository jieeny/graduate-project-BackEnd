package com.lgh.service.impl;

import com.lgh.entity.DoctorInfo;
import com.lgh.mapper.DoctorMapper;
import com.lgh.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import util.RedisKeyUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author 李广辉
 * @date 2023/3/4 15:04
 */

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public DoctorInfo queryDoctorInfo(String doctorId) {

        return doctorMapper.queryDoctorInfo(doctorId);
    }

    @Override
    public Integer queryDoctorState(String doctorId) {

        //先从Redis中查询
        //如果不存在再从mysql中查询并缓存到Redis中
        String doctorStateKey = RedisKeyUtil.getDoctorStateKey(doctorId);
        Object o = redisTemplate.opsForValue().get(doctorStateKey);
        if(o==null){
            Integer state=doctorMapper.queryDoctorState(doctorId);
            redisTemplate.opsForValue().set(doctorStateKey,state,60*10, TimeUnit.HOURS);
            return state;
        }else{
            Integer state= (Integer) o;
            return state;
        }
    }

    @Override
    public Integer updateDoctorState(String doctorId, Integer state) {
        if(state==0){
            state=1;
        }else{
            state=0;
        }
        String doctorStateKey=RedisKeyUtil.getDoctorStateKey(doctorId);
        redisTemplate.opsForValue().set(doctorStateKey,state,60*10,TimeUnit.HOURS);
        Integer integer = doctorMapper.updateDoctorState(doctorId, state);
        return integer;
    }


}
