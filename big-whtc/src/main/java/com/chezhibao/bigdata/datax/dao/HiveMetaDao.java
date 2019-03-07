package com.chezhibao.bigdata.datax.dao;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/6/8.
 */
public interface HiveMetaDao {
    /**
     * 获取hive数据库中的数据库和表信息
     * @return
     */
    List<Map<String,Object>> getDbTableInfo();

    /**
     * 根据tableId获取表字段信息
     * @param tabId
     * @return
     */
    List<Map<String,Object>> getColsByTableId(Integer tabId);

    /**
     * 查询hive中所有的库
     * @return
     */
    List<Map<String,Object>> getDbs();

    /**
     * 根据数据库id查询所有的表
     * @param dbId
     * @return
     */
    List<Map<String,Object>> getTablesByDbId(Integer dbId);

    /**
     * 查询所有的数据库与表
     * @return
     */
    List<Map<String,Object>> getDBSchema();

    /**
     * 获取去除回车符和换行符的sql语句
     * @param tblId
     * @return
     */
    List<Map<String,Object>> getSqlWithoutChar10AndChar13(Integer tblId);
}
