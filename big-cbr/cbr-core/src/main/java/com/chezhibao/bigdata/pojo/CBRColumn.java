package com.chezhibao.bigdata.pojo;

import lombok.Data;

/**
 * 报表字段
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/11.
 */
@Data
public class CBRColumn {
    /**
     * 列的唯一标识
     */
    private Integer id;
    /**
     * 字段显示名称
     */
    private String title;
    /**
     * 字段标识，于表数据中的key对应
     */
    private String key;
    /**
     * 列宽
     */
    private Integer width;
    /**
     * 字段数据展现时的数据
     */
    private String func;
    /**
     * 字段数据展现的样式
     */
    private String style;
    /**
     * 字段展现的文本
     */
    private String text;
}
