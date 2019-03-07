package com.chezhibao.bigdata.msg.bo;

import lombok.Data;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/10.
 */
@Data
public class CarValuationBO {
    /**
     * 上牌日期
     */
    private String dj;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 车系名称
     */
    private String seriesName;
    /**
     * 车型名称
     */
    private String modelName;
    /**
     * 公里数
     */
    private String mileage;
    /**
     * 竞拍城市
     */
    private String auctionregion;
    /**
     * 车辆归属地
     */
    private String carregion;
    /**
     * 客户所在地区
     */
    private String customerregion;
    /**
     * 车辆用途
     */
    private String usetype;
    /**
     * 环保标准
     */
    private String env;
    /**
     * 颜色
     */
    private String color;
    /**
     * 等级
     */
    private String scorerating;
    /**
     * 是否事故车
     */
    private String isAccident;
    /**
     * 工程师id
     */
    private String ocseId;

    /**
     * 工程师名称
     */
    private String ocseName;
    /**
     * 车编号
     */
    private String carId;
    /**
     * 事故项
     */
    private String result;

    /**
     * 估价师ID
     */
    private String appraiserId;
    /**
     * 估价师名称
     */
    private String appraiserName;
    /**
     * 估价页面url
     */
    private String accessUrl;

    /**
     * 交强险截止时间
     */
    private String compulsoryOverTime;

    /**
     * 年检时间
     */
    private String inspectionOverTime;
    /**
     * 商业险截至时间
     */
    private String commercialOverTime;

    /**
     * 贷款
     */
    private String hasLoan;

    /**
     * 贷款 0 表示C端车  1 B端车
     */
    private String is4S;

    /**
     * 检测单ID
     */
    private String detectId;

    /**
     * 技术鉴定信息备注
     */
    private String techniqueBak;
    /**
     * 检测工程师备注信息
     */
    private String importantBak;

    /**
     * 保买价格
     */
    private String baoMaiPrcie;

}
