package com.chezhibao.bigdata.search.feed.bo;

import java.util.List;

/**
 * 推荐事物
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/19.
 */
public class RcommendInfo {
    private List<Integer> carIds;

    public List<Integer> getCarIds() {
        return carIds;
    }

    public void setCarIds(List<Integer> carIds) {
        this.carIds = carIds;
    }

    @Override
    public String toString() {
        return "RcommendInfo{" +
                "carIds=" + carIds +
                '}';
    }
}
