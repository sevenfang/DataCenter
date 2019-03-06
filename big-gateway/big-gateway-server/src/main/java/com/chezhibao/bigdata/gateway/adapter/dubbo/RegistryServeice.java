package com.chezhibao.bigdata.gateway.adapter.dubbo;

import com.alibaba.dubbo.config.RegistryConfig;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/20.
 */
public interface RegistryServeice {
     Map<Integer, RegistryConfig> REGISTRY_CONFIG_MAP = Maps.newConcurrentMap();
    /**
     *
     * @param id
     * @return
     */
    RegistryConfig getRegistryConfig(Integer id);
}
