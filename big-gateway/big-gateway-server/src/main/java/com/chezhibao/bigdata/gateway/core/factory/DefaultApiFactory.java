package com.chezhibao.bigdata.gateway.core.factory;

import com.chezhibao.bigdata.gateway.adapter.dubbo.DubboApiFactory;
import com.chezhibao.bigdata.gateway.core.Api;
import com.chezhibao.bigdata.gateway.pojo.ApiInfo;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/22.
 */
@Component
public class DefaultApiFactory implements ApiFactory {
    @Override
    public Api getApi(ApiInfo apiInfo) {
        Api api = null;
        switch (apiInfo.getType()) {
            case "dubbo":
                DubboApiFactory dubboApiFactory = new DubboApiFactory();
                api = dubboApiFactory.getApi(apiInfo);
                break;
            case "http":
                break;
            default:
                break;
        }
        return api;
    }


}
