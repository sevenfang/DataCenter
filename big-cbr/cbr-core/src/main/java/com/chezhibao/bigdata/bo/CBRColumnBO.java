package com.chezhibao.bigdata.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 报表字段
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/11.
 */
@Data
public class CBRColumnBO {
    /**
     * 字段唯一标识
     */
    private Integer id;

    /**
     * 字段顺序
     */
    private Integer order;
    /**
     * 报表唯一标识
     */
    private String realreportId;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedTime;
}
