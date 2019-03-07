package com.chezhibao.bigdata.search.similarcar;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/7.
 */
public interface SimilarCarService {

    /**
     * 获取相似车辆
     * @param auctioncarId 竞拍车辆id
     */
    List<Integer> getsimilarCarByauctionCarid(String auctioncarId);



}
