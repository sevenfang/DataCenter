package com.chezhibao.bigdata.dbms.common.config;

import com.alibaba.nacos.client.config.NacosConfigService;
import com.chezhibao.bigdata.config.client.config.NacosClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/14.
 */
@Component
@Configuration
public class NacosConfiguration {
    @Bean
    public NacosConfigService getNacosConfigService() throws Exception{
        return new NacosConfigService(NacosClientConfig.getNacosProp());
    }
}
