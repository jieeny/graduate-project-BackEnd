package com.lgh.WebSocket;

import com.lgh.entity.onlineDoctorVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 李广辉
 * @date 2023/2/21 16:51
 */

@ServerEndpoint(value = "/websocket/{userId}",encoders = {WebSocketEncoder.class})
@Component
public class MyWebSocket {

    //保存所有在线的socket连接
    private static Map<String,MyWebSocket>webSocketMap=new LinkedHashMap<>();

    //记录当前在线的数目
    private static int count=0;

    //当前连接(每个websocket连入都会创建一个websocket实例)
    private Session session;

    private Logger log= LoggerFactory.getLogger(MyWebSocket.class);

    /**
     * 处理连接建立
     * @param session
     */
    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        webSocketMap.put(session.getId(),this);
        addCount();
        log.info("新的连接加入:{}",session.getId());
    }

    @OnMessage
    public void onMessage(String message,Session session){
        log.info("收到客户端{}消息:{}",session.getId(),message);
        try{
            this.sendMessage("收到消息: "+message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 处理错误
     * @param error
     * @param session
     */
    @OnError
    public void onError(Throwable error,Session session){
        log.info("发生错误:{},{}",session.getId(),error.getMessage());
    }

    /**
     * 关闭
     */
    @OnClose
    public void onClose(){
        webSocketMap.remove(this.session.getId());
        reduceCount();
        log.info("连接关闭:{}",this.session.getId());
    }

    /**
     * 发送消息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 广播消息
     */
    public static void broadcast(){
        MyWebSocket.webSocketMap.forEach((k,v)->{
            try{
                v.sendMessage("这是一条广播消息");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 推送医生上线的消息到前端
     * @param vo
     * @throws EncodeException
     * @throws IOException
     */
    public void pushMessage(Object vo) throws EncodeException, IOException {
        System.out.println("执行了pushMessage()方法!");
        this.session.getBasicRemote().sendObject(vo);
    }


    /**
     * 推送医生上线的消息到前端
     * @param vo
     */
    public static void pushMessageAboutDoctor(Object vo){
        MyWebSocket.webSocketMap.forEach((k,v)->{
            try{
                v.pushMessage(vo);
            } catch (EncodeException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


    //获取在线连接数目
    public static int getCount(){
        return count;
    }

    public static synchronized void addCount(){
        MyWebSocket.count++;
    }

    public static synchronized void reduceCount(){
        MyWebSocket.count--;
    }

}
