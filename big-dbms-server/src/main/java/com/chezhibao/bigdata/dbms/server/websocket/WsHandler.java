package com.chezhibao.bigdata.dbms.server.websocket;

import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.dbms.server.service.download.DataDownload;
import com.chezhibao.bigdata.dbms.server.service.download.DownloadFactory;
import com.chezhibao.bigdata.dbms.server.vo.DbSqlExecuteVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
public class WsHandler extends TextWebSocketHandler
{
    @Autowired
    private DownloadFactory factory;

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception
    {
        super.afterConnectionClosed(session, status);
        System.out.println("close....");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception
    {
        super.afterConnectionEstablished(session);
        System.out.println("建立新的会话");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception
    {
        log.debug("来自客户端的消息:" + message);
        DbSqlExecuteVO dbSqlExecuteVO = JSON.parseObject(message.getPayload(), DbSqlExecuteVO.class);
        DataDownload instance = factory.getInstance(dbSqlExecuteVO.getDbType());
        instance.download(dbSqlExecuteVO,session);

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception
    {
        super.handleMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception
    {
        super.handleTransportError(session, exception);
    }

}