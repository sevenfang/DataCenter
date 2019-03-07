package com.chezhibao.bigdata.search.auction;

/**
 * 竞拍中车辆上架，竞拍车辆信息存储到redis中
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/27.
 */
public interface AuctionCarInfoToRedisService {
    /**
     * 上架车辆的信息存储到redis中
     * 对接人：赵旭江
     * @param auctionCarId
     * @return
     */
    void getPutOnSaleCar(Integer auctionCarId,Integer carModelId);
}
