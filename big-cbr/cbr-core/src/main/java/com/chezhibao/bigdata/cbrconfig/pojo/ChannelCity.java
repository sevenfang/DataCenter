package com.chezhibao.bigdata.cbrconfig.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/18.
 */
@Data
public class ChannelCity {
    //主键ID
    private Integer id;
    //大区ID
    private Integer largeAreaId;
    //大区名称
    private String largeAreaName;
    //城市ID
    private Integer channelCityId;
    //城市名称
    private String channelCityName;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
}
