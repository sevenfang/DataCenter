package com.chezhibao.bigdata.config.client.pojo;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/23.
 */
public class SysConfigParamProperties {
    private Properties dynamicParam;
    private Properties normalParam;


    public Properties getNormalParam() {
        return normalParam;
    }

    public void setNormalParam(Properties normalParam) {
        this.normalParam = normalParam;
    }

    public Properties getAllParam() {
        Properties allParam = new Properties();
        allParam.putAll(this.normalParam);
        allParam.putAll(this.dynamicParam);
        return allParam;
    }
    public Map<String, Object> getParams() {
        Map<String, Object> result = new HashMap<>();
        for(Object key:this.normalParam.keySet()){
            result.put(key+"",this.normalParam.get(key));
        }
        for(Object key:this.dynamicParam.keySet()){
            result.put(key+"",this.dynamicParam.get(key));
        }
        return result;
    }


    public Properties getDynamicParam() {
        return dynamicParam;
    }

    public void setDynamicParam(Properties dynamicParam) {
        this.dynamicParam = dynamicParam;
    }

    /**
     * 获取动态参数
     * @param paramName
     * @return
     */
    public String getDynamicValue(String paramName){
        Object o = dynamicParam.get(paramName);
        if(o==null){
            return "";
        }
        return o+"";
    }

    /**
     * 获取动态list参数
     * @param paramName
     * @return
     */
    public <T> List<T> getDynamicValueList(String paramName,Class<T> clazz) {
        Object o = dynamicParam.get(paramName);
        if(o==null){
            return new ArrayList<>();
        }
        return JSON.parseArray(o+"",clazz);
    }
}
