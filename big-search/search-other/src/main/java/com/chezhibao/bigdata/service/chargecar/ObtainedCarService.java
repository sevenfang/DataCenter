package com.chezhibao.bigdata.service.chargecar;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/7.
 */
public interface ObtainedCarService {
    /**
     * 收车对应得redis下架key
     */
    String VEHICLEOFFTHESHELF = "auction:charge_car:off_the_shelf";

    /**
     * 获取下架车
     * @return 下架车ID
     */
    List<Integer> getObtainedCars();

    /**
     * 添加下架车
     */
    void addObtainedCars(List<String> carIds);
}
