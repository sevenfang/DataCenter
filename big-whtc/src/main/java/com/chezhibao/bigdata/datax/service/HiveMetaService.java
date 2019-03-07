package com.chezhibao.bigdata.datax.service;

import com.chezhibao.bigdata.datax.vo.FormItemVo;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/6/8.
 */
public interface HiveMetaService {
    /**
     * 获取所有数据库的信息
     * @return
     */
    List<FormItemVo> getDbInfo();

    /**
     * 查询数下的表
     * @param dbId
     * @return
     */
    List<FormItemVo> getTables(Integer dbId);

    /**
     * 查询表下的字段
     * @param tableId
     * @return
     */
    List<FormItemVo> getColes(Integer tableId);

    /**
     * 查询所有的数据库与表
     * @return
     */
    List<FormItemVo> getDBSchema();

    /**
     * 获取MySQL数据库的查询语句
     * @param tblId
     * @return
     */
    String getMysqlQuerySql(Integer tblId);
}
