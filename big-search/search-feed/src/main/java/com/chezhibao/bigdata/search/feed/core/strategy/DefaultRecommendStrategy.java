package com.chezhibao.bigdata.search.feed.core.strategy;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.dbms.redis.IRedisService;
import com.chezhibao.bigdata.dbms.redis.recommend.RecommendListRedisService;
import com.chezhibao.bigdata.search.common.util.RedisKeyUtils;
import com.chezhibao.bigdata.search.feed.core.RecommendStrategy;
import com.chezhibao.bigdata.search.feed.core.RedisDataServer;
import com.chezhibao.bigdata.search.recommend.bo.FeedReqInfo;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static com.chezhibao.bigdata.search.common.SearchLogUtils.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/19.
 */
@Component
public class DefaultRecommendStrategy implements RecommendStrategy {

    private RedisDataServer redisDataServer;

    @Reference(check = false)
    private IRedisService redisService;
    @Reference(check = false)
    private RecommendListRedisService listRedisService;
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    /**
     * expx EX|PX, expire time units: EX = seconds; PX = milliseconds
     */
    private static final String SET_WITH_EXPIRE_TIME = "EX";

    private static final String LOCK_RECOMMEND_PREFIX = "lock:recommend:";
    /**
     * 相似的车辆
     */
    private static final String FEED_SIMILAR_CAR_PREFIX = "similar:feed:default:";

    public DefaultRecommendStrategy(RedisDataServer redisDataServer) {
        this.redisDataServer = redisDataServer;
    }

    @Override
    public void recommend(FeedReqInfo feedReqInfo) {
        FEED_LOG.info("【实时推荐】执行默认推荐策略{}", feedReqInfo);
        Integer buyerId = feedReqInfo.getFeedBuyerId();
        String ret = redisService.set(LOCK_RECOMMEND_PREFIX.concat(buyerId + ""), "1", SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 5);
        //判断是否抢到锁
        if (ret.equals(LOCK_SUCCESS)) {
            Integer auctioncarid = feedReqInfo.getFeedAuctioncarid();
            /**
             * 获取前10辆车
             */
            List<String> strings = listRedisService.get(FEED_SIMILAR_CAR_PREFIX + auctioncarid, 0, 10);
            FEED_LOG.info("【实时推荐】执行默认推荐策略获取推荐车辆{}", strings);
            if (!ObjectUtils.isEmpty(strings)) {
                String feedTabName = feedReqInfo.getFeedTabName();
                Integer feedLoadTotalSize = feedReqInfo.getFeedLoadTotalSize();
                Integer startRowNum;
                //判断列表最后刷新的位置是否为空
                if (ObjectUtils.isEmpty(feedLoadTotalSize)) {
                    //从下一页开始推荐
                    Integer feedPage = feedReqInfo.getFeedPage();
                    startRowNum = feedPage * 10;
                } else {
                    //从最后加载的位置开始
                    startRowNum = feedLoadTotalSize + 1;
                }

                String key = RedisKeyUtils.getReCommendCarInfoQueryKey(buyerId, feedTabName);
                Integer integer = redisDataServer.replaceValues(key, startRowNum, strings);
                if (integer != 1) {
                    FEED_LOG.info("【实时推荐】执行默认推荐策略失败{}", feedReqInfo);
                }
            }
        }
    }
}
