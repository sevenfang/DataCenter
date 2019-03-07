package com.chezhibao.bigdata.dbms.server.service;

import com.chezhibao.bigdata.common.pojo.ItemVo;

import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/24.
 */
public interface DbSchemaService {
    /**
     * 获取指定实例的数据库结构
     * @param name
     * @return
     */
    List<ItemVo> getDbSchema(String name,Map<String, Object> params);
    /**
     * 获取指定hive数据库结构 需要提前配置好hive元数据库
     * @param name
     * @return
     */
    Collection<ItemVo> getHiveDbSchema(String name,Map<String, Object> params);
    /**
     * 获取指定实例的数据库所有表
     * @param name
     * @return
     */
    Map<String,ItemVo> getTableSchema(String name,Map<String, Object> params);
    /**
     * 获取指定实例的数据库所有视图
     * @param name
     * @return
     */
    Map<String,ItemVo> getViewSchema(String name,Map<String, Object> params);
    /**
     * 获取指定实例的数据库所有函数
     * @param name
     * @return
     */
    Map<String,ItemVo> getFunctionSchema(String name);

    /**
     * 获取Mysql数据库中表结构和字段信息
     * @param name
     * @param type
     * @param params
     * @return
     */
    Map<String,Set<String>> getDatabaseSchemaHint(String name, Integer type,Map<String, Object> params);

    /**
     * 获取指定的mysql表字段信息
     * @param name
     * @param type
     * @param params
     * @return
     */
    List<LinkedHashMap<String,Object>> getDatabaseColumnInfo(String name, Integer type,Map<String, Object> params);
    /**
     * 获取Hive数据库中表结构和字段信息
     * @param name
     * @param params
     * @return
     */
    Map<String,Set<String>> getHiveDbColumnsSchema(String name,Map<String, Object> params);

    /**
     * 获取指定的hive表字段信息
     * @param name
     * @param params
     * @return
     */
    List<LinkedHashMap<String,Object>> getHiveDbColumnsInfo(String name, Map<String, Object> params);
}
