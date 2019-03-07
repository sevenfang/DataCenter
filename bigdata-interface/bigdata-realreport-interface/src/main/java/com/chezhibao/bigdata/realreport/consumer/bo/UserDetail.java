package com.chezhibao.bigdata.realreport.consumer.bo;

import java.io.Serializable;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/5/31.
 */
public class UserDetail implements Serializable {
    /**
     * 用户等级
     */
    private Integer level;

    /**
     * 用户下级组织ID
     */
    private List<Integer> orgids;


    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Integer> getOrgids() {
        return orgids;
    }

    public void setOrgids(List<Integer> orgids) {
        this.orgids = orgids;
    }
}
