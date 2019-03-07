package com.chezhibao.bigdata.dbms.redis;

import java.util.List;
import java.util.Set;

/**
 * @author WangCongJun
 * @date 2018/5/16
 * Created by WangCongJun on 2018/5/16.
 */
public interface IRedisService extends RedisCommonService{

    /**
     * 缓存数据到redis
     * @param key
     * @param value
     * @param expire 过期时间 单位：毫秒 若果为
     * @throws Exception
     */
    String set(String key, String value, Long expire);

    /**
     * 缓存数据到redis
     * @param key
     * @param value
     * @throws Exception
     */
    String set(String key, String value);

    /**
     * 缓存数据到redis 如果存在就不做任何操作
     * set if not exist 简称 nx
     * @param key
     * @param value
     * @throws Exception
     */
    Long setnx(String key, String value);

    /**
     * 缓存数据到redis 如果存在就不做任何操作
     * set if not exist 简称 nx
     * @param key
     * @param value
     * @throws Exception
     */
    String set(String key, String value, String nxxx, String expx, int time);

    /**
     * 根据key获取redis中对应的值
     * @param key
     * @throws Exception
     */
    String get(String key);

    /**
     * 批量查询mGet
     * @param keys
     * @return
     * @throws Exception
     */
    List<String> mGet(List<String> keys);
}
