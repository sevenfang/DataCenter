package com.chezhibao.bigdata.config.client;

import com.chezhibao.bigdata.config.client.factory.BigdataConfigFactory;
import com.chezhibao.bigdata.config.client.pojo.SysConfigParamProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/15.
 */
public class ParamsUtil  {

    private static SysConfigParamProperties staticSysConfigParamProperties;

    static {
        staticSysConfigParamProperties = BigdataConfigFactory.getBigdataConfigService().getAllParams();
    }

    /**
     * 获取动态参数
     * @param paramName
     * @return
     */
    public static String getDynamicValue(String paramName){
        return staticSysConfigParamProperties.getDynamicValue(paramName);
    }

    /**
     * 获取动态list参数
     * @param paramName
     * @return
     */
    public static <T> List<T>  getDynamicValueList(String paramName, Class<T> clazz){
        return  staticSysConfigParamProperties.getDynamicValueList(paramName,clazz);
    }
}
