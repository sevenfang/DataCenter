package com.chezhibao.bigdata.msg.dao;

import com.chezhibao.bigdata.msg.pojo.AppraiserPrice;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/10.
 */
public interface AppraiserPriceDao {
    /**
     * 存储估价
     * @param appraiserPrice
     */
    Boolean save(AppraiserPrice appraiserPrice);

    /**
     * 获取估价
     * @param appraiserPrice
     */
    List<Map<String,Object>> get(AppraiserPrice appraiserPrice);

    /**
     * 更新出价时间和价格
     * @param appraiserPrice
     * @return
     */
    void updateAppraiserPrice(AppraiserPrice appraiserPrice);

    /**
     * 更新估价单的状态
     * @param appraiserPrice
     * @return
     */
    void updateAppraiserStatus(AppraiserPrice appraiserPrice);
}
