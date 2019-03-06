package com.chezhibao.bigdata.pojo;

import lombok.Data;

import java.util.List;

/**
 * 实时报表
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/11.
 */
@Data
public class CBR {
    /**
     * 报表ID
     */
    private String realreportId;
    /**
     * 报表name
     */
    private String realreportName;
    /**
     * 报表的描述
     */
    private String desc;
    /**
     * 报表的层级
     */
    private Integer level;
    /**
     * 查询数据的sql
     */
    private List<QuerySql> querySql;
    /**
     * 报表的字段
     */
    private List<CBRColumn> columns;
    /**
     * 报表的参数
     */
    private List<CBRParam> cbrParams;
}
