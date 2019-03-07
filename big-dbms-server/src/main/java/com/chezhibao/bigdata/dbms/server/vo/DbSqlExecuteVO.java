package com.chezhibao.bigdata.dbms.server.vo;

import lombok.Data;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/25.
 */
@Data
public class DbSqlExecuteVO {
    private Integer dbType;
    private String name;
    private Boolean selectNext;
    private String sql;
    private Integer page;
    private Integer size;
}
