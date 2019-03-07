package com.chezhibao.bigdata.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class CrawlMessageHandler extends TextWebSocketHandler {


    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     *
     */
    private static AtomicInteger clients=new AtomicInteger(0);

    private static CopyOnWriteArraySet<WebSocketSession> webSocketSet = new CopyOnWriteArraySet<>();

    public static CopyOnWriteArraySet<WebSocketSession> getWebSocketSet(){
        return webSocketSet;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        webSocketSet.remove(session);
        System.out.println("close....");
        int i = clients.addAndGet(-1);
        log.info("建立新的会话,当前在线人数："+i);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        webSocketSet.add(session);
        int i = clients.addAndGet(1);
        log.info("建立新的会话,当前在线人数："+i);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.debug("来自客户端的消息:" + message);
        if("close".equals(message.getPayload())){
            session.close();
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

}