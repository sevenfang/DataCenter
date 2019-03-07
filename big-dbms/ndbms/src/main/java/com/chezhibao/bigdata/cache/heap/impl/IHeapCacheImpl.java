package com.chezhibao.bigdata.cache.heap.impl;

import com.chezhibao.bigdata.cache.heap.IHeapCache;
import static com.chezhibao.bigdata.dbms.common.LoggerUtil.*;
import com.google.common.cache.Cache;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author WangCongJun
 * @date 2018/5/16
 * Created by WangCongJun on 2018/5/16.
 */

@Repository
public class IHeapCacheImpl implements IHeapCache {
    @Autowired
    private Cache<String,String> stringCache;

    @Override
    public void put(String key,String value) {
        REDIS_LOG.info("【缓存系统】heap本地缓存key:{},value:{}",key,value);
        stringCache.put(key,value);
    }

    @Override
    public String get(String key) {
        return stringCache.getIfPresent(key);
    }
}
