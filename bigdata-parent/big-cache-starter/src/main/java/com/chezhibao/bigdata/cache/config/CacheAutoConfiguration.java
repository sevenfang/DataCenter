package com.chezhibao.bigdata.cache.config;

import com.chezhibao.bigdata.cache.bo.RedisApp;
import com.chezhibao.bigdata.cache.factory.RedisAppFactoryBean;
import com.chezhibao.bigdata.redis.CloudCacheConfigAdapter;
import com.sohu.tv.cachecloud.client.basic.util.GetRedisClusterAddrFromCloudProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/18.
 */
@Configuration
public class CacheAutoConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.cache")
    public GetRedisClusterAddrFromCloudProperties getRedisClusterAddrFromCloudProperties(){
        GetRedisClusterAddrFromCloudProperties cloudProperties = new GetRedisClusterAddrFromCloudProperties();
        return cloudProperties;
    }

    @Bean
    public RedisApp geRedisApp(GetRedisClusterAddrFromCloudProperties getRedisClusterAddrFromCloudProperties){
        CloudCacheConfigAdapter.setConfig(getRedisClusterAddrFromCloudProperties);
        RedisAppFactoryBean factoryBean = new RedisAppFactoryBean();
        return factoryBean.getRedisApp();
    }
}
