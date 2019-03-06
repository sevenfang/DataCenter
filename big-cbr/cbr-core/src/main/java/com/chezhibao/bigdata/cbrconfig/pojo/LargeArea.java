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
public class LargeArea {
    //主键
    private Integer id;
    //大区ID
    private Integer largeAreaId;
    //大区名称
    private String largeAreaName;
    //大区优先级
    private Integer largeAreaPriority;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
}
