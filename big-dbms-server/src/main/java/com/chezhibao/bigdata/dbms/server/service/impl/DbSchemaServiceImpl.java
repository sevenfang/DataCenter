package com.chezhibao.bigdata.dbms.server.service.impl;

import com.chezhibao.bigdata.common.pojo.ItemVo;
import com.chezhibao.bigdata.dbms.server.dao.CommonDao;
import com.chezhibao.bigdata.dbms.server.service.DbSchemaService;
import com.chezhibao.bigdata.dbms.server.utils.DBUtils;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/24.
 */
@Service
public class DbSchemaServiceImpl implements DbSchemaService {


    private CommonDao commonDao;

    private SqlTemplateService sqlTemplateService;

    public DbSchemaServiceImpl(CommonDao commonDao, SqlTemplateService sqlTemplateService) {
        this.commonDao = commonDao;
        this.sqlTemplateService = sqlTemplateService;
    }

    @Override
    public List<ItemVo> getDbSchema(String name,Map<String, Object> params) {
        List<ItemVo> itemVos = new ArrayList<>();
        Map<String, ItemVo> tableSchema = getTableSchema(name,params);
        Map<String, ItemVo> viewSchema = getViewSchema(name,params);
        Set<String> keys = new HashSet<>();
        keys.addAll(tableSchema.keySet());
        keys.addAll(viewSchema.keySet());
        for (String key : keys) {
            ItemVo t = tableSchema.get(key);
            ItemVo v = viewSchema.get(key);
            if (t != null && v != null) {
                t.getChildren().add(v.getChildren().get(0));
            }
            if (t == null && v!=null) {
                t=v;
            }
            itemVos.add(t);
        }
        return itemVos;
    }

    @Override
    public Map<String, ItemVo> getTableSchema(String name,Map<String, Object> params) {
        String sql = sqlTemplateService.getSql("DBInstance.schema.getTableSchema", params);
        List<LinkedHashMap<String, Object>> select = commonDao.select(name, sql, params);
        return DBUtils.generDbItem(select, "表", "表");
    }

    @Override
    public Map<String, ItemVo> getViewSchema(String name,Map<String, Object> params) {
        String sql = sqlTemplateService.getSql("DBInstance.schema.getViewSchema", params);
        List<LinkedHashMap<String, Object>> select = commonDao.select(name, sql, params);
        return DBUtils.generDbItem(select, "视图", "视图");
    }

    @Override
    public Map<String, ItemVo> getFunctionSchema(String name) {
        return null;
    }

    @Override
    public Map<String, Set<String>> getDatabaseSchemaHint(String name,Integer type,Map<String, Object> params) {
        String sql = sqlTemplateService.getSql("DBInstance.schema.getDatabaseSchemaHint", params);
        List<LinkedHashMap<String, Object>> select = commonDao.select(name, sql, params);
        return DBUtils.getColumnsSchema(select);
    }

    @Override
    public Collection<ItemVo> getHiveDbSchema(String name,Map<String, Object> params) {
        //查询hive的就是hiveMeta
        name="hiveMeta";
        String sql = sqlTemplateService.getSql("DBInstance.schema.getHiveDbSchema", params);
        List<LinkedHashMap<String, Object>> select = commonDao.select(name, sql, params);
        return DBUtils.generDbItem(select, "表", "表").values();
    }

    @Override
    public Map<String, Set<String>> getHiveDbColumnsSchema(String name,Map<String, Object> params) {
        //查询hive的就是hiveMeta
        name="hiveMeta";
        String sql = sqlTemplateService.getSql("DBInstance.schema.getHiveDbColumnsSchema", params);
        List<LinkedHashMap<String, Object>> select = commonDao.select(name, sql, params);
        return DBUtils.getColumnsSchema(select);
    }

    @Override
    public List<LinkedHashMap<String,Object>> getDatabaseColumnInfo(String name, Integer type, Map<String, Object> params) {
        String sql = sqlTemplateService.getSql("DBInstance.schema.getDatabaseSchemaHint", params);
        return commonDao.select(name, sql, params);
    }

    @Override
    public List<LinkedHashMap<String,Object>> getHiveDbColumnsInfo(String name, Map<String, Object> params) {
        //查询hive的就是hiveMeta
        name="hiveMeta";
        String sql = sqlTemplateService.getSql("DBInstance.schema.getHiveDbColumnsSchema", params);
        return commonDao.select(name, sql, params);
    }
}
