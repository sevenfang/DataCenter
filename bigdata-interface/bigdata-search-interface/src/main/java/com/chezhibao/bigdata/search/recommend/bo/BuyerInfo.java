package com.chezhibao.bigdata.search.recommend.bo;

import java.io.Serializable;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/25.
 */
public class BuyerInfo implements Serializable {

    /**
     * 是否选中
     */
    private boolean check;
    /**
     * 商户负责人名称
     */
    private String buyerContacts;
    /**
     * 商户编号
     */
    private Integer buyerId;
    /**
     * 商户负责人手机号码
     */
    private String buyerMobile;
    /**
     * 车行名称
     */
    private String buyerName;
    /**
     * 是否拜访
     */
    private Boolean visited;

    public String getBuyerContacts() {
        return buyerContacts;
    }

    public void setBuyerContacts(String buyerContacts) {
        this.buyerContacts = buyerContacts;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerMobile() {
        return buyerMobile;
    }

    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "BuyerInfo{" +
                "check=" + check +
                ", buyerContacts='" + buyerContacts + '\'' +
                ", buyerId=" + buyerId +
                ", buyerMobile='" + buyerMobile + '\'' +
                ", buyerName='" + buyerName + '\'' +
                '}';
    }
}
