package com.chezhibao.bigdata.cache.factory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chezhibao.bigdata.cache.bo.RedisApp;
import com.sohu.tv.builder.ClientBuilder;
import com.sohu.tv.cachecloud.client.basic.util.ConstUtils;
import com.sohu.tv.cachecloud.client.basic.util.GetRedisClusterAddrFromCloudProperties;
import com.sohu.tv.cachecloud.client.basic.util.HttpUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/18.
 */
public class RedisAppFactoryBean {

    private static Logger log = LoggerFactory.getLogger(RedisAppFactoryBean.class);

    private RedisApp redisApp;

    public RedisAppFactoryBean() {
        if (redisApp == null) {
            synchronized (RedisAppFactoryBean.class) {
                if (redisApp == null) {
                    init();
                }
            }
        }
    }

    public RedisApp getRedisApp() {
        return this.redisApp;
    }

    private void init() {
        this.redisApp = new RedisApp();
        try {
            String url = ConstUtils.APP_LIST_URL;
            String response = HttpUtils.doGet(url);
            JSONObject respObject = JSON.parseObject(response);
            Object status = respObject.get("status");
            if (status.equals(200)) {
                JSONObject data = respObject.getJSONObject("data");
                Map<String, Object> appMap = new HashMap<>(data.size());
                for (String name : data.keySet()) {
                    JSONObject appInfo = data.getJSONObject(name);
                    Long appId = appInfo.getLong("appId");
                    Integer type = appInfo.getInteger("type");
                    String appKey = appInfo.getString("appKey");
                    GenericObjectPoolConfig poolConfig =getConfig(name);
                    if(poolConfig == null){
                        poolConfig = new GenericObjectPoolConfig();
                    }
                    switch (type) {
                        case 2:
                            //cluster
                            JedisCluster jedisCluster = ClientBuilder.redisCluster(appId).setJedisPoolConfig(poolConfig)
                                    .setConnectionTimeout(1000)
                                    .setSoTimeout(500)
                                    .build();
                            appMap.put(name, jedisCluster);
                            break;
                        case 5:
                            //sentinel
                            JedisSentinelPool jedisSentinelPool = ClientBuilder.redisSentinel(appId).setPoolConfig(poolConfig)
                                    .setConnectionTimeout(1000)
                                    .setSoTimeout(500)
                                    .build();
                            appMap.put(name, jedisSentinelPool);
                            break;
                        case 6:
                            //standalone
                            JedisPool jedisPool = ClientBuilder.redisStandalone(appId).setPoolConfig(poolConfig)
                                    .setTimeout(500)
                                    .build();
                            appMap.put(name, jedisPool);
                            break;
                        default:
                    }
                }
                this.redisApp.setApps(appMap);
            }

        } catch (Exception e) {
            log.error("【数据服务】初始化redis连接失败！！", e);
        }
    }

    /**
     * 读取jedis连接配置，没有就使用默认配置
     * @param namespace
     * @return
     */
    private GenericObjectPoolConfig getConfig(String namespace) {
        GetRedisClusterAddrFromCloudProperties properties = ConstUtils.cloudProperties();
        Map<String, Properties> configs = properties.getConfigs();
        if(configs==null || configs.size()==0){
            return getConfigFromProperties(new Properties());
        }
        Properties prop = configs.get(namespace);
        if (prop == null || prop.size() == 0) {
            prop = new Properties();
            Integer maxIdle = properties.getMaxIdle();
            Integer maxTotal = properties.getMaxTotal();
            Integer minIdle = properties.getMinIdle();
            if(maxIdle != null){
                prop.setProperty("maxIdle", maxIdle +"");
            }
            if(maxTotal != null){
                prop.setProperty("maxTotal", maxTotal +"");
            }
            if(minIdle != null){
                prop.setProperty("minIdle", minIdle +"");
            }
        }
        return getConfigFromProperties(prop);
    }

    private GenericObjectPoolConfig getConfigFromProperties(Properties properties) {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL*40);
        genericObjectPoolConfig.setMaxIdle(GenericObjectPoolConfig.DEFAULT_MAX_IDLE*3);
        genericObjectPoolConfig.setMinIdle(GenericObjectPoolConfig.DEFAULT_MIN_IDLE);
        if(properties!=null && properties.size()>0) {
            String maxTotal = properties.getProperty("maxTotal");
            String minIdle = properties.getProperty("minIdle");
            String maxIdle = properties.getProperty("maxIdle");
            if (maxTotal != null && !maxTotal.isEmpty()) {
                genericObjectPoolConfig.setMaxTotal(Integer.parseInt(maxTotal));
            }
            if (maxIdle != null && !maxIdle.isEmpty()) {
                genericObjectPoolConfig.setMaxIdle(Integer.parseInt(maxIdle));
            }
            if (minIdle != null && !minIdle.isEmpty()) {
                genericObjectPoolConfig.setMinIdle(Integer.parseInt(minIdle));
            }
        }
        return genericObjectPoolConfig;
    }
}
