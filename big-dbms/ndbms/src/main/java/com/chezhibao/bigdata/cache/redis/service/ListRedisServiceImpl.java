package com.chezhibao.bigdata.cache.redis.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.cache.bo.RedisApp;
import com.chezhibao.bigdata.cache.redis.adapter.AbstractRedisListService;
import com.chezhibao.bigdata.dbms.redis.ListRedisService;
import org.springframework.stereotype.Component;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/15.
 */
@Service(interfaceClass = ListRedisService.class)
@Component
public class ListRedisServiceImpl extends AbstractRedisListService {

    private RedisApp redisApp;

    public ListRedisServiceImpl(RedisApp redisApp) {
        this.redisApp = redisApp;
    }

    @Override
    public Object getRedisApp() {
        return redisApp.getApp("default");
    }
}
