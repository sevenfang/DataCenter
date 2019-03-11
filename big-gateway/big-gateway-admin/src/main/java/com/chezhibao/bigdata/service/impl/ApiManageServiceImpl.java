package com.chezhibao.bigdata.service.impl;

import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.gateway.exception.BigException;
import com.chezhibao.bigdata.gateway.pojo.ApiInfo;
import com.chezhibao.bigdata.gateway.utils.ApiKeyUtils;
import com.chezhibao.bigdata.service.ApiManageMapper;
import com.chezhibao.bigdata.service.ApiManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/7.
 */
@Service
@Slf4j
public class ApiManageServiceImpl implements ApiManageService {

    @Autowired
    private RedisTemplate<String , String > redisTemplate;

    @Autowired
    private ApiManageMapper apiManageMapper;

    @Override
    public Boolean saveApi(ApiInfo apiInfo) {
        ApiInfo apiInfo_tmp = apiManageMapper.getApiInfoByUriAndMethodAndVersion(apiInfo);
        if(!ObjectUtils.isEmpty(apiInfo_tmp)){
            log.error("【网关ADMIN】API已经存在！！");
            throw new BigException(500,"API已经存在！！");
        }
        Date date = new Date();
        apiInfo.setCreatedTime(date);
        apiInfo.setUpdatedTime(date);
        try {
            apiManageMapper.saveApiInfo(apiInfo);
        }catch (Exception e){
            log.error("【网关ADMIN】入库错误！！");
            throw new BigException(500,e.getMessage());
        }
        syncApi(apiInfo);
        return true;
    }
    @Override
    public Boolean syncApi(ApiInfo apiInfo) {
        ApiInfo info = apiManageMapper.getApiInfoByUriAndMethodAndVersion(apiInfo);
        if(ObjectUtils.isEmpty(info)){
            log.error("【网关ADMIN】API不存在！！");
            throw new BigException(500,"API不存在！！");
        }
        String key = ApiKeyUtils.getKey(apiInfo);
        String s = JSON.toJSONString(apiInfo);
        redisTemplate.opsForValue().set(key,s);
        return true;
    }

    @Override
    public Boolean updateApi(ApiInfo apiInfo) {
        ApiInfo apiById = apiManageMapper.getApiById(apiInfo.getId());
        if(ObjectUtils.isEmpty(apiById)){
            log.error("【网关ADMIN】API不存在！！");
            throw new BigException(500,"API不存在！！");
        }
        String old_key = ApiKeyUtils.getKey(apiById);
        try{
            apiManageMapper.updateApiInfo(apiInfo);
        }catch (Exception e){
            log.error("【网关ADMIN】更新错误！！");
            throw new BigException(500,e.getMessage());
        }
        String key = ApiKeyUtils.getKey(apiInfo);
        //判断key是否相同
        if(!old_key.equals(key)){
            redisTemplate.delete(old_key);
        }
        String s = JSON.toJSONString(apiInfo);
        redisTemplate.opsForValue().set(key,s);
        return true;
    }

    @Override
    public Boolean delApi(ApiInfo apiInfo) {
        Integer id = apiInfo.getId();
        ApiInfo apiById = apiManageMapper.getApiById(id);
        if(ObjectUtils.isEmpty(apiById)){
            log.error("【网关ADMIN】API不存在！！");
            throw new BigException(500,"API不存在！！");
        }
        try{
            apiManageMapper.delApiById(id);
        }catch (Exception e){
            log.error("【网关ADMIN】删除错误！！");
            throw new BigException(500,e.getMessage());
        }

        String key = ApiKeyUtils.getKey(apiInfo);
        redisTemplate.delete(key);
        return true;
    }

    @Override
    public List<ApiInfo> getAllApi() {
        return apiManageMapper.getAllApi();
    }

    @Override
    public ApiInfo getApiById(Integer id) {
        return apiManageMapper.getApiById(id);
    }
}
