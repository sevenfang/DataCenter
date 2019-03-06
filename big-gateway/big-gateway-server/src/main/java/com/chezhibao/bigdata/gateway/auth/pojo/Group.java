package com.chezhibao.bigdata.gateway.auth.pojo;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/5.
 */
public class Group {
    private String name;
    private String GroupName;

    public Group(String name, String groupName) {
        this.name = name;
        GroupName = groupName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }
}
