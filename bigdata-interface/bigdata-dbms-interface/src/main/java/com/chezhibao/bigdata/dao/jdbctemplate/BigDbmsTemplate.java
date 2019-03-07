package com.chezhibao.bigdata.dao.jdbctemplate;

import com.chezhibao.bigdata.dao.pojo.DbmsParams;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/20.
 */
public interface BigDbmsTemplate {
    /**
     * 简单查询结果 直接返回Map 没有group by join
     * @param dataSource 数据源名称
     * @param sql
     * @param params
     * @return
     */
    List<Map<String,Object>> simpleQueryForList(DbmsParams dataSource, String sql, Map<String,Object> params);
}
