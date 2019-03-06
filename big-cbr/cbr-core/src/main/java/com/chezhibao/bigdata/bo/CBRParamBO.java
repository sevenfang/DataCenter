package com.chezhibao.bigdata.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/15.
 */
@Data
public class CBRParamBO {
    /**
     * 唯一标识
     */
    private Integer id;
    /**
     * 报表ID
     */
    private String realreportId;
    /**
     * 参数标签
     */
    private String label;
    /**
     * 参数顺序
     */
    private Integer order;
    /**
     * 参数名称
     */
    private String paramName;
    /**
     * 参数类型（页面输入框类型）
     */
    private String type;
    /**
     * 参数值（默认值）
     */
    private String value;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedTime;
}
