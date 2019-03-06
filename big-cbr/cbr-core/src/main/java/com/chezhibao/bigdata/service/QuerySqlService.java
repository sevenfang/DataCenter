package com.chezhibao.bigdata.service;

import com.chezhibao.bigdata.bo.QueryDataBO;
import com.chezhibao.bigdata.bo.QuerySqlBO;
import com.chezhibao.bigdata.pojo.QuerySql;

import java.util.List;

/**
 * 获取查询Sql的服务
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/1.
 */

public interface QuerySqlService {
    /**
     * 获取查询的sql
     * @param queryDataBO
     * @return 多条Sql 第一条为当前的，另一条为对比用的
     */
    List<QuerySql> getSql(QueryDataBO queryDataBO);

    /**
     * 删除指定层级的查询sql
     * @param index
     * @return
     */
    Boolean delSqlLevel(String realreportId,List<Integer> index);

    /**
     * 根据名称获取查询的sql
     * @param realreportId
     * @param name
     * @return
     */
    QuerySqlBO getSqlByName(String realreportId, String name);
    /**
     * 根据报表ID获取查询的sql
     * @param realreportId
     * @return
     */
    List<QuerySqlBO> getSqlByRealreportId(String realreportId);

    /**
     * 更新报表sql
     * @param querySql
     * @return
     */
    Boolean updateSqlByName(QuerySqlBO querySql);
    /**
     * 更新报表sql
     * @param querySql
     * @return
     */
    Boolean updateSqlById(QuerySqlBO querySql);


    /**
     * 存储报表sql
     * @param querySql
     * @return
     */
    Boolean saveSql(QuerySqlBO querySql);

    /**
     * 批量存储报表sql
     * @param querySqls
     * @return
     */
    Boolean saveOrupdateSql(List<QuerySqlBO> querySqls);
}
