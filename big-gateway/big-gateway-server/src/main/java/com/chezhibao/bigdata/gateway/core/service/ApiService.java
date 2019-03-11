package com.chezhibao.bigdata.gateway.core.service;

import com.chezhibao.bigdata.gateway.pojo.ApiInfo;
import com.chezhibao.bigdata.gateway.bo.ApiInfoBO;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/20.
 */
public interface ApiService {
    ApiInfo getApiInfo(ApiInfoBO apiInfoBO);
}
