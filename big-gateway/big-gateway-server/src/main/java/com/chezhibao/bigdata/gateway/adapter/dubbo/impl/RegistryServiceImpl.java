package com.chezhibao.bigdata.gateway.adapter.dubbo.impl;

import com.alibaba.dubbo.config.RegistryConfig;
import com.chezhibao.bigdata.gateway.adapter.dubbo.RegistryServeice;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/20.
 */
@Service
public class RegistryServiceImpl implements RegistryServeice {


    @Override
    public RegistryConfig getRegistryConfig(Integer id) {
        //TODO 目前只有一个注册中心
        return REGISTRY_CONFIG_MAP.get(-1);
    }
}
