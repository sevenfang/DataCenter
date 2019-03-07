package com.chezhibao.bigdata.datax.vo;

import lombok.Data;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/26.
 */
@Data
public class DataXSearchVO {
    /**
     * 页码
     */
    private Integer page;
    /**
     * 每页数据量
     */
    private Integer size;
    /**
     * 查询字段
     */
    private String column;
    /**
     * 查询的值
     */
    private String value;
}
