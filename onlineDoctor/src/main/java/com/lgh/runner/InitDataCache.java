package com.lgh.runner;

import com.lgh.entity.onlineDoctorInfo;
import com.lgh.mapper.DoctorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 李广辉
 * @date 2023/3/26 19:58
 */

@Component
@Order(1)       //初始化架子啊优先级,数字越小优先级越高
public class InitDataCache implements CommandLineRunner {

    public static Map<String,onlineDoctorInfo>cacheMap=new ConcurrentHashMap<>();
    public static Set<onlineDoctorInfo>cacheSet=new HashSet<>();

    @Resource
    private DoctorMapper doctorMapper;


    @Override
    public void run(String... args) throws Exception {
        List<onlineDoctorInfo> doctorInfoList = doctorMapper.getOnlineDoctorInfoList();
        for (onlineDoctorInfo o:doctorInfoList){
            cacheMap.put(o.getDoctorId(),o);
            cacheSet.add(o);
//            System.out.println(o);
        }
    }
}
