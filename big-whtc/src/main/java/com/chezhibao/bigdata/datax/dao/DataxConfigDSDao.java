package com.chezhibao.bigdata.datax.dao;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/26.
 */
public interface DataxConfigDSDao {
    /**
     * 获取数据源配置信息
     * @return
     */
    List<Map<String,Object>> getDs(Map<String,Object> params);

    /**
     * 添加数据源配置信息
     * @return
     */
    Boolean saveDs(Map<String,Object> params);

    /**
     * 更新数据源配置信息
     * @return
     */
    Boolean updateDs(Map<String,Object> params);

    /**
     * 删除数据源配置信息
     * @return
     */
    Boolean delDs(Map<String,Object> params);
}
