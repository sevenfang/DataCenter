package com.chezhibao.bigdata.service;

import com.chezhibao.bigdata.gateway.pojo.ApiInfo;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/7.
 */
public interface ApiManageService {
    Boolean saveApi(ApiInfo apiInfo);
    Boolean syncApi(ApiInfo apiInfo);
    Boolean updateApi(ApiInfo apiInfo);
    Boolean delApi(ApiInfo apiInfo);
    List<ApiInfo> getAllApi();
    ApiInfo getApiById(Integer id);
}
