package com.chezhibao.bigdata.vo;

import lombok.Data;

import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/30.
 */
@Data
public class QueryDataVo {

    /**
     * 查询的sql
     */
    private String sql;
    /**
     * 报表ID
     */
    private Integer id;
    /**
     * 模拟ID
     */
    private Integer simulatorId;

    /**
     * 报表的唯一标识
     */
    private String realreportId;

    /**
     * 查询的层级（0最高 最低取决于下钻深度）
     */
    private Integer level;

    /**
     * 查询数据的参数
     */
    private Map<String,Object> params;

}
