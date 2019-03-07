package com.chezhibao.bigdata.datax.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/26.
 */
@Data
public class DataxConfigDSVO {
    /**
     * 数据源ID
     */
    private Integer id;

    /**
     * 数据源类型
     */
    private Integer type;
    /**
     * 数据源名称
     */
    private String name;
    /**
     * 数据源链接地址
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

}
