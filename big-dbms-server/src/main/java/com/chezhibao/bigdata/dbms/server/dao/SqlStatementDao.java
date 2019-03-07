package com.chezhibao.bigdata.dbms.server.dao;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/4.
 */
public interface SqlStatementDao{
    /**
     * 存储
     * @param dataSource 对应的数据源key
     * @param sql 对应的语句
     * @param params 传入的参数
     */
    Boolean insert(String dataSource, String sql, Map<String, Object> params);

    /**
     * 删除
     * @param dataSource 对应的数据源key
     * @param sql 对应的语句
     * @param params 传入的参数
     */

    void delete(String dataSource, String sql, Map<String, Object> params);

    /**
     * 更新
     * @param dataSource 对应的数据源key
     * @param sql 对应的语句
     * @param params 传入的参数
     */

    void update(String dataSource, String sql, Map<String, Object> params);

    /**
     * 查找
     * @param dataSource 对应的数据源key
     * @param sql 对应的语句
     * @param params 传入的参数
     */

    List<LinkedHashMap<String,Object>> select(String dataSource, String sql, Map<String, Object> params, HttpServletRequest request);

    Boolean stopStatement(String psId, HttpServletRequest request);
}
