package com.chezhibao.bigdata.gateway.adapter.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/20.
 */
public interface ApplicationService {
    Map<Integer, ApplicationConfig> APPLICATION_CONFIG_MAP = Maps.newConcurrentMap();
    /**
     *
     * @param id
     * @return
     */
    ApplicationConfig getRegistryConfig(Integer id);
}
