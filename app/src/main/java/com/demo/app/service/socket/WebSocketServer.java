package com.demo.app.service.socket;

import org.springframework.stereotype.Component;
import util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 类功能描述:　webSocket服务层
 * 这里我们连接webSocket的时候，路径中传一个参数值id，用来区分不同页面推送不同的数据
 *
 * @author fbl
 * @date 2019-02-13 8:02
 */
@ServerEndpoint(value = "/webSocket")
@Component
public class WebSocketServer {
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */

    public static CopyOnWriteArrayList<WebSocketServer> webSocketList = new CopyOnWriteArrayList<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        /**加入set中*/
        webSocketList.add(this);
        /**在线数加1*/
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        try {
            sendMessage("{ \"message\":\"恭喜您，连接成功\"}");
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
            /** 从set中删除 */
            webSocketList.remove(this);
            /** 在线数减1 */
            subOnlineCount();
            System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());

    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        if(StringUtils.isEmpty(message)){
            message = "{ \"message\":\"恭喜您，连接成功\"}";
        }else{
            String newMsg = message.replaceAll("\"", "");
            message = "{ \"message\": \""+ newMsg +"\"}";
        }
        try {
            this.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发生错误时调用
     **/
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        synchronized (session) {
            getSession().getBasicRemote().sendText(message);
        }
    }



    public Session getSession() {
        return session;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
