package com.chezhibao.bigdata.gateway.core.factory;

import com.chezhibao.bigdata.gateway.core.Api;
import com.chezhibao.bigdata.gateway.pojo.ApiInfo;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/22.
 */
public interface ApiFactory {
    Api getApi(ApiInfo apiInfo);
}
