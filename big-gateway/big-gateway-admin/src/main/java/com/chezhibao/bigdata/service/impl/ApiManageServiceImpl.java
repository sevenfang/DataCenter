package com.chezhibao.bigdata.service.impl;

import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.gateway.pojo.ApiInfo;
import com.chezhibao.bigdata.gateway.pojo.utils.ApiKeyUtils;
import com.chezhibao.bigdata.service.ApiManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/7.
 */
@Service
public class ApiManageServiceImpl implements ApiManageService {

    @Autowired
    private RedisTemplate<String , String > redisTemplate;

    @Override
    public void saveApi(ApiInfo apiInfo) {
        String key = ApiKeyUtils.getKey(apiInfo);
        System.out.println(key);
        String s = JSON.toJSONString(apiInfo);
        redisTemplate.opsForValue().set(key,s);
    }
}
