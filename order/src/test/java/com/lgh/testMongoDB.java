package com.lgh;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.C;
import com.lgh.entity.OrderInfo;
import com.lgh.service.OrderService;
import com.mongodb.client.result.UpdateResult;
import helper.UUIDHelper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import util.MongoDBKeyUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 李广辉
 * @date 2023/3/18 15:35
 */

@SpringBootTest
public class testMongoDB {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testObject(){
//        Query userId =
//                Query.query(Criteria.where("userId").is("1"));
//        Query orderId =
//                Query.query(Criteria.where("orderId").is("6d29834efd67467ea818857e9080d59e"));
//
//        String userWaitingHealKey = MongoDBKeyUtil.getUserWaitingHealKey("1");
//        List<OrderInfo> list = mongoTemplate.find(userId, OrderInfo.class, userWaitingHealKey);
//        System.out.println(list);
//        List<OrderInfo> list1 = mongoTemplate.find(orderId, OrderInfo.class, userWaitingHealKey);
//        System.out.println(list1);

        String doctorPatientWaitingHealKey =
                MongoDBKeyUtil.getDoctorPatientWaitingHealKey("1");
        Query query = Query.query(Criteria.where("doctorId").is("1"));
        List<OrderInfo> objects = mongoTemplate.find(query, OrderInfo.class, doctorPatientWaitingHealKey);
        for (OrderInfo orderInfo:objects){
            long time=orderInfo.getOrderTime();
            String timeString=String.valueOf(time);
            System.out.println(time);
            System.out.println(timeString);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(new Date(time));
            System.out.println(format);
        }
    }

    @Test
    public void testUpdate()
    {
        String orderId="c8a3871f225b4fdbb22eb675cacb3b64";
        String doctorId="1";
        String doctorPatientWaitingHealKey = MongoDBKeyUtil.getDoctorPatientWaitingHealKey(doctorId);
        Criteria c=new Criteria();
        Query query = Query.query(Criteria.where("doctorId").is(doctorId).and("orderId").is(orderId));
//        List<OrderInfo> list = mongoTemplate.find(query, OrderInfo.class, doctorPatientWaitingHealKey);
//        System.out.println(list);
        Update update=new Update();
        update.set("orderState",1);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, OrderInfo.class,doctorPatientWaitingHealKey);
        System.out.println(updateResult.getModifiedCount());
    }

    @Test
    public void testRegistration(){
        for(int i=2;i<=15;i++){
            //(3)构造订单对象
            //获取当前时间
            OrderInfo order=new OrderInfo();
            //设置订单的时间
            order.setOrderTime(System.currentTimeMillis());
            //生成订单的编号
            order.setOrderId(UUIDHelper.getRandomUUID());
            String doctorId="1";
            //设置订单的下单者ID以及医生ID
            order.setDoctorId(doctorId);
            //设置订单的下单者
            String patientId=String.valueOf(i);
            order.setUserId(patientId);
            //设置订单的状态: 0->待接诊
            order.setOrderState(0);
            order.setDoctorName("");
            order.setUsername("");
            order.setAllergies("青霉素过敏,其他无");
            order.setPatientAge("22");
            order.setPatientName("李广辉"+i);
            order.setDescription("前天拉肚子，喉咙痛，然后前天晚上和昨天都喝了复方金银花，昨天吃了藿香正气口服液，" +
                    "和复方木香小辟碱片，昨天开始鼻塞，喉咙有点痒，痰是绿色的。");
            order.setPatientSex("男");
            order.setDepartmentName("呼吸内科");
            String doctorPatientWaitingHealKey =
                    MongoDBKeyUtil.getDoctorPatientWaitingHealKey(doctorId);
            mongoTemplate.save(order,doctorPatientWaitingHealKey);
        }

    }

    @Autowired
    private OrderService orderService;

    @Test
    public void testGetWaitingHealList(){
        List<OrderInfo> waitingHealList = orderService.getWaitingHealList("1");
        for(OrderInfo orderInfo:waitingHealList){
            System.out.println(orderInfo);
        }
    }

    @Test
    public void testGetHealingList(){
        List<OrderInfo> healingList = orderService.getHealingList("1");
        for(OrderInfo orderInfo:healingList){
            System.out.println(orderInfo);
        }
    }

}
