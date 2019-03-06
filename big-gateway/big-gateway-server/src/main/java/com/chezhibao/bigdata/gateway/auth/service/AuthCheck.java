package com.chezhibao.bigdata.gateway.auth.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.chezhibao.bigdata.gateway.auth.EnforcerFactory;
import lombok.extern.slf4j.Slf4j;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/5.
 */
@Service
@Slf4j
public class AuthCheck {
    /**
     *
     * @param userId 用户ID
     * @param uri 访问路径
     * @param method 请求方式
     * @return
     */
    public Boolean auth(String userId,String uri,String method){
        Enforcer enforcer = EnforcerFactory.getEnforcer();
        if (enforcer.enforce(userId, uri, method)) {
            return true;
        } else {
            log.info("【网关系统|权鉴服务】无权访问");
            return false;
        }
    }
}
