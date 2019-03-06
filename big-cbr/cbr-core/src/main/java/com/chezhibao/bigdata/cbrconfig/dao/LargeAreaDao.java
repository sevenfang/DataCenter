package com.chezhibao.bigdata.cbrconfig.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/18.
 */
public interface LargeAreaDao {
    //获取大区信息
    List<LinkedHashMap<String,Object>> getAllLargeArea();
    //更新大区信息
    void updateLargeArea(Map<String, Object> params);
    //删除大区信息
    void delLargeArea(Integer id);
    //添加大区信息
    void saveLargeArea(Map<String, Object> params);
}
