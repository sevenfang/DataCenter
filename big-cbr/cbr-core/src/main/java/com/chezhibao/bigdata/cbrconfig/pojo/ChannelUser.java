package com.chezhibao.bigdata.cbrconfig.pojo;

import lombok.Data;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/18.
 */
@Data
public class ChannelUser {
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 渠道人员名称
     */
    private String userName;
    /**
     * 渠道人员ID
     */
    private Integer userId;
    /**
     * 大区ID
     */
    private Integer largeAreaId;
    /**
     * 大区名称
     */
    private String largeAreaName;
    /**
     * 城市Id
     */
    private Integer channelCityId;
    /**
     * 大区优先级
     */
    private Integer largeAreaPriority;
    /**
     * 城市名称
     */
    private String channelCityName;
    /**
     * 公司ID
     */
    private Integer companyCityId;
    /**
     * 公司名称
     */
    private String companyCityName;
}
