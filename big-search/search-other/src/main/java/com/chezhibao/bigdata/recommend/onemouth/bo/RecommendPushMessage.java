package com.chezhibao.bigdata.recommend.onemouth.bo;

import lombok.Data;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/23.
 */
@Data
public class RecommendPushMessage {
    private String buyerId;
    private String auctionCarId;
    private String carId;
    private String detectionId;
    private String nowClient;
    private String nowToken;
    private String defaultImg;
    private String carBrand ;
    private String carModel ;
    private String carType ;
    private String price;
}
