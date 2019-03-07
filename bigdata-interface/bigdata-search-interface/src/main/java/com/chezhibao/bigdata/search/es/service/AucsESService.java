package com.chezhibao.bigdata.search.es.service;

import com.chezhibao.bigdata.search.es.bo.RecommendInfo;
import com.chezhibao.bigdata.search.es.bo.SearchPage;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/5/14
 * Created by WangCongJun on 2018/5/14.
 */
public interface AucsESService {


    /**
     * 删除过期车辆
     * @param auctioncarid
     */
    void delExpiredCar(Integer auctioncarid);

    /**
     * 新增车辆推荐信息
     * @param auctionCarId auctionId
     * @param auctionCarId sessionName
     */
    void addNewCar(Integer auctionCarId,String sessionName);

    /**
     * 批量新增车辆推荐信息
     * @param auctionCarIds auctionId
     */
    void addNewCar(List<Integer> auctionCarIds,String sessionName);

    /**
     * 批量删除过期车辆
     * @param auctioncarid
     */
    void delExpiredCar(List<Integer> auctioncarid ,String sessionName);

    /**
     *  指定场次全部车辆推荐
     * @param buyerId 商户ID
     * @param searchPage 分页数据
     * @return auctionCarId    int  竞拍车辆ID
     *         rank    double    排序字段
     *
     */
    List<RecommendInfo> getRecommendInfoByBuyerId(Integer buyerId, SearchPage searchPage);

    /**
     *  筛选结果车辆推荐
     * @param buyerId 商户ID
     * @param auctionCarIds 筛选结果车辆ID集合
     * @param searchPage 分页数据
     * @return auctionCarId    int  竞拍车辆ID
     *         rank    double    排序字段
     *
     */
    List<RecommendInfo> getRecommendInfoByBuyerId(List<Integer> auctionCarIds, Integer buyerId, SearchPage searchPage);

    /**
     *  豪华场、寄售场
     * @param buyerId 商户ID
     * @return auctionCarId    int  竞拍车辆ID
     *         rank    double    排序字段
     *
     */
    List<RecommendInfo> getRecommendInfoByBuyerId(List<Integer> auctionCarIds, Integer buyerId);

}
