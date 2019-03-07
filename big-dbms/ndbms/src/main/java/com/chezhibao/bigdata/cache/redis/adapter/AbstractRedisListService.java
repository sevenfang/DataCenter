package com.chezhibao.bigdata.cache.redis.adapter;

import com.chezhibao.bigdata.cache.redis.BatchRedisOption;
import com.chezhibao.bigdata.dbms.common.CollectionUtils;

import static com.chezhibao.bigdata.dbms.common.LoggerUtil.*;

import com.chezhibao.bigdata.dbms.redis.ListRedisService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.PipelineBase;
import redis.clients.util.Pool;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/18.
 */
public abstract class AbstractRedisListService extends AbstractRedisCommonservice implements ListRedisService, RedisAppAdapter {
    @Override
    public List<String> get(String key) {
        Object app = getRedisApp();
        try {
            if (app instanceof JedisCluster) {
                return ((JedisCluster) app).lrange(key, 0, -1);
            }
            if (app instanceof Pool) {
                Jedis jedis = ((Pool<Jedis>) app).getResource();
                List<String> ret = jedis.lrange(key, 0, -1);
                jedis.close();
                return ret;
            }
        } catch (Exception e) {
            REDIS_LOG.error("【dbms】获取redis中key：{}出错了！", key, e);
            throw e;
        }
        return null;
    }

    @Override
    public Long addAll(String key, List<String> value) {
        Object app = getRedisApp();
        String[] strings = CollectionUtils.list2Array(value);
        try {
            if (app instanceof JedisCluster) {
                return ((JedisCluster) app).rpush(key, strings);
            }
            if (app instanceof Pool) {
                Jedis jedis = ((Pool<Jedis>) app).getResource();
                Long ret = jedis.rpush(key, strings);
                jedis.close();
                return ret;
            }
        } catch (Exception e) {
            REDIS_LOG.error("【dbms】添加redis中key：{}出错了！", key, e);
            throw e;
        }
        return null;
    }

    @Override
    public Long add(String key, String value) {
        Object app = getRedisApp();
        if (app instanceof JedisCluster) {
            return ((JedisCluster) app).rpush(key, value);
        }
        if (app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            Long ret = jedis.rpush(key, value);
            jedis.close();
            return ret;
        }
        return null;
    }

    @Override
    public List<Object> removeAll(String key, List<String> value) {
        return new BatchRedisOption(getRedisApp()) {
            @Override
            public void option(PipelineBase pipelineBase, Object s) {
                pipelineBase.lrem(key, 0, s + "");
            }
        }.batch(key, value);
    }

    @Override
    public Long remove(String key, Object value) {
        Object app = getRedisApp();
        if (app instanceof JedisCluster) {
            return ((JedisCluster) app).lrem(key, 0, value + "");
        }
        if (app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            Long ret = jedis.lrem(key, 0, value + "");
            jedis.close();
            return ret;
        }
        return null;
    }

    @Override
    public List<String> get(String key, Integer start, Integer end) {
        Object app = getRedisApp();
        if (app instanceof JedisCluster) {
            return ((JedisCluster) app).lrange(key, start, end);
        }
        if (app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            List<String> ret = jedis.lrange(key, start, end);
            jedis.close();
            return ret;
        }
        return null;
    }

    @Override
    public String rpop(String key) {
        Object app = getRedisApp();
        if (app instanceof JedisCluster) {
            return ((JedisCluster) app).rpop(key);
        }
        if (app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            String ret = jedis.rpop(key);
            jedis.close();
            return ret;
        }
        return null;
    }

    @Override
    public String lpop(String key) {
        Object app = getRedisApp();
        if (app instanceof JedisCluster) {
            return ((JedisCluster) app).lpop(key);
        }
        if (app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            String ret = jedis.lpop(key);
            jedis.close();
            return ret;
        }
        return null;
    }

    @Override
    public Long lpush(String key, String value) {
        Object app = getRedisApp();
        if (app instanceof JedisCluster) {
            return ((JedisCluster) app).lpush(key, value);
        }
        if (app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            Long ret = jedis.lpush(key, value);
            jedis.close();
            return ret;
        }
        return null;
    }

    @Override
    public Long rpush(String key, String value) {
        return add(key, value);
    }

}
