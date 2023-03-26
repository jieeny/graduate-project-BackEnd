package com.lgh.service;

import cn.hutool.db.sql.Order;
import com.lgh.entity.OrderInfo;
import com.lgh.entity.UpdateOrderInfo;
import org.springframework.data.mongodb.core.query.Update;
import result.Result;

import java.util.List;

/**
 * @author 李广辉
 * @date 2023/2/21 15:18
 */
public interface OrderService {

    //挂号
    public boolean Registration(String patientId, String doctorId);

    //取消挂号
    public boolean CancelRegistration(String patientId,String doctorId);

    //完成接诊
    public boolean CompletedHealed(String doctorId,String orderId);

    //根据医生ID查询所有的待接诊订单
    public List<OrderInfo> getWaitingHealList(String doctorId);

    //根据医生ID查询所有正在接诊的订单
    public List<OrderInfo> getHealingList(String doctorId);

    //根据医生ID查询所有已经接诊的订单
    public List<OrderInfo> getHealedList(String doctorId);

    //根据医生ID查询所有待开方的订单
    public List<OrderInfo> getWaitingPrescribeList(String doctorId);

    //根据医生ID查询所有已开处方的订单
    public List<OrderInfo> getPrescribeList(String doctorId);

    //根据用户ID查询所有待/正在接诊的订单
    public OrderInfo getUserWaitingHealOrder(String userId);

    //根据用户ID查询所有已经完成的订单
    public List<OrderInfo> getUserCompleteHealOrder(String userId);

    //根据订单ID查询对应的处方
    //ToDo


    //更改订单的状态
    public int updateOrderState(String doctorId,String orderId,int orderState);

    public boolean updateOrderInfo(UpdateOrderInfo orderInfo);
}
