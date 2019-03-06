package com.chezhibao.bigdata.bo;

import lombok.Data;

import java.util.Map;

/**
 * 查询sql的参数对象
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/1.
 */
@Data
public class QueryDataBO {
    /**
     * 报表ID（名称）
     */
    private String realreportId;
    /**
     * 查询的报表层级
     */
    private Integer level;
    /**
     * 查询的参数
     */
    private Map<String,Object> params;
}
