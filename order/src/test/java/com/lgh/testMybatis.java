package com.lgh;

import com.lgh.entity.OrderInfo;
import com.lgh.mapper.OrderMapper;
import helper.UUIDHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author 李广辉
 * @date 2023/3/8 22:28
 */

@SpringBootTest
public class testMybatis {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void insertOrder(){
        OrderInfo order=new OrderInfo();
        order.setOrderId(UUIDHelper.getRandomUUID());
        order.setOrderState(1);
        order.setOrderTime(System.currentTimeMillis());
        order.setDoctorId("3");
        order.setDoctorName("李四");
        order.setAllergies("无");
        order.setUserId("1");
        order.setUsername("admin");
        order.setDescription("third test");
        order.setDepartmentName("内科");
        order.setPatientName("李广辉");
        order.setPatientAge("23");
        order.setPatientSex("男");
        Integer integer = orderMapper.insertOrder(order);
        System.out.println(integer);

    }

    @Test
    public void testQueryOrderListByUserId(){
        String userId="1";
        List<OrderInfo> list = orderMapper.queryOrderListByUserId(userId);
        System.out.println(list);
    }

    @Test
    public void testQueryOrderListByDoctorId(){
        String doctorId="1";
        List<OrderInfo> list = orderMapper.queryOrderListByDoctorId(doctorId);
        System.out.println(list);
    }

    @Test
    public void testQueryOrderByOrderId(){
        String orderId="059ed84a80ad45bfab735d1bc53600cb";
        OrderInfo orderInfo = orderMapper.queryOrderByOrderId(orderId);
        System.out.println(orderInfo);
    }

}
