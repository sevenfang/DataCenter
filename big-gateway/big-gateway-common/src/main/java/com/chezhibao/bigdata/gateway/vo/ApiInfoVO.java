package com.chezhibao.bigdata.gateway.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/20.
 */
@Data
public class ApiInfoVO {
    /**
     * 唯一标识
     */
    private Integer id;

    /**
     * http访问地址
     */
    private String uri;
    /**
     * The constant METHOD.
     */
    private String method;

    /**
     * api描述信息
     */
    private String desc;
    /**
     * The constant VERSION.
     */
    private String version;
    /**
     * The constant TIMEOUT.
     */
    private Integer timeout;

    /**
     * The constant RETRIES.
     */
    private Integer retries;

    /**
     * The constant LOADBALANCE.
     */
    private String loadbalance;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedTime;
    /**
     * 创建人
     */
    private Integer userId;
    /**
     * 后端服务类型 http dubbo
     */
    private String type;
    /**
     * 具体API的详细信息（JSON）
     */
    private Map<String,Object> detail;
    /**
     * api 状态 1. 正常 2、删除 0、无效
     */
    private Integer status;
}
