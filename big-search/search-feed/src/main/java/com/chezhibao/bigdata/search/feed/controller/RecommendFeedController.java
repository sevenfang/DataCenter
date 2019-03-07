package com.chezhibao.bigdata.search.feed.controller;

import com.chezhibao.bigdata.search.recommend.RecomendFeedService;
import com.chezhibao.bigdata.search.recommend.bo.FeedReqInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/21.
 */
@RestController
public class RecommendFeedController {
    @Autowired
    private RecomendFeedService recomendFeedService;
    @RequestMapping("test/fedd")
    public Object test(){
        FeedReqInfo feedReqInfo = new FeedReqInfo();
        feedReqInfo.setFeedBuyerId(308);
        feedReqInfo.setFeedAuctioncarid(299144);
        feedReqInfo.setFeedPage(1);
        feedReqInfo.setFeedRowNum(9);
        feedReqInfo.setFeedTabName("chargeCar");
        recomendFeedService.sendReqInfo(feedReqInfo);
        return "success";
    }
}
