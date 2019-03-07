package com.chezhibao.bigdata.search.es.service;

import com.chezhibao.bigdata.search.es.bo.RecommendInfo;
import com.chezhibao.bigdata.search.es.bo.SearchPage;

import java.util.List;

/**
 * 竞拍收车推荐接口
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/7.
 */
public interface ChargeCarAuctionService {
    /**
     * 分页获取推荐排序的车辆
     * @param buyerId 商户ID
     * @param searchPage 查询页对象 其中sessionName默认为chargeCar
     * @return 返回的推荐车辆
     */
    List<Integer> getRecommendCarInfo(Integer buyerId, SearchPage searchPage);

    /**
     * 获取排序在前面的车辆
     * @param buyerId 商户ID
     * @param count 车辆的数量
     * @return 返回的推荐车辆
     */
    List<Integer> getRecommendCarInfo(Integer buyerId,Integer count);

    /**
     * 下架车通知
     * @param carIds 下架车辆ID
     */
    void vehicleOffTheShelf(List<Integer> carIds);
}
