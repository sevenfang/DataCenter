package com.chezhibao.bigdata.dao;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/5/28.
 */
public interface CustomerDao {
    /**
     * 获取报表中的最大时间
     * @return
     */
    String getMaxLoadDateTime();

    /**
     * 加载运营指标数据
     * @param id
     * @param type
     * @param time
     * @param list
     * @return
     */
    List<Map<String, Object>> loadData(String id, int type, String time, List<Integer> list);

    /**
     * OCSA基础数据（新）
     * @return
     */
    String getMaxLoadOCSADateTimeNew();

    /**
     * OCSA基础数据（新）
     * @param id
     * @param type
     * @param time
     * @param cityIds
     * @return
     */
    List<Map<String,Object>> loadOCSADataNew(String id, int type, String time, List<Integer> cityIds);
}
