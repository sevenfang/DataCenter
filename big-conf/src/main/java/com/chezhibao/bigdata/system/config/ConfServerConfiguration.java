package com.chezhibao.bigdata.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/23.
 */
@Configuration
public class ConfServerConfiguration {
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
