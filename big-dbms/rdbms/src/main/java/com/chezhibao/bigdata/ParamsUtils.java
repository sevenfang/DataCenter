package com.chezhibao.bigdata;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/17.
 */
public class ParamsUtils {
    private static final String DATE_PATTERN="yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
    /**
     * 处理参数中的时间
     * @param params
     * @return
     */
    public static Map<String ,Object> handlerDate(Map<String ,Object> params){
        for(String key:params.keySet()){
            Object value = params.get(key);
            if(value instanceof Date){
                params.put(key,sdf.format((Date)value));
            }
        }
        return params;
    }
}
