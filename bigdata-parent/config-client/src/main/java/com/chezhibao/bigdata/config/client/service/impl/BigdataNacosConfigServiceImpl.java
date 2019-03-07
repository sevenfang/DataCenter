package com.chezhibao.bigdata.config.client.service.impl;

import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.config.NacosConfigService;
import com.chezhibao.bigdata.config.client.config.NacosClientConfig;
import com.chezhibao.bigdata.config.client.exception.UnsuportedFileException;
import com.chezhibao.bigdata.config.client.service.BigdataNacosConfigService;
import com.chezhibao.bigdata.config.client.util.PropConfigUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Properties;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/14.
 */
@Slf4j
public class BigdataNacosConfigServiceImpl implements BigdataNacosConfigService {
    private static String XML_SUFFIX = ".xml";
    private static String YML_SUFFIX = ".yml";
    private static String PROP_SUFFIX = ".properties";
    private static String PUBLIC_CONFIG = "public.yml";

    private NacosConfigService nacosConfigService;
    /**
     * nacos分组group
     * bigdata:+项目民称
     */
    private String group;
    private String envGroup;

    public BigdataNacosConfigServiceImpl() throws Exception {
        Properties nacosProp = NacosClientConfig.getNacosProp();
        String env = NacosClientConfig.getEnv();
        envGroup = "bigdata:" + env;
        group = NacosClientConfig.getGroup();
        nacosConfigService = new NacosConfigService(nacosProp);
    }

    @Override
    public String getConfig(String dataId, long timeoutMs) throws NacosException {
        String config = nacosConfigService.getConfig(dataId, group, 5000);
        if (StringUtils.isEmpty(config)) {
            return "";
        }
        return config;
    }

    @Override
    public Properties getPublicConfig() throws Exception {
        String config = nacosConfigService.getConfig(PUBLIC_CONFIG, envGroup, 5000);
        return getPropConfig(PUBLIC_CONFIG,config,500);
    }

    @Override
    public NacosConfigService getNacosConfigService() {
        return nacosConfigService;
    }

    @Override
    public Properties getPropConfig(String dataId, String content, long timeoutMs) throws Exception {
        Properties result = new Properties();
        content = StringUtils.isEmpty(content)
                ? nacosConfigService.getConfig(dataId, group, 5000)
                : content;
        if (dataId.endsWith(PROP_SUFFIX)) {
            if (!StringUtils.isEmpty(content)) {
                result = PropConfigUtils.getPropFromPropString(content);
            }
        } else if (dataId.endsWith(XML_SUFFIX)) {
            //TODO xml配置文件
        } else if (dataId.endsWith(YML_SUFFIX)) {
            if (!StringUtils.isEmpty(content)) {
                System.out.println(content+"===============");
                result = PropConfigUtils.getPropFromYamlString(content);
            }
        } else {
            log.error("【配置服务】{}不支持此配置文件！！！", dataId);
            throw new UnsuportedFileException("【配置服务】不支持此配置文件！！！");
        }
        return result;
    }

    @Override
    public Resource getResource(String dataId, String content, long timeoutMs) throws Exception {
        return null;
    }

    @Override
    public void addListener(String dataId, Listener listener) throws NacosException {
        nacosConfigService.addListener(dataId, group, listener);
    }

    @Override
    public boolean publishConfig(String dataId, String content) throws NacosException {
        return nacosConfigService.publishConfig(dataId, group, content);
    }

    @Override
    public boolean removeConfig(String dataId) throws NacosException {
        return nacosConfigService.removeConfig(dataId, group);
    }

    @Override
    public void removeListener(String dataId, Listener listener) {
        nacosConfigService.removeListener(dataId, group, listener);
    }

    @Override
    public String getServerStatus() {
        return nacosConfigService.getServerStatus();
    }
}
