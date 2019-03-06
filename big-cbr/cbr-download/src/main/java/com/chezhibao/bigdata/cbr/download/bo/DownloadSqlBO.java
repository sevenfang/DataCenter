package com.chezhibao.bigdata.cbr.download.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/11.
 */
@Data
public class DownloadSqlBO {
    /**
     * 唯一主键
     */
    private Integer id;
    /**
     * 报表ID
     */
    private String realreportId;
    /**
     * 查询sql
     */
    private String sql;
    /**
     * 数据源
     */
    private String datasource;
    /**
     * sql名称
     */
    private String name;

    private Date createdTime;
    private Date updatedTime;

}
