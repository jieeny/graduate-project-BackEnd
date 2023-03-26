package com.lgh.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lgh.Utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 李广辉
 * @date 2023/2/12 15:40
 */

@ServerEndpoint("/chat/{userId}")
@Component
public class ChatWebSocketController {

    private final Logger logger= LoggerFactory.getLogger(ChatWebSocketController.class);

    //onlineCount: 在线连接数
    private static AtomicInteger onlineCount=new AtomicInteger(0);

    //webSocketSet: 用来存放每个客户端对应的MyWebSocket对象
    public static List<ChatWebSocketController>webSocketSet=new ArrayList<ChatWebSocketController>();

    //存入所有连接人信息
    public static List<String> userList=new ArrayList<String>();

    //与某个客户端的连接会话,需要通过它来给客户端发送数据
    private Session session;

    //用户ID
    public String userId="";


    /**
     * 连接建立成功调用的方法
     * @param session
     * @param userId
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId")String userId){
        this.session=session;
        this.userId=userId;
        userList.add(userId);
        System.out.println(userList);
        //加入set中
        webSocketSet.add(this);
        //在线数加1
        onlineCount.incrementAndGet();
        logger.info("有新连接加入!"+userId+"当前用户数为: "+onlineCount.get());
        JSONObject msg=new JSONObject();
        try{
            msg.put("msg","连接成功!");
            msg.put("status","SUCCESS");
            msg.put("userId",userId);
            sendMessage(JSON.toJSONString(msg));
        }catch (Exception e){
            logger.debug("IO异常!");
        }
    }

    /**
     * 连接关闭调用的方法
     * @param userId
     */
    public void onClose(@PathParam("userId")String userId){
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        onlineCount.decrementAndGet();
        logger.info("用户"+userId+"退出聊天!当前在线用户数为: "+onlineCount.get());
    }

    public synchronized void sendMessage(String message){
        this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 收到客户端消息后调用得到方法
     *
     * @param message
     * @param userId
     */
    @OnMessage
    public void onMessage(String message,@PathParam("userId")String userId){
        //客户端输入的消息message要经过处理后封装成新的message,后端拿到新的消息后进行数据解析,然后判断
        //是单发还是群发,并调用对应的方法
        logger.info("来自客户端"+userId+"的消息: "+message);
        try{
//            MyMessage myMessage=JSON.parseObject(message,MyMessage.class);
//            String messageContent= myMessage.getMessage();  //messageContent：真正的消息内容
//            String messageType= myMessage.getMessageType();
//            if("1".equals(messageType)){        //单聊
//                String recUser= myMessage.getUserId();      //recUser: 消息接收者
//                sendInfo(messageContent,recUser,userId);    //messageContent: 输入框实际内容 recUser：消息接收者  userId 消息发送者
//            }else {         //群聊
//                sendGroupInfo(messageContent,userId);       //messageContent：输入框实际内容 userId 消息发送者
//            }
            String messageType="2";                             //这里都是群聊
            String messageContent=message;
            sendGroupInfo(messageContent,userId);
        }catch (Exception e){
            logger.error("解析失败: {}",e);
        }
    }

    /**
     * 发生错误时调用的方法
     * @param error
     */
    @OnError
    public void onError(Throwable error){
        logger.debug("websocket 发生错误!");
        error.printStackTrace();
    }


    /**
     * 单聊
     * @param message: 消息内容,输入的实际内容,不是拼接后的内容
     * @param recUser: 消息接收者
     * @param sendUser: 消息发送者
     */
    public void sendInfo(String message,String recUser,String sendUser){
        JSONObject msgObject=new JSONObject();      //msgObject包含发送者信息的消息
        for (ChatWebSocketController item:webSocketSet){
            if(StringUtil.equals(item.userId,recUser)){
                logger.info("给用户"+recUser+"传递消息: "+message);
                //拼接返回的消息,除了输入的实际内容,还要包含发送者信息
                msgObject.put("message",message);
                msgObject.put("sendUser",sendUser);
                item.sendMessage(JSON.toJSONString(msgObject));
            }
        }
    }

    /**
     * 群聊
     * @param message
     * @param sendUser
     */
    public void sendGroupInfo(String message,String sendUser){
        JSONObject msgObject=new JSONObject();
        if(StringUtil.isNotEmpty(webSocketSet)){
            for (ChatWebSocketController item: webSocketSet){
                //排除给发送者自身回送消息
                if(!StringUtil.equals(item.userId,sendUser)){
                    logger.info("回送消息: "+message);
                    //拼接返回的消息,除了输入的实际内容,还要包含发送者信息
                    msgObject.put("message",message);
                    msgObject.put("sendUser",sendUser);
                    item.sendMessage(JSON.toJSONString(msgObject));
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if(this==o){
            return true;
        }
        if(o==null||getClass()!=o.getClass()){
            return false;
        }
        ChatWebSocketController that= (ChatWebSocketController) o;
        return Objects.equals(session,that.session);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(session);
    }

}
