package com.chezhibao.bigdata.schedule;

import com.chezhibao.bigdata.msg.service.CrawlMessageService;
import com.chezhibao.bigdata.websocket.CrawlMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/11.
 */
//@Component
@Slf4j
public class Jobs {
    private final static long FIVE_SECOND = 5 * 1000;

    @Autowired
    private CrawlMessageService crawlMessageService;

    @Scheduled(fixedDelay = FIVE_SECOND)
    public void sendCrawlData() {
        //任务执行结束后等待 fixedDelay 时间再执行

    }

    @Scheduled(fixedRate = FIVE_SECOND)
    public void fixedRateJob() {
        //每隔 fixedRate 执行一次
    }

    @Scheduled(cron = "0 15 3 * * ?")
    public void cronJob() {
        //cron表达式
    }
}
