package com.chezhibao.bigdata.search.es.bo;

import java.io.Serializable;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/21.
 */
public class AuctionPromotionBO implements Serializable {
    /**
     * 车辆ID
     */
    private Integer carId;
    /**
     * 置换金
     */
    private Integer replaceMoney;
    /**
     * 物流费
     */
    private Integer logisticsMoney;

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getReplaceMoney() {
        return replaceMoney;
    }

    public void setReplaceMoney(Integer replaceMoney) {
        this.replaceMoney = replaceMoney;
    }

    public Integer getLogisticsMoney() {
        return logisticsMoney;
    }

    public void setLogisticsMoney(Integer logisticsMoney) {
        this.logisticsMoney = logisticsMoney;
    }

    @Override
    public String toString() {
        return "AuctionPromotionBO{" +
                "carId=" + carId +
                ", replaceMoney=" + replaceMoney +
                ", logisticsMoney=" + logisticsMoney +
                '}';
    }
}
