package com.chezhibao.bigdata.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/1.
 */
@Data
public class QuerySqlBO {
    /**
     * sql的唯一标识
     */
    private Integer id;

    /**
     * sql的唯一标识
     */
    private Integer order;

    /**
     * sql的名称
     */
    private String name;

    /**
     * 所属报表
     */
    private String realreportId;
    /**
     * 查询的sql
     */
    private String sql;
    /**
     * 查询的数据库名称
     */
    private String datasource;

    private Date createdTime;
    private Date updatedTime;
}
