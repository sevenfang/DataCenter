package com.chezhibao.bigdata.search.auction.service;

import java.util.List;

/**
 * 数据部的个性化推荐服务接口
 * 提供根据商户推荐算好的数据
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/20.
 */
public interface AucsAllRecommendSjbService {
    /**
     * Redis版本
     * 根据商户ID查询推荐车辆（已经排序好的,数据部算好的结果，可能部分车没有）
     * @param buyerId
     * @return
     */
     List<Integer> getSJBRecommendCarInfoByBuyerId(Integer buyerId);
    /**
     * Hbase版本
     * 根据商户ID查询推荐车辆（已经排序好的,数据部算好的结果，可能部分车没有）
     * @param buyerId
     * @return
     */
     List<Integer> getSJBRecommendCarInfoByBuyerIdFromHbase(Integer buyerId);

    /**
     * 获取对应商户的所欲推荐车辆
     * @param buyerId
     * @return
     */
    List<Integer> getAllRecommendData(Integer buyerId);

    /**
     * 获取商户未出价的车辆，提高其中好车的曝光度
     * @param buyerId
     * @return
     */
    List<Integer> getExposureWithoutBidding(Integer buyerId);
}
