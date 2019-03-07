package com.chezhibao.bigdata.realreport.auction;

import com.chezhibao.bigdata.realreport.auction.bo.BidAvgBO;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/6/26.
 */
public interface BidAvgService {

    /**
     * 根据渠道人员ID获取对应的户均信息
     * @param ChannelIds
     * @return json字符串
     */
    BidAvgBO getBidAvgByChannelIds(List<String> ChannelIds);
}
