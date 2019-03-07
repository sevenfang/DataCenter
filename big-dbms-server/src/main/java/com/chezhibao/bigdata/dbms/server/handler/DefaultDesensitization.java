package com.chezhibao.bigdata.dbms.server.handler;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/7.
 */
@Component
public class DefaultDesensitization implements Desensitization<LinkedHashMap<String,Object>> {
    @Override
    public  LinkedHashMap<String,Object> desensitization(LinkedHashMap<String,Object> t) {
        //TODO 脱敏处理
        return t;
    }
}
