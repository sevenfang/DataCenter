package com.chezhibao.bigdata.search.es.service;

import com.chezhibao.bigdata.search.es.bo.AuctionPromotionBO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/21.
 */
public interface AuctionPromotionService {
    /**
     * 存储活动车辆信息
     * @param carId 车辆ID
     * @param date 时间
     * @return
     */
    Boolean saveAuctionPromotionCarInfo(Integer carId, Date date);
    /**
     * 存储活动车辆信息
     * @param carId 车辆ID
     * @param date 时间
     * @return
     */
    Boolean saveAuctionPromotionCarInfo(Integer carId, Date date, Map<String,Object> promotionInfo);
    /**
     * 查询活动车辆信息
     * @param carId 车辆ID
     * @return
     */
    List<AuctionPromotionBO> getAuctionPromotionCarInfos(List<Integer> carId);
}
