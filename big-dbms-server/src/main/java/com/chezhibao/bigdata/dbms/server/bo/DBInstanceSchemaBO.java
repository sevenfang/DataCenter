package com.chezhibao.bigdata.dbms.server.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 数据库实例的前台展现对象
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/13.
 */
@Data
public class DBInstanceSchemaBO {

    /**
     * 数据库实例ID
     */
    private Integer id;

    /**
     * 数据库链接的ip
     */
    private String host;

    /**
     * 数据库链接的port
     */
    private Integer port;
    /**
     * 数据库名称
     */
    private String name;
    /**
     * 驱动类名称
     */
    private String driverClassName;

    /**
     * 图标
     */
    private String icon;

    /**
     * 数据库用户名
     */
    private String username;

    /**
     * 数据库密码
     */
    private String password;

    /**
     * 额外的字段（数据库链接配置）
     */
    private String extraFields;

    /**
     * 额外的字段（数据库链接配置）
     */
    private String extraUrl;

    /**
     * 菜单类型
     */
    private Integer dbType;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    /**
     * 更新时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
}
