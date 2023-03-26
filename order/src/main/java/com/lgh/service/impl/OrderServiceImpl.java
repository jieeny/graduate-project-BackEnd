package com.lgh.service.impl;

import com.lgh.config.RedissonConfig;
import com.lgh.entity.OrderInfo;
import com.lgh.entity.UpdateOrderInfo;
import com.lgh.mapper.OrderMapper;
import com.lgh.service.OrderService;
import com.mongodb.client.result.UpdateResult;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import helper.UUIDHelper;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import result.Result;
import util.MongoDBKeyUtil;
import util.RedisKeyUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author 李广辉
 * @date 2023/2/21 15:22
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    RedissonClient redissonClient;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 挂号
     * @param patientId
     * @param doctorId
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public boolean Registration(String patientId, String doctorId) {


        //首先使用分布式锁锁主doctorId对应的zset
        RLock lock = redissonClient.getLock(doctorId+"Redisson");
        lock.lock(100,TimeUnit.SECONDS);
        System.out.println("获取锁--" + Thread.currentThread().getId());
        try{
            //这里写业务
            //(1)获取医生的号源
            String doctorRegistrationNumberKey =
                    RedisKeyUtil.getDoctorRegistrationNumberKey(doctorId);
            Integer doctorRegistrationNumber
                    = (Integer) redisTemplate.opsForValue().get(doctorRegistrationNumberKey);
            if(doctorRegistrationNumber<=0){
                return false;
            }
            //(2)一个人每次只能挂一个号
            //获取当前医生的待接诊患者的ID集合
            String doctorPatientWaitingHealSet =
                    RedisKeyUtil.getDoctorPatientWaitingHealSet(doctorId);
            if(redisTemplate.opsForSet().isMember(doctorPatientWaitingHealSet, patientId)){
                return false;
            }
            redisTemplate.opsForSet().add(doctorPatientWaitingHealSet,patientId);

            //(3)构造订单对象
            //获取当前时间
            OrderInfo order=new OrderInfo();
            //设置订单的时间
            order.setOrderTime(System.currentTimeMillis());
            //生成订单的编号
            order.setOrderId(UUIDHelper.getRandomUUID());
            //设置订单的下单者ID以及医生ID
            order.setDoctorId(doctorId);
            //设置订单的下单者
            order.setUserId(patientId);
            //设置订单的状态: 0->待接诊
            order.setOrderState(0);
            order.setDoctorName("");
            order.setUsername("");
            order.setAllergies("");
            order.setPatientAge("");
            order.setDescription("");
            order.setPatientSex("");
            order.setDepartmentName("");

            //设置订单对应的科室？？
            //获取对应医生的待接诊的患者集合
//            String doctorPatientKey = RedisKeyUtil.getDoctorPatientKey(doctorId);
//            String doctorPatientWaitingHealZSetKey =
//                    RedisKeyUtil.getDoctorPatientWaitingHealZSetKey(doctorId);
//            Boolean add = redisTemplate.opsForZSet().add(doctorPatientWaitingHealZSetKey, order, order.getOrderTime());
//            if(add){
//                //计算当前医生对应的患者数量
//                String doctorPatientWaitingHealNumberKey =
//                        RedisKeyUtil.getDoctorPatientWaitingHealNumberKey(doctorId);
//                Integer number = (Integer) redisTemplate.opsForValue().
//                        get(doctorPatientWaitingHealNumberKey);
//                //当前没有患者
//                if(number==null){
//                    redisTemplate.opsForValue().set(doctorPatientWaitingHealNumberKey,1);
//                }else{
//                    redisTemplate.opsForValue().set(doctorPatientWaitingHealNumberKey,1+number);
//                }
//
//                //医生号源减一
//                redisTemplate.opsForValue().
//                        set(doctorRegistrationNumberKey,doctorRegistrationNumber-1);
//
//                //设置对应用户的待接诊订单的缓存
//                String userWaitingHealKey = RedisKeyUtil.getUserWaitingHealKey(patientId);
//                redisTemplate.opsForValue().set(userWaitingHealKey,order);
//                return true;
//            }
//            return false;

            //将订单加入到mongoDB数据库中
            //构造医生待接诊的患者的mongoDB集合的Key

            //(4)医生的待接诊订单的集合
            String doctorPatientWaitingHealKey =
                    MongoDBKeyUtil.getDoctorPatientWaitingHealKey(doctorId);
            mongoTemplate.save(order,doctorPatientWaitingHealKey);

            //(5)计算当前医生对应的患者数量
            String doctorPatientWaitingHealNumberKey =
                        RedisKeyUtil.getDoctorPatientWaitingHealNumberKey(doctorId);
                Integer number = (Integer) redisTemplate.opsForValue().
                        get(doctorPatientWaitingHealNumberKey);

            //当前没有患者
            if(number==null){
                redisTemplate.opsForValue().set(doctorPatientWaitingHealNumberKey,1);
            }else{
                redisTemplate.opsForValue().set(doctorPatientWaitingHealNumberKey,1+number);
            }

            //(6)医生号源减一
            redisTemplate.opsForValue().set(doctorRegistrationNumberKey,doctorRegistrationNumber-1);

            //(7)在线医生处的患者数量发生变化


            //(8)设置对应用户的待接诊订单的缓存
            String userWaitingHealKey =
                    MongoDBKeyUtil.getUserWaitingHealKey();
            mongoTemplate.save(order,userWaitingHealKey);

            return true;
        }finally {
            lock.unlock();
        }

    }

    /**
     * 取消挂号
     * @param patientId
     * @param doctorId
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public boolean CancelRegistration(String patientId, String doctorId) {
        //首先使用分布式锁锁主doctorId对应的zset
        RLock lock = redissonClient.getLock(doctorId+"Redisson");
        lock.lock(100,TimeUnit.SECONDS);
        try{
            //这里写业务
            //获取医生的号源并+1
            String doctorRegistrationNumberKey =
                    RedisKeyUtil.getDoctorRegistrationNumberKey(doctorId);
            Integer doctorRegistrationNumber
                    = (Integer) redisTemplate.opsForValue().get(doctorRegistrationNumberKey);
            redisTemplate.opsForValue().
                    set(doctorRegistrationNumberKey,doctorRegistrationNumber+1);
            String doctorPatientWaitingHealZSetKey
                    = RedisKeyUtil.getDoctorPatientWaitingHealZSetKey(doctorId);

            //从医生的待接诊订单集合中移除
            String doctorPatientWaitingHealKey
                    = MongoDBKeyUtil.getDoctorPatientWaitingHealKey(doctorId);
            Query query = Query.query(Criteria.where("userId").is(patientId));
            mongoTemplate.remove(query,OrderInfo.class,doctorPatientWaitingHealKey);

            //从用户的待接诊集合中移除该订单
            String userWaitingHealKey
                    = MongoDBKeyUtil.getUserWaitingHealKey();
            mongoTemplate.remove(query,OrderInfo.class,userWaitingHealKey);

            //从医生的待接诊的患者ID集合中移除
            String doctorPatientWaitingHealSet =
                    RedisKeyUtil.getDoctorPatientWaitingHealSet(doctorId);
            redisTemplate.opsForSet().remove(doctorPatientWaitingHealSet,patientId);

            //医生当前的患者数量减一
            String doctorPatientWaitingHealNumberKey
                    = RedisKeyUtil.getDoctorPatientWaitingHealNumberKey(doctorId);
            Integer patientNumber = (Integer) redisTemplate.opsForValue().get(doctorPatientWaitingHealNumberKey);
            redisTemplate.opsForValue().set(doctorPatientWaitingHealNumberKey,patientNumber-1);
            return true;
        }
        finally {

            if(null!=lock&& lock.isLocked()){       //判断锁是否还存在
                if(lock.isHeldByCurrentThread()){   //判断锁的拥有者是否为当前的线程
                    lock.unlock();
                }
            }
        }
    }

    /**
     * 完成订单=>完成接诊
     * @param doctorId
     * @param orderId
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public boolean CompletedHealed(String doctorId,String orderId) {
        //从医生的待接诊的患者集合中移除
        String doctorPatientWaitingHealKey
                = MongoDBKeyUtil.getDoctorPatientWaitingHealKey(doctorId);
        Query query = Query.query(Criteria.where("orderId").is(orderId));
        List<OrderInfo> list
                = mongoTemplate.find(query, OrderInfo.class, doctorPatientWaitingHealKey);
        //获取已完成的订单
        OrderInfo completedOrder=list.get(0);
        if(completedOrder==null){
            return false;
        }
        mongoTemplate.remove(query,OrderInfo.class,doctorPatientWaitingHealKey);

        //从医生的待接诊的患者ID集合中移除
        String doctorPatientWaitingHealSet =
                RedisKeyUtil.getDoctorPatientWaitingHealSet(doctorId);
        redisTemplate.opsForSet().remove(doctorPatientWaitingHealSet,orderId);

        //设置订单的状态=>待开处方状态
        completedOrder.setOrderState(2);

        //将订单缓存到到医生的待开方集合中
        String doctorWaitingPrescribeKey = MongoDBKeyUtil.getDoctorWaitingPrescribeKey();
        mongoTemplate.save(completedOrder,doctorWaitingPrescribeKey);

        //将用户的待接诊订单移除
        String userWaitingHealKey = MongoDBKeyUtil.getUserWaitingHealKey();
        mongoTemplate.remove(OrderInfo.class,userWaitingHealKey);

        //医生当前的患者数量减一
        String doctorPatientWaitingHealNumberKey
                = RedisKeyUtil.getDoctorPatientWaitingHealNumberKey(doctorId);
        Integer patientNumber = (Integer) redisTemplate.opsForValue().get(doctorPatientWaitingHealNumberKey);
        redisTemplate.opsForValue().set(doctorPatientWaitingHealNumberKey,patientNumber-1);


        return true;
    }

    /**
     * 根据医生ID查询所有的待接诊订单
     * @param doctorId
     * @return
     */
    @Override
    public List<OrderInfo> getWaitingHealList(String doctorId) {
        String doctorPatientWaitingHealKey =
                MongoDBKeyUtil.getDoctorPatientWaitingHealKey(doctorId);
        Query query = Query.query(Criteria.where("doctorId").is(doctorId).and("orderState").is(0));
        List<OrderInfo> orderInfoList =
                mongoTemplate.find(query, OrderInfo.class, doctorPatientWaitingHealKey);
        return orderInfoList;
    }

    /**
     * 根据医生ID查询所有的正在接诊订单
     * @param doctorId
     * @return
     */
    @Override
    public List<OrderInfo> getHealingList(String doctorId) {
        String doctorPatientWaitingHealKey =
                MongoDBKeyUtil.getDoctorPatientWaitingHealKey(doctorId);
        Query query = Query.query(Criteria.where("doctorId").is(doctorId).and("orderState").is(1));
        List<OrderInfo> orderInfoList =
                mongoTemplate.find(query, OrderInfo.class, doctorPatientWaitingHealKey);
        return orderInfoList;
    }

    /**
     * 根据医生ID查询所有已经完成接诊的订单
     * @param doctorId
     * @return
     */
    @Override
    public List<OrderInfo> getHealedList(String doctorId) {

        //已接诊的订单可能存在于缓存中,也可能存在于数据库中
        //首先查询缓存是否存有数据
//        String doctorCompletedHealed = RedisKeyUtil.getDoctorCompletedHealed(doctorId);
//        List<OrderInfo> list = (List<OrderInfo>) redisTemplate.opsForValue().get(doctorCompletedHealed);
//        if(list!=null){
//            return list;
//        }

        //然后查询数据库
        List<OrderInfo> orderInfoList = orderMapper.queryOrderListByDoctorId(doctorId);
        return orderInfoList;
    }

    //根据医生ID查询所有待开方的订单
    //ToDO
    @Override
    public List<OrderInfo> getWaitingPrescribeList(String doctorId) {
        return null;
    }

    //根据医生ID查询所有已开处方的订单
    //ToDo
    @Override
    public List<OrderInfo> getPrescribeList(String doctorId) {
        return null;
    }

    //Todo
    /**
     * 根据用户ID获取用户等待接诊的订单
     * @param userId
     * @return
     */
    @Override
    public OrderInfo getUserWaitingHealOrder(String userId) {

        String userWaitingHealKey = RedisKeyUtil.getUserWaitingHealKey(userId);
        OrderInfo order = (OrderInfo) redisTemplate.opsForValue().get(userWaitingHealKey);

        return order;
    }

    //Todo
    /**
     * 根据用户编号获取用户已经完成的订单的列表
     * @param userId
     * @return
     */
    @Override
    public List<OrderInfo> getUserCompleteHealOrder(String userId) {

        //首先去缓存查询
//        String patientCompleteHealed = RedisKeyUtil.getPatientCompleteHealed(userId);
//        List<OrderInfo> list = (List<OrderInfo>) redisTemplate.opsForValue().get(patientCompleteHealed);
//        if(list!=null){
//            return list;
//        }

        //如果缓存不存在则去mysql数据库中查询
        List<OrderInfo> orderInfoList = orderMapper.queryOrderListByUserId(userId);

        //设置缓存,过期时间为24小时
//        redisTemplate.opsForValue().set(patientCompleteHealed,orderInfoList,24,TimeUnit.HOURS);

        return orderInfoList;
    }

    @Override
    public int updateOrderState(String doctorId,String orderId,int orderState) {

        //新的订单状态
        int newOrderState=orderState+1;
        //根据订单的编号更新订单的状态
        String doctorPatientWaitingHealKey = MongoDBKeyUtil.getDoctorPatientWaitingHealKey(doctorId);
        Query query = Query.query(Criteria.where("doctorId").is(doctorId).and("orderId").is(orderId));
        Update update=new Update();
        update.set("orderState",newOrderState);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, OrderInfo.class,doctorPatientWaitingHealKey);
        System.out.println(updateResult.getModifiedCount());
        return (int) updateResult.getModifiedCount();
    }

    @Override
    public boolean updateOrderInfo(UpdateOrderInfo orderInfo) {

//        String orderId=orderInfo.get

        return true;
    }


}
