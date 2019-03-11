package com.chezhibao.bigdata.gateway.core.service;

import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.gateway.adapter.dubbo.DubboProxyService;
import com.chezhibao.bigdata.gateway.bo.ApiInfoBO;
import com.chezhibao.bigdata.gateway.core.Api;
import com.chezhibao.bigdata.gateway.core.ApiExecutor;
import com.chezhibao.bigdata.gateway.core.factory.ApiFactory;
import com.chezhibao.bigdata.gateway.pojo.ApiInfo;
import com.chezhibao.bigdata.gateway.exception.ApiException;
import com.chezhibao.bigdata.gateway.pojo.DubboParam;
import com.chezhibao.bigdata.gateway.utils.ApiKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ApiServiceDelegate {

    private ApiFactory apiFactory;
    private Api apiServer;

    public ApiServiceDelegate(ApiFactory apiFactory) {
        this.apiFactory = apiFactory;
        //获取搜索api的服务API
        apiServer = getSearchApiServer();
    }

    public ApiInfo doGetApiInfo(ApiInfoBO apiInfoBO) {
        Map<String, Object> param = new HashMap<>();
        String key = ApiKeyUtils.getKey(apiInfoBO);
        param.put("key", key);
        Object process = apiServer.process(param);
        if (StringUtils.isEmpty(process)) {
            throw new ApiException("服务不存在！！");
        }
        try {
            return JSON.parseObject(process.toString(), ApiInfo.class);
        } catch (Exception e) {
            log.error("【网关系统】aqi存放格式有问题！必须是JSON格式！！",e);
            throw new ApiException("api 格式有问题！");
        }
    }

    /**
     * 获取搜索api的服务api
     *
     * @return
     */
    private Api getSearchApiServer() {
        String key = ApiKeyUtils.getKey(RedisApiServer.apiInfoBO);
        Api api = ApiExecutor.API_MAP.get(key);
        if(ObjectUtils.isEmpty(api)){
            api = apiFactory.getApi(RedisApiServer.redisGetServer);
            ApiExecutor.API_MAP.put(key,api);
        }
        return api;
    }

    static class RedisApiServer {
        static  ApiInfo redisGetServer = new ApiInfo();
        static String  className = "com.chezhibao.bigdata.dbms.redis.IRedisService";
        static String methodName = "get";
        static  ApiInfoBO apiInfoBO = new ApiInfoBO(className,methodName,null);

        static {

            DubboProxyService.DubboRequest dubboRequest = new DubboProxyService.DubboRequest();
            dubboRequest.setInterfaceName(className);
            dubboRequest.setMethod(methodName);

            List<DubboParam> params = new ArrayList<>();
            DubboParam param = new DubboParam();
            param.setClassName("java.lang.String");
            param.setName("key");
            param.setRequired(true);
            params.add(param);
            dubboRequest.setDubboParams(JSON.toJSONString(params));

            redisGetServer.setType("dubbo");
            redisGetServer.setTimeout(1000);
            redisGetServer.setRetries(0);
            redisGetServer.setDetail(JSON.toJSONString(dubboRequest));
        }
    }

}