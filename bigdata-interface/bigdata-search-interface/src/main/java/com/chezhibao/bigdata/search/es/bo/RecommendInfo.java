package com.chezhibao.bigdata.search.es.bo;


import java.io.Serializable;

/**
 * @author WangCongJun
 * @date 2018/5/15
 * Created by WangCongJun on 2018/5/15.
 */
public class RecommendInfo implements Serializable {
    /**
     * 商户ID
     */
    private Integer buyerId;
    /**
     * 竞拍车辆ID
     */
    private Integer auctionCarid;
    /**
     * 车辆ID
     */
    private Integer carId;
    /**
     * 推荐排序的顺序（正常不用的）
     */
    private Integer rank;
    /**
     * 推荐排序的分数
     */
    private Double score;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getAuctionCarid() {
        return auctionCarid;
    }

    public void setAuctionCarid(Integer auctionCarid) {
        this.auctionCarid = auctionCarid;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
