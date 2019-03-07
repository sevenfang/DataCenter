package com.chezhibao.bigdata.search.store;

import com.chezhibao.bigdata.search.store.bo.StoreStatusInfo;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/3.
 */
public interface StoreService {
    /**
     * 根据传入的门店信息获取对应异常状态的车辆信息
     * 查询车辆结果直接存入到{@link com.chezhibao.bigdata.search.store.bo.StoreStatusInfo#carIds}中
     * @param stores
     * @return
     */
    List<StoreStatusInfo> getCarIdsOfAbnormalStore(List<StoreStatusInfo> stores);
}
