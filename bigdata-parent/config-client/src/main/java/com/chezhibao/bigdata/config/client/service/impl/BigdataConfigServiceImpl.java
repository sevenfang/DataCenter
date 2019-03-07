package com.chezhibao.bigdata.config.client.service.impl;

import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.client.config.NacosConfigService;
import com.chezhibao.bigdata.config.client.config.NacosClientConfig;
import com.chezhibao.bigdata.config.client.exception.UnsuportedFileException;
import com.chezhibao.bigdata.config.client.pojo.NacosFileConfig;
import com.chezhibao.bigdata.config.client.pojo.SysConfigParamProperties;
import com.chezhibao.bigdata.config.client.service.BigdataConfigService;
import com.chezhibao.bigdata.config.client.service.BigdataNacosConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.jar.Manifest;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/14.
 */
@Slf4j
public class BigdataConfigServiceImpl implements BigdataConfigService {

    private BigdataNacosConfigService bigdataNacosConfigService;

    private SysConfigParamProperties sysConfigParamProperties;

    public BigdataConfigServiceImpl(BigdataNacosConfigService bigdataNacosConfigService) {
        this.bigdataNacosConfigService = bigdataNacosConfigService;
        try {
            this.sysConfigParamProperties=initConfig();
        }catch (Exception e){
            log.error("【配置服务】：初始化出错了！！",e);
        }
    }

    @Override
    public void initLogback() {
        String logFile = "logback.xml";
        String path = BigdataConfigServiceImpl.class.getResource("/").getPath();
        System.out.println(path);
        NacosConfigService nacosConfigService = bigdataNacosConfigService.getNacosConfigService();
        try {
            String group = NacosClientConfig.getGroup();
            String logback = nacosConfigService.getConfig(logFile,group,5000);
            log.debug("【配置服务】读取logback的配置：{}",logback);
            FileUtils.write(new File(path+ logFile), logback, "UTF-8", false);
            nacosConfigService.addListener(logFile, group, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    try {
                        FileUtils.write(new File(path+ logFile), configInfo, "UTF-8", false);
                    }catch (Exception e){
                        log.error("【配置服务】：logback动态更新出错了！！",e);
                    }

                }
            });
        }catch (Exception e){
            log.error("【配置服务】：logback初始化出错了！！",e);
        }
    }

    @Override
    public SysConfigParamProperties getAllParams() {
        return sysConfigParamProperties;
    }

    /**
     * 初始化配置
     * @return
     */
    public SysConfigParamProperties initConfig() throws Exception {
        Properties normal = new Properties();
        Properties dynamic = new Properties();
        Properties publicConfig = bigdataNacosConfigService.getPublicConfig();
        normal.putAll(NacosClientConfig.getSpeciaProp());
        normal.putAll(publicConfig);
        normal.putAll(NacosClientConfig.getNacosProp());
        List<NacosFileConfig> nacosFileConfigs = NacosClientConfig.getNacosFileConfigs();
        for (NacosFileConfig nf : nacosFileConfigs) {
            String dataId = nf.getDataId();
            Boolean autoRefresh = nf.getAutoRefresh();
            try {
                Properties propConfig = bigdataNacosConfigService.getPropConfig(dataId,null, 5000);
                if(ObjectUtils.isEmpty(propConfig)){
                    continue;
                }
                if (autoRefresh) {
                    dynamic.putAll(propConfig);
                    bigdataNacosConfigService.addListener(dataId, new Listener() {
                        @Override
                        public Executor getExecutor() {
                            return null;
                        }

                        @Override
                        public void receiveConfigInfo(String configInfo) {
                            try {
                                dynamic.putAll(bigdataNacosConfigService.getPropConfig(dataId, configInfo, 1000));
                            }catch (Exception e){
                                log.error("【配置服务】动态改变参数{}出错了！！",dataId,e);
                            }
                        }
                    });
                } else {
                    normal.putAll(propConfig);
                }
            } catch (UnsuportedFileException e) {
                String value = bigdataNacosConfigService.getConfig(dataId, 5000);
                if(StringUtils.isEmpty(value)){
                    continue;
                }
                if (autoRefresh) {
                    dynamic.put(dataId, value);
                    bigdataNacosConfigService.addListener(dataId, new Listener() {
                        @Override
                        public Executor getExecutor() {
                            return null;
                        }

                        @Override
                        public void receiveConfigInfo(String configInfo) {
                            try {
                                dynamic.put(dataId, configInfo);
                            }catch (Exception e){
                                log.error("【配置服务】动态改变参数{}出错了！！",dataId,e);
                            }
                        }
                    });
                } else {
                    normal.put(dataId, value);
                }
            }
        }
        SysConfigParamProperties sysConfigParamProperties = new SysConfigParamProperties();
        sysConfigParamProperties.setNormalParam(normal);
        sysConfigParamProperties.setDynamicParam(dynamic);
        initSentinelConfig(sysConfigParamProperties);
        return sysConfigParamProperties;
    }


    private void initSentinelConfig(SysConfigParamProperties sysConfigParamProperties){
        Properties allParam = sysConfigParamProperties.getAllParam();
        for(Object k : allParam.keySet()){
            if(k.toString().startsWith("csp.sentinel")){
                System.setProperty(k+"",allParam.get(k)+"");
            }
        }
        String property = System.getProperty("project.name");
        Assert.notNull(property,"project.name must be define!!");

    }
}
