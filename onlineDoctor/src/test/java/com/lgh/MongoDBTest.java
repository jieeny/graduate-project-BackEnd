package com.lgh;

import com.lgh.entity.onlineDoctorInfo;
import com.lgh.entity.onlineDoctorVO;
import com.lgh.runner.InitDataCache;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import helper.UUIDHelper;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李广辉
 * @date 2023/2/20 17:17
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MongoDBTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testCollection(){
        boolean emp = mongoTemplate.collectionExists("emp");
        if(emp){
            System.out.println(emp);
            mongoTemplate.dropCollection("emp");
        }
        //创建集合
        mongoTemplate.createCollection("emp");
    }

    @Test
    public void testInsertMongoDB(){

//        Object[] objects = InitDataCache.cacheSet.toArray();
        Object[] objects = InitDataCache.cacheSet.toArray();
        for(onlineDoctorInfo doctorInfo:InitDataCache.cacheSet){
            onlineDoctorInfo emp = mongoTemplate.save(doctorInfo, "OnlineDoctor");
            System.out.println(emp);
        }
//        for (onlineDoctorInfo doctorInfo:objects){
//            onlineDoctorInfo emp = mongoTemplate.save(doctorInfo, "OnlineDoctor");
//            System.out.println(emp);
//        }
//        onlineDoctorInfo doctorInfo=new onlineDoctorInfo();
//        doctorInfo.setId(UUIDHelper.getRandomUUID());


    }

    @Test
    public void testRemoveMongoDB(){
        Query query = Query.query(
                Criteria.where("doctorId").is("1"));
        DeleteResult remove = mongoTemplate.remove(query, onlineDoctorInfo.class);
        System.out.println(remove);
    }

    @Test
    public void testSelectMongoDB(){
        Query query = Query.query(Criteria.where("department").is("呼吸内科"));
        List<onlineDoctorInfo> doctorInfoList =
                mongoTemplate.find(query, onlineDoctorInfo.class,"OnlineDoctor");
//        List<onlineDoctorVO>voList=new ArrayList<>();
//        for(onlineDoctorInfo doctorInfo:doctorInfoList){
//            onlineDoctorVO vo=new onlineDoctorVO();
//            vo.setType("查询");
//            vo.setDoctorId(doctorInfo.getDoctorId());
//            vo.setDoctorName(doctorInfo.getDoctorName());
//            vo.setDoctorPost(doctorInfo.getDoctorPost());
//            vo.setHosp(doctorInfo.getHosp());
//            vo.setNumber((Integer) redisTemplate.opsForHash().get("doctorNumber",doctorInfo.getDoctorId()));
//            vo.setDepartmentName(doctorInfo.getDepartmentName());
//            voList.add(vo);
//        }
        System.out.println(doctorInfoList);
    }

}
