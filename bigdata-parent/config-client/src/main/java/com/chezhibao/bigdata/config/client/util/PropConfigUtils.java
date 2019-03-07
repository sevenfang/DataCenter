package com.chezhibao.bigdata.config.client.util;

import com.chezhibao.bigdata.config.client.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.util.Properties;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/14.
 */
@Slf4j
public class PropConfigUtils {

    public static Properties getPropFromYamlString(String yamlString) {
        if (StringUtils.isEmpty(yamlString)){
            return new Properties();
        }
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        ByteArrayResource byteArrayResource = new ByteArrayResource(yamlString.getBytes());
        yaml.setResources(byteArrayResource);
        return yaml.getObject();
    }
    public static Properties getPropFromPropString(String propString) throws Exception{
        if (StringUtils.isEmpty(propString)){
            return new Properties();
        }
        PropertiesFactoryBean pro = new PropertiesFactoryBean();
        ByteArrayResource byteArrayResource = new ByteArrayResource(propString.getBytes());
        pro.setLocations(byteArrayResource);
        return pro.getObject();
    }


    public static Properties getPropertiesFromFile(String filePath) {
        if (filePath.endsWith(Constants.YML_SUFFIX)) {
            log.debug("【配置系统】读取的yml文件名路径：{}", filePath);
            return getPropertiesFromYamlFile(filePath);
        }
        if (filePath.endsWith(Constants.PROP_SUFFIX)) {
            log.debug("【配置系统】读取的propertie文件名路径：{}", filePath);
            return getPropertiesFromPropFile(filePath);
        }
        return null;
    }


    private static Properties getPropertiesFromYamlFile(String filePath) {
        ClassPathResource classPathResource = new ClassPathResource("config//" + filePath);
        if (!classPathResource.exists()) {
            classPathResource = new ClassPathResource(filePath);
        }
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(classPathResource);
        return yaml.getObject();
    }

    private static Properties getPropertiesFromPropFile(String filePath) {
        ClassPathResource classPathResource = new ClassPathResource("config//" + filePath);

        if (!classPathResource.exists()) {
            classPathResource = new ClassPathResource(filePath);
        }
        PropertiesFactoryBean pro = new PropertiesFactoryBean();
        pro.setLocations(classPathResource);
        try {
            return pro.getObject();
        } catch (Exception e) {
            log.error("获取系统配置出错了", e);
            return null;
        }
    }
}
