package com.chezhibao.bigdata.search.feed.core;

import com.chezhibao.bigdata.search.recommend.bo.FeedReqInfo;


/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/19.
 */
public interface RecommendStrategy {
    /**
     * 推荐
     * @param feedReqInfo
     * @return
     */
    void recommend(FeedReqInfo feedReqInfo);
}
