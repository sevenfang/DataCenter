package com.chezhibao.bigdata.service;

import com.chezhibao.bigdata.vo.QueryDataVo;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/30.
 */
public interface QueryDataService {


    List<LinkedHashMap<String,Object>> getData(QueryDataVo queryDataVo);
    List<LinkedHashMap<String,Object>> getCbrData(QueryDataVo queryDataVo);
    List<LinkedHashMap<String,Object>> getDataBySql(QueryDataVo queryDataVo);
}
