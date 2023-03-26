package com.lgh.mapper;

import cn.hutool.db.sql.Order;
import com.lgh.entity.OrderInfo;
import com.lgh.service.OrderService;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 李广辉
 * @date 2023/3/8 21:48
 */
@Mapper
public interface OrderMapper {

    //根据订单编号查询订单
    OrderInfo queryOrderByOrderId(String orderId);

    //根据下单者编号查询订单列表
    List<OrderInfo> queryOrderListByUserId(String userId);

    //根据医生编号查询订单
    List<OrderInfo> queryOrderListByDoctorId(String doctorId);

    //插入一份订单
    Integer insertOrder(OrderInfo order);
}
