package com.chezhibao.bigdata.search.recommend.bo;

import java.io.Serializable;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/25.
 */
public class RecommendBidCarIfo implements Serializable {
    /**
     * /**
     * 竞拍车编号
     */
    private String auctionCarId;
    /**
     * /**
     * 竞拍类型
     */
    private Integer auctionType;
    /**
     * 推荐商户
     */
    private List<BuyerInfo> buyerList;
    /**
     * 品牌ID
     */
    private Integer carBandId;
    /**
     * 品牌
     */
    private String carBand;
    /**
     * 车编
     */
    private String carId;
    /**
     * 车系
     */
    private String carModel;
    /**
     * 车系ID
     */
    private Integer carModelId;
    /**
     * 车型
     */
    private String carType;
    /**
     * 车型ID
     */
    private Integer carTypeId;
    /**
     * 	搓车地址
     */
    private String cuocheAddress;
    /**
     * 	搓车上门时间
     */
    private String cuocheTime;
    /**
     * 	客户手机号
     */
    private String customerMobile;
    /**
     * 检测Id
     */
    private String detectionId;
    /**
     * 首次上牌日期
     */
    private String dj;
    /**
     * 心理价
     */
    private String expectPrice;
    /**
     * 公里数
     */
    private String mileage;
    /**
     * 车辆归属地
     */
    private String region;
    /**
     * 车辆归属地
     */
    private Integer regionId;
    /**
     * 静态检测报告
     */
    private String staticHtml;
    /**
     * 默认图
     */
    private String staticJpg;

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getCarBandId() {
        return carBandId;
    }

    public void setCarBandId(Integer carBandId) {
        this.carBandId = carBandId;
    }

    public Integer getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(Integer carModelId) {
        this.carModelId = carModelId;
    }

    public Integer getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(Integer carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getCuocheAddress() {
        return cuocheAddress;
    }

    public void setCuocheAddress(String cuocheAddress) {
        this.cuocheAddress = cuocheAddress;
    }

    public String getCuocheTime() {
        return cuocheTime;
    }

    public void setCuocheTime(String cuocheTime) {
        this.cuocheTime = cuocheTime;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getExpectPrice() {
        return expectPrice;
    }

    public void setExpectPrice(String expectPrice) {
        this.expectPrice = expectPrice;
    }

    public List<BuyerInfo> getBuyerList() {
        return buyerList;
    }

    public void setBuyerList(List<BuyerInfo> buyerList) {
        this.buyerList = buyerList;
    }

    public String getCarBand() {
        return carBand;
    }

    public void setCarBand(String carBand) {
        this.carBand = carBand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getDetectionId() {
        return detectionId;
    }

    public void setDetectionId(String detectionId) {
        this.detectionId = detectionId;
    }

    public String getStaticHtml() {
        return staticHtml;
    }

    public void setStaticHtml(String staticHtml) {
        this.staticHtml = staticHtml;
    }

    public String getStaticJpg() {
        return staticJpg;
    }

    public void setStaticJpg(String staticJpg) {
        this.staticJpg = staticJpg;
    }

    public Integer getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(Integer auctionType) {
        this.auctionType = auctionType;
    }

    public String getAuctionCarId() {
        return auctionCarId;
    }

    public void setAuctionCarId(String auctionCarId) {
        this.auctionCarId = auctionCarId;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    @Override
    public String toString() {
        return "RecommendBidCarIfo{" +
                "buyerList=" + buyerList +
                ", carId='" + carId + '\'' +
                ", auctionType=" + auctionType +
                ", auctionCarId='" + auctionCarId + '\'' +
                ", detectionId='" + detectionId + '\'' +
                ", carBand='" + carBand + '\'' +
                ", carModel='" + carModel + '\'' +
                ", carType='" + carType + '\'' +
                ", dj='" + dj + '\'' +
                ", staticHtml='" + staticHtml + '\'' +
                ", staticJpg='" + staticJpg + '\'' +
                ", mileage='" + mileage + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
