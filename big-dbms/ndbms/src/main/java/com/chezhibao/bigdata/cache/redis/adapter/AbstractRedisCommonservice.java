package com.chezhibao.bigdata.cache.redis.adapter;

import com.chezhibao.bigdata.dbms.common.CollectionUtils;
import com.chezhibao.bigdata.dbms.redis.RedisCommonService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.util.Pool;

import java.util.List;
import java.util.Set;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/18.
 */
public abstract class AbstractRedisCommonservice implements RedisCommonService,RedisAppAdapter {
    @Override
    public Long delPatternKeys(String pattern) {
        return null;
    }

    @Override
    public Long delete(String key) {
        Object app = getRedisApp();
        if(app instanceof JedisCluster){
            return ((JedisCluster) app).del(key);
        }
        if(app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            Long del = jedis.del(key);
            jedis.close();
            return del;
        }
        return null;
    }

    @Override
    public Long delete(Set<String> keys) {
        Object app = getRedisApp();
        String[] strings = CollectionUtils.list2Array(keys);
        if(app instanceof JedisCluster){
            return ((JedisCluster) app).del(strings);
        }
        if(app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            Long del = jedis.del(strings);
            jedis.close();
            return del;
        }
        return null;
    }

    @Override
    public Long expire(String key, Long expire) {
        Object app = getRedisApp();
        if(app instanceof JedisCluster){
            return ((JedisCluster) app).pexpire(key,expire);
        }
        if(app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            Long del = jedis.pexpire(key,expire);
            jedis.close();
            return del;
        }
        return null;
    }

    /**
     * 加载脚本
     * @param script
     * @param key
     * @return
     */
    @Override
    public String scriptLoad(String script,String key){
        Object app = getRedisApp();
        if(app instanceof JedisCluster){
            return ((JedisCluster) app).scriptLoad(script,key);
        }
        if(app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            String ret = jedis.scriptLoad(script);
            jedis.close();
            return ret;
        }
        return null;
    }

    @Override
    public Object evalsha(String script, List<String> keys, List<String> args) {
        Object app = getRedisApp();
        if(app instanceof JedisCluster){
            return ((JedisCluster) app).evalsha(script,keys,args);
        }
        if(app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            Object ret = jedis.evalsha(script,keys,args);
            jedis.close();
            return ret;
        }
        return null;
    }
    @Override
    public Object eval(String script, List<String> keys, List<String> args) {
        Object app = getRedisApp();
        if(app instanceof JedisCluster){
            return ((JedisCluster) app).eval(script,keys,args);
        }
        if(app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            Object ret = jedis.eval(script,keys,args);
            jedis.close();
            return ret;
        }
        return null;
    }
}
