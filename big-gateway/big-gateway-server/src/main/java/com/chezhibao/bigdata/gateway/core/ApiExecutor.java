package com.chezhibao.bigdata.gateway.core;

import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.gateway.core.factory.ApiFactory;
import com.chezhibao.bigdata.gateway.pojo.ApiInfo;
import com.chezhibao.bigdata.gateway.core.pojo.ApiInfoBO;
import com.chezhibao.bigdata.gateway.core.service.ApiService;
import com.chezhibao.bigdata.gateway.exception.ApiException;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/20.
 */
@Component
@Slf4j
public class ApiExecutor {

    public static ConcurrentHashMap<String, Api> API_MAP = new ConcurrentHashMap<>();

    public static ApiKeyGenerator apiKeyGenerator = new ApiKeyGenerator() {
        @Override
        public String getKey(ApiInfoBO apiInfo) {
            String uri = apiInfo.getUri();
            Assert.notNull(uri, "http访问路径不能为空！！！");
            StringBuilder builder = new StringBuilder(uri.split("\\?")[0]);

            String method = apiInfo.getMethod();
            if (method != null) {
                builder.append("#").append(method.toUpperCase());
            }
            String version = apiInfo.getVersion();
            if (version != null) {
                builder.append("#").append(version);
            }
            return builder.toString();
        }
    };

    @Autowired
    private ApiFactory apiFactory;

    @Autowired
    private ApiService apiService;

    public BigdataResult execute(FullHttpRequest request) {
        // TODO 目前只支持JSON
        BigdataResult result;
        ApiInfoBO apiInfoBO = getApiInfoBO(request);
        try {
            Map<String, Object> jsonParams = FullHttpRequestHelper.getJSONParams(request);
            Object execute = execute(apiInfoBO, jsonParams);
            result = BigdataResult.ok(execute);
        } catch (Exception e) {
            result = BigdataResult.build(5000, e.getMessage());
            log.error("【网关系统】执行出错了！！请求参数：{}", apiInfoBO, e);
        }
        return result;
    }

    public Object execute(ApiInfoBO apiInfoBO, Map<String, Object> params) throws Exception {
        Api api = getApi(apiInfoBO);
        if (api == null) {
            throw new ApiException("api not exist!!");
        }
        Object process = api.process(params);
        return process;
    }

    private Api getApi(ApiInfoBO apiInfoBO) {
        String key = apiKeyGenerator.getKey(apiInfoBO);
        Api api = API_MAP.get(key);
        if (api != null) {
            return api;
        }
        ApiInfo apiInfo = apiService.getApiInfo(apiInfoBO);
        api = apiFactory.getApi(apiInfo);
        if (api == null) {
            return null;
        }
        API_MAP.put(key, api);
        return api;
    }

    /**
     * 提取请求参数中的APi相关信息
     *
     * @param request
     * @return
     */
    private ApiInfoBO getApiInfoBO(FullHttpRequest request) {
        String uri = request.uri();
        String name = request.method().name();
        Map<String, Object> getParams = FullHttpRequestHelper.getGetParamsFromUrl(request);
        String version = getParams.getOrDefault("version", 0).toString();
        return new ApiInfoBO(uri, name, version);
    }

    public interface ApiKeyGenerator {
        String getKey(ApiInfoBO apiInfoBO);
    }
}
