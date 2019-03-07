package com.chezhibao.bigdata.dbms.redis;

import java.util.List;

/**
 * 默认使用 rpush rpushAll
 *
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/15.
 */
public interface ListRedisService extends RedisCommonService {
    /**
     * 获取list
     *
     * @param key
     * @return
     */
    List<String> get(String key);
    /**
     * 获取list最右端数据
     *
     * @param key
     * @return
     */
    String rpop(String key);
    /**
     * 获取list最左端数据
     *
     * @param key
     * @return
     */
    String lpop(String key);

    /**
     * 想list中批量添加值
     *
     * @param key
     * @param value
     * @return
     */
    Long addAll(String key, List<String> value);

    /**
     * 想list中添加单个值
     *
     * @param key
     * @param value
     * @return
     */
    Long add(String key, String value);

    /**
     * 想list中最左端添加单个值
     *
     * @param key
     * @param value
     * @return
     */
    Long lpush(String key, String value);

    /**
     * 想list中最右端添加单个值
     *
     * @param key
     * @param value
     * @return
     */
    Long rpush(String key, String value);

    /**
     * list中批量删除值
     *
     * @param key
     * @param value
     * @return
     */
    List<Object> removeAll(String key, List<String> value);

    /**
     * list删除单个值
     *
     * @param key
     * @param value
     * @return
     */
    Long remove(String key, Object value);

    /**
     * 获取list中的额一段值
     * [start,end] 前后都包含的
     * @param key
     * @param start
     * @param end
     * @return
     */
    List<String> get(String key, Integer start, Integer end);

//    /**
//     * 从指定位置开始，将当前及后面的数据都替换成新数据
//     * @param key
//     * @param startIndex
//     * @param values
//     * @return
//     */
//    List<Object> replaceValues(String key,Integer startIndex,List<?> values);
}
