package com.chezhibao.bigdata.config.client.config;

import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.config.client.pojo.NacosFileConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 读取项目的基础配置
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/14.
 */

@Slf4j
public class NacosClientConfig {
    public final static String SPRING_APPLICATION_NAME = "spring.application.name";
    //    private final static String SPRING_PROFILE_ACTIVE = "spring.profiles.active";
    public final static String SPRING_CONFIG_URL = "spring.config.url";
    public final static String SPRING_CLOUD_NACOS_CONFIG_SERVER_ADDR = "spring.cloud.nacos.config.serverAddr";
    public final static String SPRING_CLOUD_NACOS_DISCOVERY_SERVER_ADDR = "spring.cloud.nacos.discovery.serverAddr";
    public final static String NACOS_SERVER = "nacosServer";
    public final static String SENTINEL = "sentinel";
    //启动的环境
    public final static String ENV = "env";

    public final static String NACOS_CONFIG = "nacosConfig";
    /**
     * 读取系统的基本配置
     */
    private  static Properties basicProp;
    static {
        ClassPathResource classPathResource = new ClassPathResource("config//application.properties");
        if (!classPathResource.exists()) {
            classPathResource = new ClassPathResource("application.properties");
        }
        Assert.notNull(classPathResource,"请添加classpath://config/application.json配置文件");
        try {
            basicProp = new Properties();
            basicProp.load(classPathResource.getInputStream());
            //配置环境名称
            Object o = System.getProperty(ENV);
            if(ObjectUtils.isEmpty(o)){
                log.warn("【配置服务】运行时未指定ENV，可通过启动参数-Denv=环境名称指定！！");
            }
            basicProp.setProperty(ENV,o+"");
            //配置项目名称
            System.setProperty("project.name",NacosClientConfig.getGroup());
            basicProp.setProperty("project.name",NacosClientConfig.getGroup());
        }catch (Exception e){
            log.error("【配置服务】读取基础的配置出错!!",e);
        }
        log.info("【配置服务】读取基础的配置为：{}",basicProp);
        Assert.notEmpty(basicProp,"请添加classpath://config/application.json配置文件");
    }

    /**
     * 通过环境初始化的配置来指定特定的上下文配置
     * @return
     */
    public static Properties getSpeciaProp(){
        Properties speciaProp = new Properties();
        //配置nacos
        Object o = basicProp.get("serverAddr" + "." + getEnv());
        speciaProp.put(SPRING_CLOUD_NACOS_CONFIG_SERVER_ADDR,o);
        speciaProp.put(SPRING_CLOUD_NACOS_DISCOVERY_SERVER_ADDR,o);
        return speciaProp;
    }
    /**
     *  1、优先根据java -D参数获取对应的应用名称信息
     *  2、其次使用application.json配置文件中的appName
     * @return
     */
    public static String getAppName(){
        Object o = System.getProperty(SPRING_APPLICATION_NAME);
        if (ObjectUtils.isEmpty(o)){
            o = basicProp.get(SPRING_APPLICATION_NAME);
        }
        Assert.notNull(o,SPRING_APPLICATION_NAME+" 必须设定");
        return o +"";
    }

    /**
     *  1、优先根据java -D参数获取对应的环境信息
     *  2、其次使用application.json配置文件中的evn
     * @return
     */
    public static String getEnv(){
        Object o=basicProp.get(ENV);
        Assert.notNull(o,ENV+" 必须设定");
        return o +"";
    }

    public static Properties getNacosProp(){
        Properties properties = new Properties();
        Object o = basicProp.get("serverAddr" + "." + getEnv());
        properties.put("serverAddr",o);
        return properties;
    }
    public static Properties getSentinelProp(){
        Properties properties = new Properties();
        Object o = basicProp.get(SENTINEL);
        if(!ObjectUtils.isEmpty(o)){
            properties.putAll((Map<?, ?>) o);
        }
        return properties;
    }

    /**
     * 获取项目中配置的dataId
     * @return
     */
    public static List<NacosFileConfig> getNacosFileConfigs(){
        List<NacosFileConfig> result = new ArrayList<>();
        //默认配置
        NacosFileConfig applicationParams = new NacosFileConfig();
        applicationParams.setAutoRefresh(false);
        applicationParams.setDataId("application.yml");
        result.add(applicationParams);
        NacosFileConfig dynamicParams = new NacosFileConfig();
        dynamicParams.setAutoRefresh(true);
        dynamicParams.setDataId("dynamic.yml");
        result.add(dynamicParams);
        //读取自定义配置
        Object nacosConfig = basicProp.get(NACOS_CONFIG);
        if(!ObjectUtils.isEmpty(nacosConfig)){
            List<Map<String,Object>> o = (List<Map<String,Object>>) nacosConfig;
            List<NacosFileConfig> nacosFileConfigs = ObjectCommonUtils.map2Object(o, NacosFileConfig.class);
            result.addAll(nacosFileConfigs);
        }
        return  result;
    }

    /**
     * 获取对应的环境组信息
     * @return
     */
    public static String getGroup(){
        String env = getEnv();
        String appName = getAppName();
        String envGroup = "bigdata:" + env;
        return envGroup +":"+ appName;
    }
}
