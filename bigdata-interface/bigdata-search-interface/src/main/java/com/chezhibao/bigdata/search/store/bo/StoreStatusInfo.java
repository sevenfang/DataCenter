package com.chezhibao.bigdata.search.store.bo;

import java.io.Serializable;
import java.util.List;

/**
 * 门店异常状态信息，包含门店和对应的异常状态的车辆
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/3.
 */
public class StoreStatusInfo implements Serializable {
    /**
     * 门店ID
     */
    private Integer storeId;
    /**
     * 车辆异常状态标识
     */
    private Integer abnormalStatusFlag;
    /**
     * 对应异常状态的车辆列表
     */
    private List<Integer> carIds;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getAbnormalStatusFlag() {
        return abnormalStatusFlag;
    }

    public void setAbnormalStatusFlag(Integer abnormalStatusFlag) {
        this.abnormalStatusFlag = abnormalStatusFlag;
    }

    public List<Integer> getCarIds() {
        return carIds;
    }

    public void setCarIds(List<Integer> carIds) {
        this.carIds = carIds;
    }

    @Override
    public String toString() {
        return "StoreStatusInfo{" +
                "storeId=" + storeId +
                ", abnormalStatusFlag=" + abnormalStatusFlag +
                ", carIds=" + carIds +
                '}';
    }
}
