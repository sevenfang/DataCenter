package com.chezhibao.bigdata.search.common;

import com.chezhibao.bigdata.common.log.LogService;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/21.
 */
public enum SearchLogEnum implements LogService {
    //竞拍推荐日志
    AUCTION_LOG("com.chezhibao.bigdata.service.auction"),
    //竞拍推荐日志
    FEED_LOG("com.chezhibao.bigdata.service.feed"),
    //收车日志
    CHARGE_CAR("com.chezhibao.bigdata.chargecar"),
    //相似车型日志
    SIMILAR_CAR("com.chezhibao.bigdata.similarcar"),
    //相似车型日志
    STORE("com.chezhibao.bigdata.store"),
    //好车推荐日志
    BETTER_CAR("com.chezhibao.bigdata.bettercar"),
    //竞拍车辆信息查询日志
    AUCTION_CAR("com.chezhibao.bigdata.auctioncar"),
    //渠道推荐查询日志
    CHANNEL_CAR("com.chezhibao.bigdata.channel"),;

    private String logFileName;

    SearchLogEnum(String fileName) {
        this.logFileName = fileName;
    }

    @Override
    public String getLoggerName() {
        return logFileName;
    }
}
