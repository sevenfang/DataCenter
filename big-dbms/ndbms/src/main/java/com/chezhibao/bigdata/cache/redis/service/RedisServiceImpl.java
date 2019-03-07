package com.chezhibao.bigdata.cache.redis.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.cache.bo.RedisApp;
import com.chezhibao.bigdata.cache.redis.adapter.AbstractRedisService;
import com.chezhibao.bigdata.dbms.redis.IRedisService;
import org.springframework.stereotype.Component;

/**
 * @author WangCongJun
 * @date 2018/5/16
 * Created by WangCongJun on 2018/5/16.
 */
@Service(interfaceClass = IRedisService.class)
@Component
public class RedisServiceImpl extends AbstractRedisService {

    private RedisApp redisApp;

    public RedisServiceImpl(RedisApp redisApp) {
        this.redisApp = redisApp;
    }

    @Override
    public Object getRedisApp() {
        return redisApp.getApp("default");
    }
}
