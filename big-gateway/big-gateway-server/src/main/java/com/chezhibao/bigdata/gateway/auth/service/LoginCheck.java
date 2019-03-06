package com.chezhibao.bigdata.gateway.auth.service;

import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.gateway.adapter.dubbo.DubboProxyService;
import com.chezhibao.bigdata.gateway.core.Api;
import com.chezhibao.bigdata.gateway.core.ApiExecutor;
import com.chezhibao.bigdata.gateway.core.factory.ApiFactory;
import com.chezhibao.bigdata.gateway.pojo.ApiInfo;
import com.chezhibao.bigdata.gateway.core.pojo.ApiInfoBO;
import com.chezhibao.bigdata.gateway.pojo.DubboParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/5.
 */
@Service
public class LoginCheck {

    @Autowired
    private ApiFactory apiFactory;

    public Boolean isLogin(String userId){
        String key = ApiExecutor.apiKeyGenerator.getKey(LoginApiServer.apiInfoBO);
        Api api = ApiExecutor.API_MAP.get(key);
        if(ObjectUtils.isEmpty(api)){
            api = apiFactory.getApi(LoginApiServer.loginGetServer);
            ApiExecutor.API_MAP.put(key,api);
        }
        Map<String,Object> params = new HashMap<>();
        params.put("id",userId);
        Object process = api.process(params);
        return process != null;
    }

    static class LoginApiServer {
        static ApiInfo loginGetServer = new ApiInfo();
        static String  className = "com.chezhibao.bigdata.authorization.service.UserService";
        static String methodName = "getCacheUserById";
        static ApiInfoBO apiInfoBO = new ApiInfoBO(className,methodName,null);

        static {

            DubboProxyService.DubboRequest dubboRequest = new DubboProxyService.DubboRequest();
            dubboRequest.setInterfaceName(className);
            dubboRequest.setMethod(methodName);

            List<DubboParam> params = new ArrayList<>();
            DubboParam param = new DubboParam();
            param.setClassName("java.lang.Integer");
            param.setName("id");
            param.setRequired(true);
            params.add(param);
            dubboRequest.setDubboParams(params);

            loginGetServer.setType("dubbo");
            loginGetServer.setTimeout(1000);
            loginGetServer.setRetries(0);
            loginGetServer.setDetail(JSON.toJSONString(dubboRequest));
        }
    }
}
