package com.chezhibao.bigdata.system.vo;

import java.io.Serializable;

/**
 * @author WangCongJun
 * @date 2018/5/21
 * Created by WangCongJun on 2018/5/21.
 */

public class MenuGroupVo implements Serializable {
    private String sysName;
    private String groupName;
    private String path;

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

    public String getPath() {
        return "/"+sysName + "/" + groupName.replaceAll("\\.", "/");
    }

    public void setPath(String path) {
        this.path = path;
    }

}
