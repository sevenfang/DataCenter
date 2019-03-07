package com.chezhibao.bigdata.dbms.server.vo;

import lombok.Data;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/20.
 */
@Data
public class ColumnVO {
    /**
     * 数据库实例类型
     */
    private Integer dbType;
    /**
     * 数据库实例名称
     */
    private String  dbName;
    /**
     * 数据库schema
     */
    private String  schema;
    /**
     * 数据库表名
     */
    private String  tableName;
    /**
     * 数据字段名
     */
    private String  columnName;
}
