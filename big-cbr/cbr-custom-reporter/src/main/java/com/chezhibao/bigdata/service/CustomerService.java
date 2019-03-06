package com.chezhibao.bigdata.service;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/5/28.
 */
public interface CustomerService {

    /**
     * 获取运用指标数据
     * @param id  用户ID
     * @param type 用户类型
     * @param time 时间
     * @param orgIds 组织ID
     * @return
     */
    Object getOperationalIndicators( String id, int type, String time, List<Integer> orgIds);

    /**
     * OCSA基础数据（新）
     * 加载客户意向数据量
     * @param id
     * @param type
     * @param time
     * @param cityIds
     * @return
     */
    Map<String,Object> loadOCSADataNew(String id, int type, String time, List<Integer> cityIds);
}
