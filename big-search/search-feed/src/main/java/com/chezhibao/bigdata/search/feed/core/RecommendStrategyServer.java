package com.chezhibao.bigdata.search.feed.core;

import com.chezhibao.bigdata.config.client.ParamsUtil;
import com.chezhibao.bigdata.search.common.ApplicationContextHelper;
import com.chezhibao.bigdata.search.feed.core.strategy.DefaultRecommendStrategy;
import com.chezhibao.bigdata.search.recommend.bo.FeedReqInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import static com.chezhibao.bigdata.search.common.SearchLogUtils.*;

import java.util.List;

/**
 * 推荐策略选择
 *
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/19.
 */
@Service
public class RecommendStrategyServer {
    private static final String RECOMMEND_ALL = "feed.recommendAll";
    private static final String RECOMMEND_LIST = "feed.recommendList";

    private RedisDataServer redisDataServer;

    public RecommendStrategyServer(RedisDataServer redisDataServer) {
        this.redisDataServer = redisDataServer;
    }

    RecommendStrategy choiceStrategy(FeedReqInfo feedReqInfo) {
        try {
            String dynamicValue = ParamsUtil.getDynamicValue(RECOMMEND_ALL);
            if (!StringUtils.isEmpty(dynamicValue) && Boolean.parseBoolean(dynamicValue)) {

                return ApplicationContextHelper.get(DefaultRecommendStrategy.class);
            }
            List<Integer> dynamicValueList = ParamsUtil.getDynamicValueList(RECOMMEND_LIST, Integer.class);
            if (!ObjectUtils.isEmpty(dynamicValueList)
                    && dynamicValueList.contains(feedReqInfo.getFeedBuyerId())) {
                return ApplicationContextHelper.get(DefaultRecommendStrategy.class);
            }
        } catch (Exception e) {
            FEED_LOG.error("【实时推荐】推荐失败!!{}", feedReqInfo, e);
        }
        return null;
    }
}
