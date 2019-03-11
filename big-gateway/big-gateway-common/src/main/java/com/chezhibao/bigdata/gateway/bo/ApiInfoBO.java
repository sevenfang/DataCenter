package com.chezhibao.bigdata.gateway.bo;

import lombok.Data;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/9.
 */
@Data
public class ApiInfoBO {
    public ApiInfoBO() {
    }

    public ApiInfoBO(String uri, String method, String version) {
        this.uri = uri;
        this.method = method;
        this.version = version;
    }

    /**
     * 唯一标识
     */
    private Integer id;

    /**
     * http访问地址
     */
    private String uri;
    /**
     * 后端服务类型 http dubbo
     */
    private String type;
    /**
     * The constant METHOD.
     */
    private String method;

    /**
     * The constant TIMEOUT.
     */
    private Integer timeout;

    /**
     * The constant VERSION.
     */
    private String version;

    /**
     * The constant RETRIES.
     */
    private Integer retries;

    /**
     * The constant LOADBALANCE.
     */
    private String loadbalance;
    /**
     * 创建人
     */
    private Integer userId;
    /**
     * 具体API的详细信息（JSON）
     */
    private String detail;
    /**
     * api 状态 1. 正常 2、删除 0、无效
     */
    private Integer status;
}
