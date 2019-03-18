package com.sohu.tv.cachecloud.client.basic.util;

import java.util.Map;
import java.util.Properties;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/11.
 */
public class GetRedisClusterAddrFromCloudProperties {
    private String clientVersion;
    private String domainUrl;
    private Integer maxTotal;
    private Integer minIdle;
    private Integer maxIdle;
    private Map<String,Properties> configs;

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getDomainUrl() {
        return domainUrl;
    }

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Map<String, Properties> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, Properties> configs) {
        this.configs = configs;
    }
}
