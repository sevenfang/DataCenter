package com.chezhibao.bigdata.msg.vo;

import lombok.Data;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/10.
 */
@Data
public class AppraiserVO {
    private Integer carId;
    private Integer ocseId;
    private Integer appraiserId;
    private Double price;
    private String accessUrl;
    private String detectId;
    private String comment;
}
