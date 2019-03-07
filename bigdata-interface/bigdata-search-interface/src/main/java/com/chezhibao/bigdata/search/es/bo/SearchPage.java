package com.chezhibao.bigdata.search.es.bo;

import java.io.Serializable;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/15.
 */
public class SearchPage implements Serializable {
    /**
     * 查询的页码
     */
    private Integer page;
    /**
     * 每页的返回数量
     */
    private Integer size;
    /**
     * 场次标签
     */
    private String sessionName;

    public SearchPage(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    @Override
    public String toString() {
        return "SearchPage{" +
                "page=" + page +
                ", size=" + size +
                ", sessionName='" + sessionName + '\'' +
                '}';
    }
}
