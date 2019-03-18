package com.chezhibao.bigdata.redis;

import com.sohu.tv.cachecloud.client.basic.util.GetRedisClusterAddrFromCloudProperties;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 为了适配原先的redis配置
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/11.
 */
public class CloudCacheConfigAdapter {

    private static GetRedisClusterAddrFromCloudProperties config;

    public static void  setConfig(GetRedisClusterAddrFromCloudProperties cloudProperties) {
        config = cloudProperties;
    }

    public static GetRedisClusterAddrFromCloudProperties getConfig(){
        return config;
    }
}
