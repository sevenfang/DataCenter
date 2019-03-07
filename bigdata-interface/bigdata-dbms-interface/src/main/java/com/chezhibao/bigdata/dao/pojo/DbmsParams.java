package com.chezhibao.bigdata.dao.pojo;

import java.io.Serializable;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/20.
 */
public class DbmsParams implements Serializable {
    /**
     * 数据源名称
     */
    private String dataSourceName;
    /**
     * 调用系统名称
     */
    private String systemName;
    /**
     * 权鉴token
     */
    private String token;

    public DbmsParams(String dataSourceName, String systemName, String token) {
        this.dataSourceName = dataSourceName;
        this.systemName = systemName;
        this.token = token;
    }

    public DbmsParams() {
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "DbmsParams{" +
                "dataSourceName='" + dataSourceName + '\'' +
                ", systemName='" + systemName + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
