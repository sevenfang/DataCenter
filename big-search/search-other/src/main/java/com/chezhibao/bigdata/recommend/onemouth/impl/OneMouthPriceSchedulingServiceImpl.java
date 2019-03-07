package com.chezhibao.bigdata.recommend.onemouth.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.dbms.redis.recommend.RecommendRedisService;
import com.chezhibao.bigdata.recommend.onemouth.AbstractOlRecommendPushService;
import com.chezhibao.bigdata.recommend.onemouth.OneMouthPriceSchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/23.
 */
@Service
public class OneMouthPriceSchedulingServiceImpl implements OneMouthPriceSchedulingService {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    /**
     * expx EX|PX, expire time units: EX = seconds; PX = milliseconds
     */
    private static final String SET_WITH_EXPIRE_TIME = "EX";

    private static String LOCK_ONEMOUTH_REDIS_PREFIX = "onemouth:push:lock";

    @Reference
    private RecommendRedisService recommendRedisService;

    @Autowired
    private AbstractOlRecommendPushService olRecommendPushService;

    @Override
    @Scheduled(cron = "0 30 8-22/1 * * *")
    public void recommendCar() {
        //这里是抢到锁后占有锁的时间，一定要小于任务执行的间隔时间
        String set = recommendRedisService.set(LOCK_ONEMOUTH_REDIS_PREFIX, "1", SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 60 * 50);
        if(set==null || !set.equals(LOCK_SUCCESS)){
            return;
        }
        olRecommendPushService.pushMessage();
    }
}
