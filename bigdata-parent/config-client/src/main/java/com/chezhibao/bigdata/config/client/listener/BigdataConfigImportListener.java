package com.chezhibao.bigdata.config.client.listener;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.client.config.NacosConfigService;
import com.chezhibao.bigdata.config.client.config.NacosClientConfig;
import com.chezhibao.bigdata.config.client.factory.BigdataConfigFactory;
import com.chezhibao.bigdata.config.client.pojo.SysConfigParamProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.boot.context.event.*;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.ObjectUtils;

import java.net.InetAddress;
import java.util.Properties;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/14.
 */
@Slf4j
public class BigdataConfigImportListener implements ApplicationListener<SpringApplicationEvent>, PriorityOrdered {

    private int order = Ordered.HIGHEST_PRECEDENCE + 10;
    private SysConfigParamProperties sysConfigParamProperties;

    public BigdataConfigImportListener(){
        this.sysConfigParamProperties = BigdataConfigFactory.getBigdataConfigService().getAllParams();
        BigdataConfigFactory.getBigdataConfigService().initLogback();
    }

    @Override
    public void onApplicationEvent(SpringApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            onApplicationEnvironmentPreparedEvent(
                    (ApplicationEnvironmentPreparedEvent) event);
        }
        if (event instanceof ApplicationPreparedEvent) {
            onApplicationPreparedEvent((ApplicationPreparedEvent)event);
        }
        if (event instanceof ApplicationStartedEvent){
            onApplicationStartedEvent((ApplicationStartedEvent)event);
        }
        if (event instanceof ApplicationStartingEvent){
            onApplicationStartingEvent((ApplicationStartingEvent)event);
        }
    }

    private void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event){
        try {
            MapPropertySource mapPropertySource = new MapPropertySource("initConfig",sysConfigParamProperties.getParams());
            event.getEnvironment().getPropertySources().addFirst(mapPropertySource);
        }catch (Exception e){
            log.error("【配置服务】添加配置出错了！！",e);
        }
    }

    private void  onApplicationStartedEvent(ApplicationStartedEvent event){
        //注册服务
        Properties allParam = sysConfigParamProperties.getAllParam();
        Object o = allParam.get("server.port");
        Integer port = 0;
        if(!ObjectUtils.isEmpty(o)){
            port=Integer.parseInt(""+o);
        }
        try {

            InetAddress address = InetAddress.getLocalHost();
            String ip = address.getHostAddress();//返回IP地址
            String group = NacosClientConfig.getGroup();
            NamingService namingService = NamingFactory.createNamingService(NacosClientConfig.getNacosProp());
            namingService.registerInstance(group,ip,port);
        }catch (Exception e){
            log.error("【配置服务】注册实例出错了！！" , e);
        }
    }

    private void onApplicationPreparedEvent(ApplicationPreparedEvent event){
    }

    private void onApplicationStartingEvent(ApplicationStartingEvent event){

    }

    @Override
    public int getOrder() {
        return this.order;
    }
}
