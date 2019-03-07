package com.chezhibao.bigdata.search.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/10.
 */
public class SearchLogUtils {
    //竞拍推荐日志
    public static final Logger AUCTION_LOG = LoggerFactory.getLogger("com.chezhibao.bigdata.service.auction");
    //竞拍推荐日志
    public static final Logger FEED_LOG = LoggerFactory.getLogger("com.chezhibao.bigdata.service.feed");
    //收车日志
    public static final Logger CHARGE_CAR_LOG = LoggerFactory.getLogger("com.chezhibao.bigdata.chargecar");
    //相似车型日志
    public static final Logger SIMILAR_CAR_LOG = LoggerFactory.getLogger("com.chezhibao.bigdata.similarcar");
    //相似车型日志
    public static final Logger STORE_LOG = LoggerFactory.getLogger("com.chezhibao.bigdata.store");
    //好车推荐日志
    public static final Logger BETTER_CAR_LOG = LoggerFactory.getLogger("com.chezhibao.bigdata.bettercar");
    //竞拍车辆信息查询日志
    public static final Logger AUCTION_CAR_LOG = LoggerFactory.getLogger("com.chezhibao.bigdata.auctioncar");
    //渠道推荐查询日志
    public static final Logger CHANNEL_CAR_LOG = LoggerFactory.getLogger("com.chezhibao.bigdata.channel");
}
