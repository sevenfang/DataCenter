package com.chezhibao.bigdata.cache.redis;

import com.chezhibao.bigdata.jedis.pipeline.JedisClusterPipeline;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.PipelineBase;
import redis.clients.util.Pool;

import java.util.List;

import static com.chezhibao.bigdata.dbms.common.LoggerUtil.REDIS_LOG;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/19.
 */
public abstract class BatchRedisOption {
    private Object app;

    public BatchRedisOption(Object app) {
        this.app = app;
    }

    /**
     * 具体的对每个值进行操作
     * @param pipelineBase
     * @param s 批量操作中的一个具体值
     */
    public abstract void option(PipelineBase pipelineBase,Object s);
    public List<Object> batch(String key, List<?> value){
        if(ObjectUtils.isEmpty(value)){
            return null;
        }
        if(app instanceof JedisCluster){
            JedisClusterPipeline jcp = JedisClusterPipeline.pipelined((JedisCluster) app);
            try {
                jcp.refreshCluster();
                for (Object s : value) {
                    option(jcp,s);
                }
                jcp.sync();
            }catch (Exception e){
                REDIS_LOG.error("【数据服务】批量删除list的值出错！！！key:{};values:{}",key,value,e);
                jcp.close();
            }
            return jcp.syncAndReturnAll();
        }

        if(app instanceof Pool) {
            Jedis jedis = ((Pool<Jedis>) app).getResource();
            Pipeline pipelined = jedis.pipelined();
            try {
                for (Object s : value) {
                    option(pipelined,s);
                }
                pipelined.sync();
            }catch (Exception e){
                REDIS_LOG.error("【数据服务】批量删除list的值出错！！！key:{};values:{}",key,value,e);
                try {
                    pipelined.close();
                }catch (Exception e1){
                    e1.printStackTrace();
                }
                jedis.close();
            }
            return pipelined.syncAndReturnAll();
        }
        return null;
    }
}
