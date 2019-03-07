package com.chezhibao.bigdata.datax.service;

import com.chezhibao.bigdata.datax.pojo.DataxConfigDS;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/26.
 */
public interface DataxConfigDSService {
    List<Map<String,Object>> getDS(DataxConfigDS dataxConfigDS);
    DataxConfigDS getDS(String name);
    DataxConfigDS getDSById(Integer id);
    Boolean saveDS(DataxConfigDS dataxConfigDS);
    Boolean delDS(DataxConfigDS dataxConfigDS);
    Boolean updateDS(DataxConfigDS dataxConfigDS);
}
