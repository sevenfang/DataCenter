package com.chezhibao.bigdata.gateway.pojo;


import java.util.Date;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/20.
 */
public class ApiInfo {
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

    private Date createdTime;

    private Date updatedTime;
    /**
     * 创建人
     */
    private Integer useId;
    /**
     * 具体API的详细信息（JSON）
     */
    private String detail;
    /**
     * api 状态 1. 正常 2、删除 0、无效
     */
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }

    public String getLoadbalance() {
        return loadbalance;
    }

    public void setLoadbalance(String loadbalance) {
        this.loadbalance = loadbalance;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getUseId() {
        return useId;
    }

    public void setUseId(Integer useId) {
        this.useId = useId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ApiInfo{" +
                "id=" + id +
                ", uri='" + uri + '\'' +
                ", type='" + type + '\'' +
                ", method='" + method + '\'' +
                ", timeout=" + timeout +
                ", version='" + version + '\'' +
                ", retries=" + retries +
                ", loadbalance='" + loadbalance + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", useId=" + useId +
                ", detail='" + detail + '\'' +
                ", status=" + status +
                '}';
    }
}
