package com.chezhibao.bigdata.search.auction.bo;

import java.io.Serializable;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/27.
 */
public class CarFollowUpInfo implements Serializable {
    private Integer trakingNum;
    private double trakingTime;

    public Integer getTrakingNum() {
        return trakingNum;
    }

    public void setTrakingNum(Integer trakingNum) {
        this.trakingNum = trakingNum;
    }

    public double getTrakingTime() {
        return trakingTime;
    }

    public void setTrakingTime(double trakingTime) {
        this.trakingTime = trakingTime;
    }

    @Override
    public String toString() {
        return "CarFollowUpInfo{" +
                "trakingNum=" + trakingNum +
                ", trakingTime=" + trakingTime +
                '}';
    }
}
