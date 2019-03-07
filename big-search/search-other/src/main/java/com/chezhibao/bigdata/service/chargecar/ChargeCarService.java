package com.chezhibao.bigdata.service.chargecar;

import com.chezhibao.bigdata.search.es.bo.SearchPage;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/7.
 */
public interface ChargeCarService {
    /**
     * 处理第一页逻辑
     * @param buyerId 商户ID
     */
    void firstPageHandler(Integer buyerId, Integer count);

    /**
     * 获取分页的车辆
     * @param buyerId
     * @param searchPage
     * @return
     */
    List<Integer> getPageCar(Integer buyerId, SearchPage searchPage);

    /**
     *  返回指定商户的所有推荐车辆
     * @param buyerId
     * @param count
     * @return
     */
    List<Integer> getAllChargeCar(Integer buyerId, Integer count);

}
