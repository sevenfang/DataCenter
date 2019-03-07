package com.chezhibao.bigdata.common.utils;

import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Map;
import java.util.Properties;

/**
 * 目前只支持读取classpath下的yml文件
 * @author WangCongJun
 * @date 2018/5/17
 * Created by WangCongJun on 2018/5/17.
 */
public class YamlUtils {

    /**
     * 读取yml为map
     * @param path
     * @return
     */
    public static Map<String,Object> getMapFromYmlFile(String path){
        ClassPathResource resource = new ClassPathResource(path);
        YamlMapFactoryBean yamlMapFactoryBean = new YamlMapFactoryBean();
        yamlMapFactoryBean.setResources(resource);
        return yamlMapFactoryBean.getObject();
    }

    /**
     * 读取yml为prop
     * @param path
     * @return
     */
    public static Properties getPropFromYmlFile(String path){
        ClassPathResource resource = new ClassPathResource(path);
        YamlPropertiesFactoryBean yamlMapFactoryBean = new YamlPropertiesFactoryBean();
        yamlMapFactoryBean.setResources(resource);
        return yamlMapFactoryBean.getObject();
    }

}
