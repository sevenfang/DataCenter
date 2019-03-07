package com.chezhibao.bigdata.search.recommend.channel;

import com.chezhibao.bigdata.search.es.bo.SearchPage;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun 拜访
 * Created by WangCongJun on 2018/11/30.
 */
public interface ChannelCarService {
    /**
     * 根据渠道ID获取所对应的所有商户ID
     * @param channelId 渠道ID
     * @return
     */
    List<Integer> getAllBuyersByChannelId(Integer channelId);

    /**
     * 根据渠道ID获取所对应的所有商户ID的拜访情况
     * @param channelId 渠道ID
     * @return 返回的是key为渠道ID，value为是否拜访的Boolean值
     */
    Map<Integer,Boolean> getAllBuyersByVisitedInfo(Integer channelId);

    /**
     * 根据渠道ID获取需要开拓的品牌
     * @param channelId
     * @return
     */
    List<Map<String, Object>> getExploreBrandInfo(Integer channelId);

    /**
     * 根据商户ID获取对应的所有排序车辆ID和检测ID
     * @param buyerId 商户ID
     * @param searchPage 查询的分页信息
     * @return
     */
    Map<Integer,Integer> getAllCarIdAndDetectedIdByBuyerId(Integer buyerId, SearchPage searchPage);
}
