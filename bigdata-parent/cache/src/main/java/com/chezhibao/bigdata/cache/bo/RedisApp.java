package com.chezhibao.bigdata.cache.bo;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/18.
 */
public class RedisApp {
    private Map<String,Object> apps=new HashMap<>();

    public JedisCluster getJedisCluster(String namespace){
        Object o = apps.get(namespace);
        if(o instanceof JedisCluster){
            return (JedisCluster)o;
        }
        return null;
    }
    public JedisSentinelPool getJedisSentinelPool(String namespace){
        Object o = apps.get(namespace);
        if(o instanceof JedisSentinelPool){
            return (JedisSentinelPool)o;
        }
        return null;
    }
    public JedisPool getJedisPool(String namespace){
        Object o = apps.get(namespace);
        if(o instanceof JedisPool){
            return (JedisPool)o;
        }
        return null;
    }
    public Object getApp(String namespace){
        return apps.get(namespace);
    }

    public void setApps(Map<String, Object> apps) {
        this.apps = apps;
    }
}
