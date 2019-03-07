package com.chezhibao.bigdata.cache.heap;

/**
 * @author WangCongJun
 * @date 2018/5/16
 * Created by WangCongJun on 2018/5/16.
 */
public interface IHeapCache {

    void put(String key, String value);
    String get(String key);
}
