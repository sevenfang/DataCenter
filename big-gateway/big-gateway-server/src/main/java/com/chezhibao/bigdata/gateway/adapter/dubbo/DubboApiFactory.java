package com.chezhibao.bigdata.gateway.adapter.dubbo;

import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.gateway.core.Api;
import com.chezhibao.bigdata.gateway.core.factory.ApiFactory;
import com.chezhibao.bigdata.gateway.pojo.ApiInfo;
import com.chezhibao.bigdata.gateway.util.SpringApplicationContextUtils;

import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/22.
 */
public class DubboApiFactory implements ApiFactory {

    @Override
    public Api getApi(ApiInfo apiInfo) {
        DubboApi api = new DubboApi();
        api.setApiInfo(apiInfo);
        return api;
    }
    public class DubboApi implements Api {

        private ApiInfo apiInfo;

        @Override
        public ApiInfo getApiInfo() {
            return apiInfo;
        }

        public void setApiInfo(ApiInfo apiInfo) {
            this.apiInfo = apiInfo;
        }

        public DubboApi() {}

        @Override
        public Object process(Map<String , Object> params) {
            ApiInfo apiInfo = getApiInfo();
            DubboProxyService.DubboRequest dubboRequest = JSON.parseObject(apiInfo.getDetail(), DubboProxyService.DubboRequest.class);
            dubboRequest.setTimeout(apiInfo.getTimeout());
            dubboRequest.setRetries(apiInfo.getRetries());
            dubboRequest.setLoadbalance(apiInfo.getLoadbalance());
            dubboRequest.setVersion(apiInfo.getVersion());
            dubboRequest.setParamValues(params);
            DubboProxyService dubboProxyService = SpringApplicationContextUtils.CONTEXT.getBean(DubboProxyService.class);
            return dubboProxyService.genericInvoker(dubboRequest);
        }
    }
}
