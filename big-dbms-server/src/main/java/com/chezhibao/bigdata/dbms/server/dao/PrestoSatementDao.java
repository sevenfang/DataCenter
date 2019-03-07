package com.chezhibao.bigdata.dbms.server.dao;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/6.
 */
public interface PrestoSatementDao {
    /**
     * 查找
     * @param dataSource 对应的数据源key
     * @param sql 对应的语句
     * @param params 传入的参数
     */

    List<LinkedHashMap<String,Object>> selectNext(String dataSource, String sql, Map<String, Object> params, HttpServletRequest request) throws  Exception;

    List<LinkedHashMap<String,Object>> executeQuery(String dataSource, String sql, Map<String, Object> params, HttpServletRequest request) throws  Exception;

    /**
     * 查询限量数据 默认限制为1000条
     * @param dataSource
     * @param sql
     * @param params
     * @return
     * @throws Exception
     */
    List<LinkedHashMap<String,Object>> executeQuery(String dataSource, String sql, Map<String, Object> params) throws  Exception;
}
