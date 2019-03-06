package com.chezhibao.bigdata.cbrconfig.service;

import com.chezhibao.bigdata.cbrconfig.pojo.LargeArea;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/18.
 */
public interface LargeAreaService {
    //获取所有大区信息
    List<LargeArea> getAllLargeAreaInfo();
    //更新修改大区信息
    Boolean updateLargeArea(LargeArea largeArea);
    //删除大区信息
    Boolean delLargeArea(LargeArea largeArea);
    //添加大区信息
    Boolean saveLargeArea(LargeArea largeArea);
    //获取大区ID和名称
    Map<Integer,String> getLargeAreaIdAndName();

}
