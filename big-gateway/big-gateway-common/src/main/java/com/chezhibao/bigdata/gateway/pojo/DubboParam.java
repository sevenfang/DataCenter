package com.chezhibao.bigdata.gateway.pojo;

public class DubboParam {
    /**
     * 参数的类型名称
     */
    private String className;
    /**
     * 参数名
     */
    private String name;
    /**
     * 是否必须
     */
    private Boolean required = false;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}