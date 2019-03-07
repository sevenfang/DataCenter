package com.chezhibao.bigdata.dao.jdbctemplate;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/20.
 */
public interface CBRTemplate {
    /**
     * 简单查询结果 直接返回Map 没有group by join
     * @param sql
     * @param params
     * @return
     */
    List<Map<String,Object>> simpleQueryForList(String sql, Map<String,Object> params);
}
