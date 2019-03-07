package com.chezhibao.bigdata.msg.bo;

import java.io.Serializable;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/5/4
 * Created by WangCongJun on 2018/5/4.
 */
public class PushMessage implements Serializable {
    private Integer buyerId;
    private String msg;
    private String mobile;
    private String token;
    private String client = "2";
    private String title = "好车推荐";
    private String typeGroup;
    private Map<String,String> attachments;

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeGroup() {
        return typeGroup;
    }

    public void setTypeGroup(String typeGroup) {
        this.typeGroup = typeGroup;
    }

    public Map<String, String> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "PushMessage{" +
                "buyerId=" + buyerId +
                ", msg='" + msg + '\'' +
                ", mobile='" + mobile + '\'' +
                ", token='" + token + '\'' +
                ", client='" + client + '\'' +
                ", title='" + title + '\'' +
                ", typeGroup='" + typeGroup + '\'' +
                ", attachments=" + attachments +
                '}';
    }
}
