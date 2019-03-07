package com.chezhibao.bigdata.cache.redis.adapter;

import com.chezhibao.bigdata.dbms.common.CollectionUtils;
import com.chezhibao.bigdata.dbms.redis.IRedisService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.util.Pool;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/18.
 */
public abstract class AbstractRedisService extends AbstractRedisCommonservice implements IRedisService ,RedisAppAdapter{

    @Override
    public String set(String key, String value, Long expire) {
        Object app = getRedisApp();
        if(app instanceof JedisCluster){
            return ((JedisCluster) app).psetex(key,expire,value);
        }
        if(app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            String ret = jedis.psetex(key,expire,value);
            jedis.close();
            return ret;
        }
        return null;
    }

    @Override
    public String set(String key, String value) {
        Object app = getRedisApp();
        if(app instanceof JedisCluster){
            return ((JedisCluster) app).set(key,value);
        }
        if(app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            String ret = jedis.set(key,value);
            jedis.close();
            return ret;
        }
        return null;
    }

    @Override
    public String get(String key) {
        Object app = getRedisApp();
        if(app instanceof JedisCluster){
            return ((JedisCluster) app).get(key);
        }
        if(app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            String ret = jedis.get(key);
            jedis.close();
            return ret;
        }
        return null;
    }

    @Override
    public List<String> mGet(List<String> keys) {
        String[] strings = CollectionUtils.list2Array(keys);
        Object app = getRedisApp();
        if(app instanceof JedisCluster){
            return ((JedisCluster) app).mget(strings);
        }
        if(app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            List<String> ret = jedis.mget(strings);
            jedis.close();
            return ret;
        }
        return null;
    }

    @Override
    public Long setnx(String key, String value) {
        Object app = getRedisApp();
        if(app instanceof JedisCluster){
            return ((JedisCluster) app).setnx(key,value);
        }
        if(app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            Long ret = jedis.setnx(key,value);
            jedis.close();
            return ret;
        }
        return null;
    }

    @Override
    public String set(String key, String value, String nxxx, String expx, int time) {
        Object app = getRedisApp();
        if(app instanceof JedisCluster){
            return ((JedisCluster) app).set(key,value,nxxx,expx,time);
        }
        if(app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            String ret = jedis.set(key,value,nxxx,expx,time);
            jedis.close();
            return ret;
        }
        return null;
    }
}
