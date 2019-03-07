package com.chezhibao.bigdata.dbms.server.vo;

import lombok.Data;

/**
 * 数据库实例的前台展现对象
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/13.
 */
@Data
public class DBInstanceSchemaVO {
    /**
     * 数据库链接的Url
     */
    private String url;
    /**
     * 数据库名称
     */
    private String name;

    /**
     * 一般就是数据库实例名称
     */
    private Integer id;
    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单类型
     */
    private Integer dbType;
}
