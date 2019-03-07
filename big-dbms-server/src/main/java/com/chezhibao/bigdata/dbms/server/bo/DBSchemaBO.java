package com.chezhibao.bigdata.dbms.server.bo;

import com.chezhibao.bigdata.common.pojo.ItemVo;
import lombok.Data;

import java.util.List;

/**
 * 数据库实例的前台展现对象
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/13.
 */
@Data
public class DBSchemaBO {
    /**
     * 数据库名称
     */
    private String label;
    /**
     * 数据库名称
     */
    private String value;

    /**
     * 所有表
     */
    private List<ItemVo>  tables;
    /**
     * 图标
     */
    private String icon;
}
