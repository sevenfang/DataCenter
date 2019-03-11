package com.chezhibao.bigdata.gateway.core.service;

import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.gateway.bo.ApiInfoBO;
import com.chezhibao.bigdata.gateway.exception.ApiException;
import com.chezhibao.bigdata.gateway.pojo.ApiInfo;
import com.chezhibao.bigdata.gateway.utils.ApiKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/7.
 */
@Service
@Slf4j
public class RedisApiServiceDelegate {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public ApiInfo doGetApiInfo(ApiInfoBO apiInfoBO){
        if(log.isDebugEnabled()){
            log.debug("【网关服务|获取API】查询参数：{}",apiInfoBO);
        }
        ApiInfo apiInfo = new ApiInfo();
        BeanUtils.copyProperties(apiInfoBO,apiInfo);
        String key = ApiKeyUtils.getKey(apiInfo);
        String s = redisTemplate.opsForValue().get(key);
        if(StringUtils.isEmpty(s)){
            throw new ApiException(String.format("key:%s此API不存在",key));
        }
        ApiInfo result = JSON.parseObject(s, ApiInfo.class);
        if(log.isDebugEnabled()){
            log.debug("【网关服务|获取API】获取api：{}",result);
        }
        return result;
    }
}
