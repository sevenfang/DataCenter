package com.chezhibao.bigdata.cbrconfig.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/18.
 */
public interface ChannelCityDao {
    /**
     * 获取所有的city信息
     * @return
     */
    List<LinkedHashMap<String,Object>> getAllChannelCity();

    /**
     * 删除城市信息
     * @param cityId
     * @return
     */
    Boolean delChannelCity(Integer cityId);

    /**
     * 添加城市信息
     * @param params
     * @return
     */
    Boolean addChannelCity(Map<String, Object> params);

    /**
     * 更新城市信息
     * @param params
     * @return
     */
    Boolean updateChannelCity(Map<String, Object> params);
}
