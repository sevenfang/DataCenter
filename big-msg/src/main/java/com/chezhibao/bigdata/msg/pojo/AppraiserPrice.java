package com.chezhibao.bigdata.msg.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/10.
 */
@Data
@ToString
public class AppraiserPrice {
    /**
     * 估价师ID
     */
    private Integer appraiserId;
    /**
     * 检测工程师ID
     */
    private Integer ocseId;
    /**
     * 竞拍车辆ID
     */
    private Integer carId;
    /**
     * 估价师出价
     */
    private Double price;
    /**
     * 估价状态 0：没有发送给ocse 1 已发送给ocse
     */
    private Integer status;
    /**
     * 出价时间
     */
    private Date bidTime;

    /**
     * 估价单生成时间
     */
    private Date createdTime;
    /**
     * 估价页面url
     */
    private String accessUrl;

    /**
     * 估价页面url
     */
    private String detectId;

    /**
     * 估价师建议
     */
    private String comment;
}
