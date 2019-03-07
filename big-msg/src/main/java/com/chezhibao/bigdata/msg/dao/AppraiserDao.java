package com.chezhibao.bigdata.msg.dao;

import com.chezhibao.bigdata.msg.pojo.Appraiser;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/11.
 */
public interface AppraiserDao {
    List<Map<String,Object>> queryAppraiserByCity(String city);
}
