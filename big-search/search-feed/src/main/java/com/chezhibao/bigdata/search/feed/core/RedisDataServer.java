package com.chezhibao.bigdata.search.feed.core;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.dbms.redis.ListRedisService;
import com.chezhibao.bigdata.search.feed.core.lua.ScriptServer;
import org.springframework.stereotype.Service;
import static com.chezhibao.bigdata.search.common.SearchLogUtils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/20.
 */
@Service
public class RedisDataServer {

    @Reference(check = false)
    private ListRedisService listRedisService;

    private ScriptServer scriptServer;

    public RedisDataServer(ScriptServer scriptServer) {
        this.scriptServer = scriptServer;
    }

    public Integer replaceValues(String key, Integer start, List<String> values){
        Integer result = 0;
        /**
         * 脚本逻辑：
         * 1、获取浏览过的数据
         * 2、去除已经浏览的车辆数据
         * 3、获取未浏览过的数据、去除推荐数据
         * 4、分别将以浏览数据和推荐数据、未浏览数据缓存进redis
         */
        List<String> keys = new ArrayList<>(1);
        keys.add(key);
        List<String> args = new ArrayList<>(values.size()+1);
        args.add(start+"");
        args.addAll(values);
        try {
            Object eval = listRedisService.eval(scriptServer.getLuaScriptByName("replaceValues"), keys, args);
            result = Integer.parseInt(eval + "");
        }catch (Exception e){
            FEED_LOG.error("【实时推荐】替换list推荐数据失败！！！key{}，value{}",key,values,e);
        }
        return result;

    }
}
