package com.chezhibao.bigdata.cbr.download.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/11.
 */
@Data
public class DownloadColumnVO {
    /**
     * 唯一主键
     */
    private Integer id;
    /**
     * 报表ID
     */
    private String realreportId;
    /**
     * 下载显示名称
     */
    private String title;
    /**
     * 数据库对应字段
     */
    private String key;
    private Date createdTime;
    private Date updatedTime;
}
