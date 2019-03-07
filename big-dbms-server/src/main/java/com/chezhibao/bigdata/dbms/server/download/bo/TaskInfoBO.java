package com.chezhibao.bigdata.dbms.server.download.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/12.
 */
@Data
public class TaskInfoBO {
    /**
     * 唯一主键
     */
    private Integer id;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * SQL
     *
     */
    private String sql;

    /**
     * 数据源
     *
     */
    private String datasource;
    /**
     * 任务状态 0、创建 1、任务等待执行 2、任务执行中 3、任务完成 4、任务取消
     */
    private Integer status;
    /**
     * 任务创建者ID
     */
    private Integer userId;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    /**
     * 更改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedTime;
}
