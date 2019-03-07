package com.chezhibao.bigdata.dbms.server.service;

import com.chezhibao.bigdata.common.pojo.ItemVo;
import com.chezhibao.bigdata.dbms.server.bo.DBInstanceSchemaBO;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/23.
 */
public interface DataBaseService {
    /**
     * 获取数据库实例列表
     * @return
     */
     List<LinkedHashMap<String,Object>> getDBInstances(Map<String, Object> params);

    /**
     * 获取数据库实例列表
     * @return
     */
    List<LinkedHashMap<String,Object>> getDBInstancesAndName();

    /**
     * 获取数据库实例列表菜单
     * @return
     */
    List<DBInstanceSchemaBO> getDBInstancesMenu();

    /**
     * 根据Id获取数据库实例列表
     * @return
     */
    DBInstanceSchemaBO getDBInstanceById(Integer id);

    /**
     * 获取数据库结构信息
     * @param dbInstanceSchemaBO
     * @return
     */
     List<ItemVo> getDBSchema(DBInstanceSchemaBO dbInstanceSchemaBO);

    /**
     * 添加数据库实例
     */
     void saveOrUpdateDBInstance(DBInstanceSchemaBO dbInstanceSchemaBO);

    /**
     * 删除数据库实例
     */
    void delDBInstance(Integer dbId);

     Map<Object,Object> getAllCustomerDataSource();
}
