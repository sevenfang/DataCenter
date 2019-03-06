package com.chezhibao.bigdata.pojo;

import lombok.Data;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/15.
 */
@Data
public class CBRParam {
    /**
     * 参数唯一标识
     */
    private Integer id;
    /**
     * 参数标签
     */
    private String label;
    /**
     * 参数顺序
     */
    private Integer order;
    /**
     * 参数名称
     */
    private String paramName;
    /**
     * 参数类型（页面输入框类型）
     */
    private String type;
    /**
     * 参数值（默认值）
     */
    private String value;
}
