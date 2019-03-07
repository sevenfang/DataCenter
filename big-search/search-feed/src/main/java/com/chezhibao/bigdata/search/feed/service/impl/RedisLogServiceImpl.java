package com.chezhibao.bigdata.search.feed.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.dbms.redis.ListRedisService;
import com.chezhibao.bigdata.search.feed.service.LogService;
import org.springframework.stereotype.Component;

/**
 * 这里统一将数据从左边插入，在从右边消费
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/19.
 */
@Component
public class RedisLogServiceImpl implements LogService {

    /**
     * 用户点击列表日志消息队列
     */
    private static final String USER_CLICK_LOG_QUEUE="user:click:log:queue";

    @Reference(check = false)
    private ListRedisService recommendListRedisService;

    @Override
    public Long sendLog(String msg) {
        return recommendListRedisService.lpush(USER_CLICK_LOG_QUEUE,msg);
    }

    @Override
    public String getLog() {
        return recommendListRedisService.rpop(USER_CLICK_LOG_QUEUE);
    }
}
