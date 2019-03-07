package com.chezhibao.bigdata.zipkin.dubbo.dubbo.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/16.
 */
public class ZipkinProperties {
    @Value ("${spring.zipkin.host:http://127.0.0.1:9411/}")
    private String zipkinHost;
    @Value ("${project.name:default}")
    private String serverName;
    @Value ("${spring.zipkin.rate:1.0}")
    private String rate;

    public String getZipkinHost() {
        return zipkinHost;
    }

    public void setZipkinHost(String zipkinHost) {
        this.zipkinHost = zipkinHost;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
