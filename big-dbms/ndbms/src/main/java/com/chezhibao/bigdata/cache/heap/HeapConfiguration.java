package com.chezhibao.bigdata.cache.heap;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author WangCongJun
 * @date 2018/5/16
 * Created by WangCongJun on 2018/5/16.
 */
@Configuration
public class HeapConfiguration {

    @Bean
    public Cache<String,String> stringStringCache(){
        return CacheBuilder.newBuilder().initialCapacity(10)
                .maximumSize(60)
                .expireAfterWrite(10,TimeUnit.SECONDS)
                .build();
    }

}
