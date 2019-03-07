package com.chezhibao.bigdata.dao.jdbctemplate;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/26.
 */
public interface DwdmTemplate {
    /**
     * 简单查询结果 直接返回Map 没有group by join
     * @param sql
     * @param params
     * @return
     */
    List<Map<String,Object>> simpleQueryForList(String sql,Map<String,Object> params);

    /**
     * 查询结果 根据用户需求返回指定的数据类型 没有group by join
     * @param sql 查询sql
     * @param params 查询参数
     * @param elementType 指定的结果类型
     * @param <T> 返回类型
     * @return
     */
    <T> List<T> simpleQueryForList(String sql,Map<String,Object> params,Class<T> elementType);
}
