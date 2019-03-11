package com.chezhibao.bigdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/11.
 */
@Configuration
public class GatewayAdminConfig {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
