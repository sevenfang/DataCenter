package com.chezhibao.bigdata.realreport.router;

import java.io.Serializable;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/13.
 */
public class Router implements Serializable {
    /**
     * 代理真实地址
     */
    private String targetUrl;
    /**
     * 是否需要校验
     */
    private Boolean isNeedAuth;
    /**
     * 代理地址的前缀
     */
    private String proxyPass;
    /**
     * 真实地址
     */
    private String location;
    /**
     * location是否是正则表达式
     */
    private Boolean isReg;

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public Boolean getIsNeedAuth() {
        return isNeedAuth;
    }

    public void setIsNeedAuth(Boolean needAuth) {
        isNeedAuth = needAuth;
    }

    public String getProxyPass() {
        return proxyPass;
    }

    public void setProxyPass(String proxyPass) {
        this.proxyPass = proxyPass;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getIsReg() {
        return isReg;
    }

    public void setIsReg(Boolean reg) {
        isReg = reg;
    }

    @Override
    public String toString() {
        return "Router{" +
                "targetUrl='" + targetUrl + '\'' +
                ", isNeedAuth=" + isNeedAuth +
                ", proxyPass='" + proxyPass + '\'' +
                ", location='" + location + '\'' +
                ", isReg=" + isReg +
                '}';
    }
}
