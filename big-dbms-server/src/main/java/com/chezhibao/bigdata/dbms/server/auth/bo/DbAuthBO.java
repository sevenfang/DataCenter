package com.chezhibao.bigdata.dbms.server.auth.bo;

import lombok.Data;

/**
 * 查询数据相关的类
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/16.
 */
@Data
public class DbAuthBO {
    /**
     * 数据源名称
     */
    private String datasource;
    /**
     * 查询的sql
     */
    private String sql;
    /**
     * 用户ID
     */
    private Integer userId;
}
