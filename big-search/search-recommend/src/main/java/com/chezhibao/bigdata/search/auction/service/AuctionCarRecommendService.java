package com.chezhibao.bigdata.search.auction.service;

import com.chezhibao.bigdata.search.es.bo.SearchPage;

import java.util.List;

/**
 * 拍卖推荐服务接口
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/16.
 */
public interface AuctionCarRecommendService {
    /**
     * 查询推荐车辆
     * @param buyerId
     * @return
     */
    List<Integer> queryRecommendCars(Integer buyerId, SearchPage searchPage);

    /**
     * 查询推荐车辆
     * @param auctionCars 默认车辆
     * @param buyerId
     * @param searchPage 查询场次分页信息
     * @return
     */
    List<Integer> queryRecommendCars(List<Integer> auctionCars, Integer buyerId, SearchPage searchPage);

    /**
     * 计算新排序；
     * 防止有些车辆没有计算到排序中
     * 先看recommendCar是否包含defaultCar，包含直接取交集返回即可
     * 不包含 取交集后  将defaultCar多出来的car插入到结果集后面
     * @param recommendCar 推荐排序的车辆信息
     * @param defaultCar   默认的车辆信息
     * @return
     */
    List<Integer> computedSortRecommendCarInfo(List<Integer> recommendCar, List<Integer> defaultCar);

    /**
     * 获取默认排序 当推荐系统完全崩溃的情况下
     * @param defaultCar
     * @return
     */
    List<Integer> getDefaultResult(List<Integer> defaultCar, SearchPage searchPage);

}
