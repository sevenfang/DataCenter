package com.chezhibao.bigdata.cbrconfig.service;

import com.chezhibao.bigdata.cbrconfig.pojo.ChannelCity;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/18.
 */
public interface ChannelCityService {
    /**
     * 获取所有的城市信息
     * @return
     */
    List<ChannelCity> getAllCity();

    /**
     * 删除城市信息
     * @param channelCity
     * @return
     */
    Boolean delChannelCity(ChannelCity channelCity);

    /**
     * 更新城市信息
     * @param channelCity
     * @return
     */
    Boolean updateChannelCity(ChannelCity channelCity);

    /**
     * 添加城市信息
     * @param channelCity
     * @return
     */
    Boolean saveChannelCity(ChannelCity channelCity);

    /**
     * 获取城市ID和名称
     * @return
     */
    Map<Integer,Map<Integer,String>> getCityIdandName();
    /**
     * 更新某已城市的所属大区
     * @param cityName
     * @param largeAreaName
     * @return
     */
    Boolean updateCityLargeArea(String cityName, String largeAreaName);
}
