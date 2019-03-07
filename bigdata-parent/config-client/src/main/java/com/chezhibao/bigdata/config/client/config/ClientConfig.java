package com.chezhibao.bigdata.config.client.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/26.
 */
@Configuration
public class ClientConfig {
    @Bean
    public SentinelResourceAspect getSentinelResourceAspect(){
        return new SentinelResourceAspect();
    }

}
