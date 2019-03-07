package com.chezhibao.bigdata.system.vo;

import com.chezhibao.bigdata.system.constants.Constants;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author WangCongJun
 * @date 2018/5/22
 * Created by WangCongJun on 2018/5/22.
 */
@ToString
public class SysConfigVo implements Serializable {
    private String sysName="";
    private String groupName="";
    private String paramName;
    private String paramValue;
    private Boolean dynamic;

    public Boolean getDynamic() {
        return dynamic;
    }

    public void setDynamic(Boolean dynamic) {
        this.dynamic = dynamic;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupPath() {
        return Constants.BIGDATACONFIGPATH+"/"+sysName + "/" + groupName.replaceAll("\\.", "/");
    }

    public String getParamPath() {
        return Constants.BIGDATACONFIGPATH+ "/"+sysName.trim() + "/" + groupName.trim().replaceAll("\\.", "/")+
                "/"+paramName.trim().replaceAll("\\.", "/");
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
}
