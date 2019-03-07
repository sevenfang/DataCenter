package com.chezhibao.bigdata.search.recommend;

import com.chezhibao.bigdata.search.recommend.bo.FeedReqInfo;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/10.
 */
public interface RecomendFeedService {
    /**
     * 发送用户的请求信息（点击车辆列表时）
     * @param feedReqInfo
     */
    void sendReqInfo(FeedReqInfo feedReqInfo);
}
