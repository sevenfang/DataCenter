package com.chezhibao.bigdata.search.es.pojo;

public class RecommedValue implements Comparable<RecommedValue>{

    private String buyerId;

    private String auctionCarId;

    private String recommendScore;

    private Double elaPrice;

    private String region;

    public RecommedValue(){
        super();
    }

    public RecommedValue(String buyerId, String auctionCarId, String recommendScore, Double elaPrice, String region) {
        super();
        this.buyerId = buyerId;
        this.auctionCarId = auctionCarId;
        this.recommendScore = recommendScore;
        this.elaPrice = elaPrice;
        this.region = region;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getAuctionCarId() {
        return auctionCarId;
    }

    public void setAuctionCarId(String auctionCarId) {
        this.auctionCarId = auctionCarId;
    }

    public String getRecommendScore() {
        return recommendScore;
    }

    public void setRecommendScore(String recommendScore) {
        this.recommendScore = recommendScore;
    }

    public Double getElaPrice() {
        return elaPrice;
    }

    public void setElaPrice(Double elaPrice) {
        this.elaPrice = elaPrice;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "RecommedValue{" +
                "buyerId='" + buyerId + '\'' +
                ", auctionCarId='" + auctionCarId + '\'' +
                ", recommendScore='" + recommendScore + '\'' +
                ", elaPrice='" + elaPrice + '\'' +
                ", region='" + region + '\'' +
                '}';
    }

    @Override
    public int compareTo(RecommedValue o) {
        return  this.elaPrice.compareTo(o.elaPrice);
    }
}
