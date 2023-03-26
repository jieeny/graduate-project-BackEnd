package com.lgh.controller;


import com.lgh.entity.OrderInfo;
import com.lgh.entity.UpdateOrderInfo;
import com.lgh.service.OrderService;
import helper.jwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import result.Result;
import util.MongoDBKeyUtil;
import util.RedisKeyUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 李广辉
 * @date 2023/3/12 11:55
 */

@RestController
//@RequestMapping("Order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 挂号接口
     * @param doctorId  医生编号
     * @return
     */
    @GetMapping("Registration")
    public Result getRegistration(HttpServletRequest request,
                                  @RequestParam("doctorId") String doctorId){
//        Result registration = orderService.Registration(userId, doctorId);
        //1、获取请求中的token
        String token = request.getHeader("token");
        //2、解析token,获取token中的doctorId
        String userId = jwtHelper.getUserId(token);
        boolean registration = orderService.Registration(userId, doctorId);
        if(registration){
            Map<String,Object>ResultMap=new HashMap<>();
            String message="挂号成功!";
            //通过Redis查询对应的orderID
//            String userWaitingHealKey = RedisKeyUtil.getUserWaitingHealKey(userId);
//            OrderInfo o = (OrderInfo) redisTemplate.opsForValue().get(userWaitingHealKey);
            String userWaitingHealKey = MongoDBKeyUtil.getUserWaitingHealKey();
            Query query = Query.query(Criteria.where("doctorId").is(doctorId));
            List<OrderInfo> list =
                    mongoTemplate.find(query, OrderInfo.class, userWaitingHealKey);
            OrderInfo order=list.get(0);
            //获取订单编号
            String orderId = order.getOrderId();
            //整合信息到返回集合中
            ResultMap.put("message",message);
            ResultMap.put("orderId",orderId);
            ResultMap.put("userId",userId);
            ResultMap.put("doctorId",doctorId);
            ResultMap.put("order",order);
            return Result.ok(ResultMap);
        }else{
            String message="操作失败,服务器错误!";
            Map<String,Object>ResultMap=new HashMap<>();
            ResultMap.put("message",message);
            return Result.fail(ResultMap);
        }
    }

    /**
     * 根据医生ID获取等待接诊的接口
     * @param doctorId
     * @return
     */
    @GetMapping("waitingHealList")
    public Result getWaitingHealList(@RequestParam("doctorId") String doctorId){
        List<OrderInfo> waitingHealList = orderService.getWaitingHealList(doctorId);
        Map<String,Object>resultMap=new HashMap<>();
        String message="操作成功!";
        resultMap.put("OrderList",waitingHealList);
        resultMap.put("message",message);
        return Result.ok(resultMap);
    }

    /**
     * 取消挂号
     * @param doctorId
     * @return
     */
    @GetMapping("CancelRegistration")
    public Result CancelRegistration(HttpServletRequest request,
                                     @RequestParam("doctorId") String doctorId){
        //1、获取请求中的token
        String token = request.getHeader("token");
        //2、解析token,获取token中的doctorId
        String userId = jwtHelper.getUserId(token);
        boolean b = orderService.CancelRegistration(userId, doctorId);
        Map<String,Object>resultMap=new HashMap<>();
        if(b){
            String message="操作成功!";
            resultMap.put("message",message);
            return Result.ok(resultMap);
        }else{
            String message="操作失败,服务器错误!";
            resultMap.put("message",message);
            return Result.ok(resultMap);
        }
    }

    /**
     * 完成接诊
     * @param doctorId
     * @param orderId
     * @return
     */
    @GetMapping("CompletedHealed")
    public Result CompletedHealed(@RequestParam("doctorId") String doctorId,
                                  @RequestParam("orderId") String orderId){
        boolean b = orderService.CompletedHealed(doctorId, orderId);
        Map<String,Object>resultMap=new HashMap<>();
        if(b){
            String message="操作成功!";
            resultMap.put("message",message);
            return Result.ok(resultMap);
        }else{
            String message="操作失败,服务器错误!";
            resultMap.put("message",message);
            return Result.ok(resultMap);
        }
    }

    /**
     * 查询医生待接诊的订单列表接口
     * @return
     */
    @GetMapping("getWaitingHealList")
    public Result GetWaitingHealListByDoctorId(HttpServletRequest request){
        //1、获取请求中的token
        String token = request.getHeader("token");
        //2、解析token,获取token中的doctorId
        String doctorId = jwtHelper.getUserId(token);
        List<OrderInfo> waitingHealList = orderService.getWaitingHealList(doctorId);
        Map<String,Object>resultMap=new HashMap<>();
        resultMap.put("waitingHealList",waitingHealList);
        resultMap.put("message","操作成功!");
        return Result.ok(resultMap);
    }

    /**
     * 查询医生待接诊的订单数量接口
     * @return
     */
    @GetMapping("getWaitingHealNumber")
    public Result GetWaitingHealNumber(HttpServletRequest request){
        //1、获取请求中的token
        String token = request.getHeader("token");
        //2、解析token,获取token中的doctorId
        String doctorId = jwtHelper.getUserId(token);
        List<OrderInfo> waitingHealList = orderService.getWaitingHealList(doctorId);
        int number = waitingHealList.size();
        Map<String,Object>resultMap=new HashMap<>();
        resultMap.put("number",number);
        resultMap.put("message","操作成功!");
        return Result.ok(resultMap);
    }

    /**
     * 查询医生正在接诊的订单列表接口
     * @return
     */
    @GetMapping("getHealingHealList")
    public Result GetHealingListDoctorId(HttpServletRequest request){
        //1、获取请求中的token
        String token = request.getHeader("token");
        //2、解析token,获取token中的doctorId
        String doctorId = jwtHelper.getUserId(token);
        List<OrderInfo> healingList = orderService.getHealingList(doctorId);
        Map<String,Object>resultMap=new HashMap<>();
        resultMap.put("healingList",healingList);
        resultMap.put("message","操作成功!");
        return Result.ok(resultMap);
    }

    /**
     * 查询医生正在接诊的订单数量接口
     * @return
     */
    @GetMapping("getHealingNumber")
    public Result GetHealingNumber(HttpServletRequest request){
        //1、获取请求中的token
        String token = request.getHeader("token");
        //2、解析token,获取token中的doctorId
        String doctorId = jwtHelper.getUserId(token);
        List<OrderInfo> healingList = orderService.getHealingList(doctorId);
        int number = healingList.size();
        Map<String,Object>resultMap=new HashMap<>();
        resultMap.put("number",number);
        resultMap.put("message","操作成功!");
        return Result.ok(resultMap);
    }

    /**
     * 查询医生已完成接诊的订单列表接口
     * @param doctorId
     * @return
     */
    @GetMapping("getHealedList")
    public Result GetHealedListByDoctorId(@RequestParam("doctorId") String doctorId){
        List<OrderInfo> healedList = orderService.getHealedList(doctorId);
        Map<String,Object>resultMap=new HashMap<>();
        resultMap.put("headList",healedList);
        resultMap.put("message","操作成功!");
        return Result.ok(resultMap);
    }

    /**
     * 获取用户待接诊的订单接口
     * @param userId
     * @return
     */
    @GetMapping("UserWaitingHealOrder")
    public Result GetUserWaitingHealOrder(@RequestParam("userId")String userId){
        OrderInfo order = orderService.getUserWaitingHealOrder(userId);
        Map<String,Object>resultMap=new HashMap<>();
        resultMap.put("message","操作成功!");
        resultMap.put("order",order);
        return Result.ok(resultMap);
    }

    /**
     * 获取用户已完成接诊的订单的接口
     * @param userId
     * @return
     */
    @GetMapping("UserCompleteHealOrder")
    public Result GetUserCompleteHealOrder(@RequestParam("userId")String userId){
        List<OrderInfo> userCompleteHealOrder = orderService.getUserCompleteHealOrder(userId);
        Map<String,Object>resultMap=new HashMap<>();
        resultMap.put("message","操作成功!");
        resultMap.put("OrderList",userCompleteHealOrder);
        return Result.ok(resultMap);
    }

    /**
     * 更新订单的状态(0:待接诊->1:正在接诊;1:正在接诊->2:完成接诊)
     * @param orderId
     * @param orderState        在原来的state基础上+1
     * @return
     */
    @GetMapping("UpdateOrderState")
    public Result UpdateOrderState(@RequestParam("StringId")String doctorId,
                                   @RequestParam("orderId")String orderId,
                                   @RequestParam("state")int orderState){

        int i = orderService.updateOrderState(doctorId, orderId, orderState);
        if(i<1){
            Map<String,Object>ResultMap=new HashMap<>();
            ResultMap.put("message","操作失败!");
            return Result.ok(ResultMap);
        }else {
            Map<String,Object>ResultMap=new HashMap<>();
            ResultMap.put("message","操作成功!");
            ResultMap.put("orderId",orderId);
            ResultMap.put("newState",orderState+1);
            return Result.ok(ResultMap);
        }
    }

    /**
     * 更新订单信息
     * @return
     */
    @PostMapping("UpdateOrderInfo")
    public Result UpdateOrderInfo(@RequestBody UpdateOrderInfo orderInfo){



        Map<String,Object>ResultMap=new HashMap<>();
        return Result.ok(ResultMap);
    }

}
