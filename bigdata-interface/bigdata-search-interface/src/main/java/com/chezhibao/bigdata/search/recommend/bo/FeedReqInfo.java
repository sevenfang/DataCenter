package com.chezhibao.bigdata.search.recommend.bo;

import java.io.Serializable;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/10.
 */
public class FeedReqInfo implements Serializable {
    /**
     * (tab页名称)
     */

    private String feedTabName;
    /**
     * （ 被查看车辆所在页码数）
     */

    private Integer feedPage;
    /**
     * （被查看车辆所在页码的行数）
     */

    private Integer feedRowNum;
    /**
     * （商户id）
     */
    private Integer feedBuyerId;
    /**
     * (被查看竞拍车辆id)
     */
    private Integer feedAuctioncarid;
    /**
     * (被查看车辆新车指导价)
     */
    private Double feedNewCarPrice;

    /**
     * (当前商户浏览列表最后加载的位置)
     */
    private Integer feedLoadTotalSize;

    public String getFeedTabName() {
        return feedTabName;
    }

    public void setFeedTabName(String feedTabName) {
        this.feedTabName = feedTabName;
    }

    public Integer getFeedPage() {
        return feedPage;
    }

    public void setFeedPage(Integer feedPage) {
        this.feedPage = feedPage;
    }

    public Integer getFeedRowNum() {
        return feedRowNum;
    }

    public void setFeedRowNum(Integer feedRowNum) {
        this.feedRowNum = feedRowNum;
    }

    public Integer getFeedBuyerId() {
        return feedBuyerId;
    }

    public void setFeedBuyerId(Integer feedBuyerId) {
        this.feedBuyerId = feedBuyerId;
    }

    public Integer getFeedAuctioncarid() {
        return feedAuctioncarid;
    }

    public void setFeedAuctioncarid(Integer feedAuctioncarid) {
        this.feedAuctioncarid = feedAuctioncarid;
    }

    public Double getFeedNewCarPrice() {
        return feedNewCarPrice;
    }

    public void setFeedNewCarPrice(Double feedNewCarPrice) {
        this.feedNewCarPrice = feedNewCarPrice;
    }

    public Integer getFeedLoadTotalSize() {
        return feedLoadTotalSize;
    }

    public void setFeedLoadTotalSize(Integer feedLoadTotalSize) {
        this.feedLoadTotalSize = feedLoadTotalSize;
    }

    @Override
    public String toString() {
        return "FeedReqInfo{" +
                "feedTabName='" + feedTabName + '\'' +
                ", feedPage=" + feedPage +
                ", feedRowNum=" + feedRowNum +
                ", feedBuyerId=" + feedBuyerId +
                ", feedAuctioncarid=" + feedAuctioncarid +
                ", feedNewCarPrice=" + feedNewCarPrice +
                ", feedLoadTotalSize=" + feedLoadTotalSize +
                '}';
    }
}
