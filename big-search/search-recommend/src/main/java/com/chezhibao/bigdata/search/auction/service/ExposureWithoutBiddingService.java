package com.chezhibao.bigdata.search.auction.service;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/17.
 */
public interface ExposureWithoutBiddingService {
    /**
     * 增加曝光车 这个是第一版
     * 将曝光车添加到每一页的首部
     * 新版{@link ExposureWithoutBiddingService#addExposureWithoutBidding(Integer, List)}
     * @param buyerId
     * @param size
     * @param recommendCars
     */
    @Deprecated
    void addExposureWithoutBidding(Integer buyerId, Integer size, List<Integer> recommendCars);

    /**
     * 新版直接将数据追加到推荐车的头部
     * @param buyerId
     * @param recommendCars
     */
    void addExposureWithoutBidding(Integer buyerId, List<Integer> recommendCars);
}
