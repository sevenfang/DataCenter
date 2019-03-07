package com.chezhibao.bigdata.datax.dao.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.dao.CommonDao;
import com.chezhibao.bigdata.datax.dao.HiveMetaDao;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/6/8.
 */
@Repository
public class HiveMetaDaoImpl implements HiveMetaDao {

    @Reference(check = false)
    private CommonDao commonDao;
    private SqlTemplateService sqlTemplateService;

    @Autowired
    public HiveMetaDaoImpl(SqlTemplateService sqlTemplateService) {
        this.sqlTemplateService = sqlTemplateService;
    }

    @Override
    public List<Map<String, Object>> getDbTableInfo() {
        HashMap<String, Object> params = new HashMap<>();
        String sql = sqlTemplateService.getSql("datax.hive_metadata.getDbTableInfo", params);
        return commonDao.select("hiveMeta", sql, params);
    }

    @Override
    public List<Map<String, Object>> getColsByTableId(Integer tabId) {
        Map<String, Object> params=ParamsBean.newInstance().put("tableId",tabId).build();
        String sql = sqlTemplateService.getSql("datax.hive_metadata.getCols", params);
        return commonDao.select("hiveMeta", sql, params);
    }

    @Override
    public List<Map<String, Object>> getDbs() {
        HashMap<String, Object> params = new HashMap<>();
        String sql = sqlTemplateService.getSql("datax.hive_metadata.getDbs", params);
        return commonDao.select("hiveMeta", sql, params);
    }

    @Override
    public List<Map<String, Object>> getTablesByDbId(Integer dbId) {
        Map<String, Object> params=ParamsBean.newInstance().put("dbId",dbId).build();
        String sql = sqlTemplateService.getSql("datax.hive_metadata.getTables", params);
        return commonDao.select("hiveMeta", sql, params);
    }

    @Override
    public List<Map<String, Object>> getDBSchema() {
        HashMap<String, Object> params = new HashMap<>();
        String sql = sqlTemplateService.getSql("datax.hive_metadata.getDBSchema", params);
        return commonDao.select("hiveMeta", sql, params);
    }

    @Override
    public List<Map<String, Object>> getSqlWithoutChar10AndChar13(Integer tblId) {
        Map<String, Object> params = ParamsBean.newInstance().put("tblId", tblId).build();
        String sql = sqlTemplateService.getSql("datax.hive_metadata.getSqlWithoutChar10AndChar13", params);
        return commonDao.select("hiveMeta", sql, params);
    }
}
