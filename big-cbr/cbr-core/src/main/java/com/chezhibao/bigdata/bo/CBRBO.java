package com.chezhibao.bigdata.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/11.
 */
@Data
public class CBRBO {
    /**
     * 报表唯一标识
     */
    private Integer id;
    /**
     * 报表层级数
     */
    private Integer level;
    /**
     * 报表ID（name）
     */
    private String realreportId;
    /**
     * 报表name
     */
    private String realreportName;
    /**
     * 报表的描述
     */
    private String desc;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedTime;
}
