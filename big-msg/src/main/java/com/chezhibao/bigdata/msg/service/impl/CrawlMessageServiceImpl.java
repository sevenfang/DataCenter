package com.chezhibao.bigdata.msg.service.impl;

import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.msg.service.CrawlMessageService;
import com.chezhibao.bigdata.websocket.CrawlMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/11.
 */
@Service
@Slf4j
public class CrawlMessageServiceImpl implements CrawlMessageService {

    private Vector<Object> msgs = new Vector<>();
    private final AtomicReference<List<Object>> currentMsgs = new AtomicReference<>();
    private Boolean isSend = false;

    @Override
    public List<Object> getAllMsgs() {
        //去除过期数据
        checkExpiredMsg(msgs);
        Vector<Object> temp = new Vector<>(msgs);
        msgs.removeAll(temp);
        return temp;
    }

    @Override
    public void checkExpiredMsg(List<Object> msgs) {

    }

    public Boolean isExpiredMsg(Object msgs) {
        return false;
    }

    @Override
    public void addNewMsg(Object msg) {
        //去除过期数据
        checkExpiredMsg(msgs);
        msgs.add(msg + "");
    }

    @Override
    public void stop() {
        if (isSend) {
            isSend = false;
            //停止后的操作
            stopHandler();
        }
    }

    @Override
    public void start() {
        if (!isSend) {
            isSend = true;
            sendMsg();
        }
    }

    private Boolean getSend() {
        return isSend;
    }

    @Override
    public void sendMsg() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                log.error("【消息系统】",e);
            }
            CopyOnWriteArraySet<WebSocketSession> webSocketSet = CrawlMessageHandler.getWebSocketSet();
            if(System.currentTimeMillis()%10000<1000){
                log.info("数据抓取服务：当前人数：{};ips:{}", webSocketSet.size(),getRemoteAddr(webSocketSet));
            }

            if (ObjectUtils.isEmpty(webSocketSet)) {
                stop();
                continue;
            }
            if (getSend()) {
                List<Object> allMsgs = getAllMsgs();
                //按照既定的规则分发消息
                sendRandomMsg(allMsgs, webSocketSet);
            } else {
                break;
            }
        }
    }

    private List<String> getRemoteAddr(CopyOnWriteArraySet<WebSocketSession> webSocketSet){
        List<String> strings = new ArrayList<>();

        for(WebSocketSession ws :webSocketSet){
            InetSocketAddress remoteAddress = ws.getRemoteAddress();
            if(!ObjectUtils.isEmpty(remoteAddress)){
                String hostString = remoteAddress.getHostString();
                strings.add(hostString);
            }
        }

        return strings;
    }

    private void stopHandler() {

    }

    //轮询分发
    private void sendBalanceMsg(List<Object> allMsgs, CopyOnWriteArraySet<WebSocketSession> webSocketSet) {
        Iterator<Object> iterator = allMsgs.iterator();
        while (iterator.hasNext()) {
            for (WebSocketSession session : webSocketSet) {
                if (session.isOpen()) {
                    if (iterator.hasNext()) {
                        Object next = iterator.next();
                        //校验消息是否过期
                        Boolean aBoolean = isExpiredMsg(next);
                        if(aBoolean){
                            continue;
                        }
                        try {
                            session.sendMessage(new TextMessage(JSON.toJSONString(BigdataResult.ok(next))));
                        } catch (Exception e) {
                            log.error("【数据抓取】分发数据出错了！msg：{}", next,e);
                        }

                    }
                }
            }
        }
    }

    //随机分发
    private void sendRandomMsg(List<Object> allMsgs, CopyOnWriteArraySet<WebSocketSession> webSocketSet) {
        Iterator<Object> iterator = allMsgs.iterator();
        Random random = new Random();
        while (iterator.hasNext()) {
            int size = webSocketSet.size();
            int i = random.nextInt(size);
            log.info("=======:{}",i);
            //获取session会话
            WebSocketSession session;
            try {
                session = (WebSocketSession) webSocketSet.toArray()[i];
            } catch (IndexOutOfBoundsException e) {
                if (webSocketSet.size() > 0) {
                    session = (WebSocketSession) webSocketSet.toArray()[0];
                } else {
                    //没有有效的session就一直等待
                    log.info("等待客户端接入......");
                    continue;
                }
            }
            Object next = iterator.next();
            //校验消息是否过期
            Boolean aBoolean = isExpiredMsg(next);
            if(aBoolean){
                continue;
            }
            try {
                session.sendMessage(new TextMessage(next + ""));
            } catch (Exception e) {
                log.error("【数据抓取】分发数据出错了！msg：{}", next,e);
            }
        }
    }
}
