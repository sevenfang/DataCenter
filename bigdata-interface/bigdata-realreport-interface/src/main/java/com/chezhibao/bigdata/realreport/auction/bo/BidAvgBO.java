package com.chezhibao.bigdata.realreport.auction.bo;

import java.io.Serializable;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/6/26.
 */
public class BidAvgBO implements Serializable {

    /**
     * 出价总数
     */
    private Integer BidSum;
    /**
     * 出价商户数
     */
    private Integer BidBuyerSum;

    /**
     * 商户平均出价数
     */
    private Float avg;
    /**
     * 商户参拍率
     */
    private Float participationRate;

    public Integer getBidSum() {
        return BidSum;
    }

    public void setBidSum(Integer bidSum) {
        BidSum = bidSum;
    }

    public Integer getBidBuyerSum() {
        return BidBuyerSum;
    }

    public void setBidBuyerSum(Integer bidBuyerSum) {
        BidBuyerSum = bidBuyerSum;
    }

    public Float getAvg() {
        return avg;
    }

    public void setAvg(Float avg) {
        this.avg = avg;
    }

    public Float getParticipationRate() {
        return participationRate;
    }

    public void setParticipationRate(Float participationRate) {
        this.participationRate = participationRate;
    }

    @Override
    public String toString() {
        return "BidAvgBO{" +
                "BidSum=" + BidSum +
                ", BidBuyerSum=" + BidBuyerSum +
                '}';
    }
}
