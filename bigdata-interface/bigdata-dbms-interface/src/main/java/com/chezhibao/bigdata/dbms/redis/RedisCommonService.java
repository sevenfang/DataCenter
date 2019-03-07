package com.chezhibao.bigdata.dbms.redis;

import java.util.List;
import java.util.Set;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/18.
 */
public interface RedisCommonService {
    /**
     * 删除表达式key *:value:*
     * @param pattern
     * @return
     * @throws Exception
     */
    Long delPatternKeys(String pattern);

    /**
     * 单独删除指定key
     * @param key
     * @return
     * @throws Exception
     */
    Long delete (String key);
    /**
     * 批量删除指定key
     * @param keys
     * @return
     * @throws Exception
     */
    Long delete (Set<String> keys);
    /**
     * 设置过期时间
     *
     * @param key
     * @param expire
     * @return
     */
    Long expire(String key, Long expire);

    /**
     * 加载脚本
     * @param script
     * @param key
     * @return
     */
    String scriptLoad(String script,String key);

    /**
     * 执行lua脚本
     * @param script
     * @param keys
     * @param args
     * @return
     */
    Object evalsha(String script, List<String> keys,List<String> args);
    /**
     * 执行lua脚本
     * @param script
     * @param keys
     * @param args
     * @return
     */
    Object eval(String script, List<String> keys,List<String> args);
}
