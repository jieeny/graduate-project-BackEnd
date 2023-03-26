package com.lgh.service.Impl;

import com.lgh.WebSocket.MyWebSocket;
import com.lgh.entity.doctorLeftVO;
import com.lgh.entity.onlineDoctorInfo;
import com.lgh.entity.onlineDoctorVO;
import com.lgh.mapper.DoctorMapper;
import com.lgh.runner.InitDataCache;
import com.lgh.service.onlineDoctorService;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import time.Time;
import util.RedisKeyUtil;


import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 李广辉
 * @date 2023/2/20 19:54
 */

@Service
public class onlineDoctorServiceImpl implements onlineDoctorService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private DoctorMapper doctorMapper;

    /**
     * 上线
     * @param doctorInfo
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public boolean insertDoctorToMongoDB(onlineDoctorInfo doctorInfo) throws ParseException {
        //将医生信息加入到mongoDB中
        onlineDoctorInfo onlineDoctor = mongoTemplate.save(doctorInfo, "OnlineDoctor");
        //获取当前时间的时间戳
        long currentTime = System.currentTimeMillis();
        //生成医生最近一次下线的时间的Redis Key
        String doctorLeftTime = RedisKeyUtil.getDoctorLeftTime(doctorInfo.getDoctorId());
        //医生剩余号源的Redis Key
        String doctorRegistrationNumberKey = RedisKeyUtil.getDoctorRegistrationNumberKey(doctorInfo.getDoctorId());
        //与Redis中的最近一次下线的时间戳进行比较
//        if(redisTemplate.opsForHash().get(doctorLeftTime, doctorInfo.getDoctorId())!=null){
//            long leftTime = (long) redisTemplate.opsForHash().get(doctorLeftTime, doctorInfo.getDoctorId());
//            boolean b = Time.between24hours(leftTime, currentTime);
//            //不在24小时内,发放新的号源
//            if(!b){
//                redisTemplate.opsForHash().put(doctorRegistrationNumberKey,doctorInfo.getDoctorId(),50);
//            }
//        }else{
//            redisTemplate.opsForHash().put(doctorRegistrationNumberKey,doctorInfo.getDoctorId(),50);
//        }

        //与Redis中的最近一次下线的时间戳进行比较
        if(redisTemplate.opsForValue().get(doctorLeftTime)!=null){
            long leftTime = (long)redisTemplate.opsForValue().get(doctorLeftTime);
            boolean b = Time.between24hours(leftTime, currentTime);
            //不在24小时内,发放新的号源
            if(!b) {
                redisTemplate.opsForValue().set(doctorRegistrationNumberKey, 50);
            }
        }else{
            redisTemplate.opsForValue().set(doctorRegistrationNumberKey,50);
        }

        //推送医生上线的信息到患者前台
        onlineDoctorVO vo=new onlineDoctorVO();
        vo.setType("上线");
        vo.setDoctorId(doctorInfo.getDoctorId());
        vo.setDoctorName(doctorInfo.getDoctorName());
        vo.setPost(doctorInfo.getPost());
        vo.setHospital(doctorInfo.getHospital());
        //获取医生当前的患者数量
        String doctorPatientWaitingHealNumberKey =
                RedisKeyUtil.getDoctorPatientWaitingHealNumberKey(doctorInfo.getDoctorId());
        Integer number = (Integer) redisTemplate.opsForValue().get(doctorPatientWaitingHealNumberKey);
        if(number!=null){
            vo.setNumber(number);
        }else {     //当前没有患者
            vo.setNumber(0);
        }
        vo.setDepartment(doctorInfo.getDepartment());
        MyWebSocket.pushMessageAboutDoctor(vo);
        //修改mysql数据库中的医生状态

        return true;
    }

    /**
     * 医生上线(重构)
     * @param doctorId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public Boolean doctorOnline(String doctorId) throws Exception {
        //(1)根据医生id获取医生的信息
        onlineDoctorInfo doctorInfo = (onlineDoctorInfo) InitDataCache.cacheMap.get(doctorId);
        mongoTemplate.save(doctorInfo, "OnlineDoctor");
        //(2)获取当前时间的时间戳
        long currentTime = System.currentTimeMillis();
        //(3)生成医生最近一次下线的时间的Redis Key
        String doctorLeftTime = RedisKeyUtil.getDoctorLeftTime(doctorInfo.getDoctorId());
        //(4)医生剩余号源的Redis Key
        String doctorRegistrationNumberKey = RedisKeyUtil.getDoctorRegistrationNumberKey(doctorInfo.getDoctorId());
        if(redisTemplate.opsForValue().get(doctorLeftTime)!=null){
            long leftTime = (long)redisTemplate.opsForValue().get(doctorLeftTime);
            boolean b = Time.between24hours(leftTime, currentTime);
            //不在24小时内,发放新的号源
            if(!b) {
                redisTemplate.opsForValue().set(doctorRegistrationNumberKey, 50);
            }
        }else{
            redisTemplate.opsForValue().set(doctorRegistrationNumberKey,50);
        }
        //(5)修改mysql数据库中的医生状态
        int oldState= doctorMapper.queryDoctorState(doctorId);
        int newState=oldState==0?1:0;
        doctorMapper.updateDoctorState(doctorId,newState);

        //推送医生上线的信息到患者前台
        onlineDoctorVO vo=new onlineDoctorVO();
        vo.setType("上线");
        vo.setDoctorId(doctorInfo.getDoctorId());
        vo.setDoctorName(doctorInfo.getDoctorName());
        vo.setPost(doctorInfo.getPost());
        vo.setHospital(doctorInfo.getHospital());
        //获取医生当前的患者数量
        String doctorPatientWaitingHealNumberKey =
                RedisKeyUtil.getDoctorPatientWaitingHealNumberKey(doctorInfo.getDoctorId());
        Integer number = (Integer) redisTemplate.opsForValue().get(doctorPatientWaitingHealNumberKey);
        if(number!=null){
            vo.setNumber(number);
        }else {     //当前没有患者
            vo.setNumber(0);
        }
        vo.setDepartment(doctorInfo.getDepartment());
        MyWebSocket.pushMessageAboutDoctor(vo);

        return true;
    }

    /**
     * 下线
     * @param doctorId
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteOnlineDoctorFromMongoDB(String doctorId) {

        //(1)将医生的信息从mongoDB中移除
        Query query = Query.query(Criteria.where("doctorId").is(doctorId));
        DeleteResult remove = mongoTemplate.remove(query, onlineDoctorInfo.class);
        System.out.println(remove);
        //(2)记录医生的离线时间
        long currentTime = System.currentTimeMillis();
        String doctorLeftTime = RedisKeyUtil.getDoctorLeftTime(doctorId);
//        redisTemplate.opsForHash().put(doctorLeftTime, doctorId,currentTime);
        redisTemplate.opsForValue().set(doctorLeftTime,currentTime);
        //(4)修改mysql数据库中的医生状态
        int oldState = doctorMapper.queryDoctorState(doctorId);
        int newState=oldState==0?1:0;
        doctorMapper.updateDoctorState(doctorId,newState);
        //(3)将医生离线的消息推送到前端,
        doctorLeftVO left=new doctorLeftVO();
        left.setType("离线");
        left.setDoctorId(doctorId);
        MyWebSocket.pushMessageAboutDoctor(left);

        return true;
    }

    /**
     * 根据科室查询医生
     * @param department
     * @return
     */
    @Override
    public List<onlineDoctorVO> queryOnlineDoctorByDepartment(String department) {

        Query query = Query.query(Criteria.where("department").is(department));
        List<onlineDoctorInfo> doctorInfoList =
                mongoTemplate.find(query, onlineDoctorInfo.class,"OnlineDoctor");
        System.out.println(doctorInfoList);
        List<onlineDoctorVO>voList=new ArrayList<>();
        for(onlineDoctorInfo doctorInfo:doctorInfoList){
            onlineDoctorVO vo=new onlineDoctorVO();
            vo.setType("查询");
            vo.setDoctorId(doctorInfo.getDoctorId());
            vo.setDoctorName(doctorInfo.getDoctorName());
            vo.setPost(doctorInfo.getPost());
            vo.setHospital(doctorInfo.getHospital());

            //获取医生的待接诊患者数量的Redis Key
            String doctorPatientWaitingHealNumberKey =
                    RedisKeyUtil.getDoctorPatientWaitingHealNumberKey(doctorInfo.getDoctorId());
            Integer number = (Integer) redisTemplate.opsForValue().get(doctorPatientWaitingHealNumberKey);
            if(number!=null){
                vo.setNumber(number);
            }else {     //当前没有患者
                vo.setNumber(0);
            }
            vo.setDepartment(doctorInfo.getDepartment());
            voList.add(vo);
        }
        return voList;
    }


}
