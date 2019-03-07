package com.chezhibao.bigdata.search.recommend;

import java.util.List;
import java.util.Map;

/**
 * 查询竞拍实时数据
 * Created by jerry on 2018/11/5.
 */
public interface RecommendBiddingDataService {
    /**
     * 查询storm中 计算的 出价数据
     * @param auctionCarIds 若干个竞拍车辆ID
     * @return value值存的是：每辆车的出价记录，例如：{"65353":28700,"70147":34400,"16605":26000,"180165":45200}
     */
    Map<Integer, String> getBiddingDetail(List<Integer> auctionCarIds);
}
