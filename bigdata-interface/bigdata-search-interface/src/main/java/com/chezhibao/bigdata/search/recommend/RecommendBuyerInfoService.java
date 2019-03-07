package com.chezhibao.bigdata.search.recommend;

import com.chezhibao.bigdata.search.recommend.bo.RecommendBidCarIfo;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/25.
 */
public interface RecommendBuyerInfoService {
    /**
     * 根据车编返回推荐商户
     * @param carId
     * @return
     */
    List<String> getBuyerIdsByCarId(Integer carId,Integer channelId);

    /**
     * 根据车辆推荐给商户
     * @param selUserId
     * @return
     */
    List<RecommendBidCarIfo> getRecommendBidCarIfo(Integer selUserId);
    /**
     * 根据车辆（搓车）推荐给商户
     * @param selUserId
     * @return
     */
    List<RecommendBidCarIfo> getCuocheRecommendBidCarIfo(Integer selUserId);

    /**
     * 根据车编号查感兴趣的商户
     * 对接周峰
     * @param carId
     * @return
     */
    List<Integer> getBuyIdsByCarId(Integer carId);
}
