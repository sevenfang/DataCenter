package com.chezhibao.bigdata.cache.redis.service.recommend;

import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.cache.bo.RedisApp;
import com.chezhibao.bigdata.cache.redis.adapter.AbstractRedisListService;
import com.chezhibao.bigdata.dbms.redis.ListRedisService;
import com.chezhibao.bigdata.dbms.redis.recommend.RecommendListRedisService;
import org.springframework.stereotype.Component;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/15.
 */
@Service(interfaceClass = RecommendListRedisService.class)
@Component
public class RecommendListRedisServiceImpl extends AbstractRedisListService implements RecommendListRedisService {

    private RedisApp redisApp;

    public RecommendListRedisServiceImpl(RedisApp redisApp) {
        this.redisApp = redisApp;
    }

    @Override
    public Object getRedisApp() {
        return redisApp.getApp("recommend");
    }
}
