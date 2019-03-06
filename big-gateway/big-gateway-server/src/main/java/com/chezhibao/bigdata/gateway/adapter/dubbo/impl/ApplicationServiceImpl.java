package com.chezhibao.bigdata.gateway.adapter.dubbo.impl;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.chezhibao.bigdata.gateway.adapter.dubbo.ApplicationService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/20.
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Override
    public ApplicationConfig getRegistryConfig(Integer id) {
        //TODO 目前只有一个application
        return APPLICATION_CONFIG_MAP.get(-1);
    }
}
