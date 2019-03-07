package com.chezhibao.bigdata.template;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/5/31.
 */
public class ParamsBean {
    private Map<String,Object> params;

    private ParamsBean(){
        params = new HashMap<>();
    }

    public static ParamsBean newInstance(){
        return new ParamsBean();
    }

    public ParamsBean put(String key,Object value){
        params.put(key,value);
        return this;
    }

    public Map<String,Object> build(){
        return params;
    }
}
