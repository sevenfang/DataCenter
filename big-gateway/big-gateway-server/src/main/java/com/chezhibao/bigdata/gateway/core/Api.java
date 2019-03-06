package com.chezhibao.bigdata.gateway.core;


import com.chezhibao.bigdata.gateway.pojo.ApiInfo;

import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/20.
 */
public interface Api {
    ApiInfo getApiInfo();
    Object process(Map<String , Object> params);
}
