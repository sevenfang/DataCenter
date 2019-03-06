package com.chezhibao.bigdata.gateway.pojo;

import java.util.List;
import java.util.Map;

public class DubboApi {
    /**
     * 应用ID
     */
    private Integer appId;
    /**
     * 注册中心ID
     */
    private Integer regId;
    /**
     * 应用名称
     */
    private String appName;

    /**
     * dubbo 请求参数
     */
    private List<DubboParam> dubboParams;
    /**
     * The constant CLASS_PARAMS.
     */
    private Map<String, Object> paramValues;

    /**
     * The constant INTERFACE_NAME.
     */
    private String interfaceName;

    /**
     * The constant METHOD.
     */
    private String method;

    /**
     * The constant GROUP.
     */
    private String group;

    /**
     * The constant LOADBALANCE.
     */
    private String loadbalance;

    /**
     * The constant protocol.
     */
    private String protocol;

}