package com.chezhibao.bigdata.cache.redis.aspect;

import com.chezhibao.bigdata.cache.redis.annotation.RedisCache;
import com.chezhibao.bigdata.dbms.common.LoggerUtil;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author WangCongJun
 * @date 2018/5/17
 * Created by WangCongJun on 2018/5/17.
 */
@Aspect
@Component
public class RedisAspect {
    private static Logger log = LoggerUtil.REDIS_LOG;
    @Before("@annotation(redisCache)")
    public void beforeSwitchDS(RedisCache redisCache){

    }


    @After("@annotation(redisCache)")
    public void afterSwitchDS(RedisCache redisCache){

    }
}
