package com.chezhibao.bigdata.search.auction.service;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/4.
 */
public interface AuctionCarInitService {
    /**
     * 初始化所有场次车辆数据
     * @return
     */
    Boolean initAuctionCar();

    /**
     * 获取指定场次车辆数据
     * @return
     */
    Object getCars(String sessionName);
}
