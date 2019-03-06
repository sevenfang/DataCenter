package com.chezhibao.bigdata.pojo;

import lombok.Data;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/1.
 */
@Data
public class QuerySql {
    /**
     * 列的唯一标识
     */
    private Integer id;
    /**
     * sql的名称
     */
    private String name;
    /**
     * 查询的sql
     */
    private String sql;
    /**
     * 查询的数据库名称
     */
    private String datasource;
}
