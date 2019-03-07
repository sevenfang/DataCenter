package com.chezhibao.bigdata.dbms.server.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.chezhibao.bigdata.common.pojo.ItemVo;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.dbms.server.bo.DBInstanceSchemaBO;
import com.chezhibao.bigdata.dbms.server.dao.DefaultDao;
import com.chezhibao.bigdata.dbms.server.dto.DataSourceDTO;
import com.chezhibao.bigdata.dbms.server.service.DataBaseService;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/23.
 */
@Service
@Slf4j
public class DataBaseServiceImpl implements DataBaseService {

    private DefaultDao defaultDao;

    private SqlTemplateService sqlTemplateService;

    public DataBaseServiceImpl(DefaultDao defaultDao, SqlTemplateService sqlTemplateService) {
        this.defaultDao = defaultDao;
        this.sqlTemplateService = sqlTemplateService;
    }

    @Override
    public List<LinkedHashMap<String,Object>> getDBInstances(Map<String, Object> params) {
        String sql = sqlTemplateService.getSql("DBInstance.Instance.getDBInstances", params);
        List<LinkedHashMap<String, Object>> select = defaultDao.select(sql, params);
        return select;
    }

    @Override
    public Map<Object,Object> getAllCustomerDataSource() {
        Map<Object,Object> dataSourceMap = new HashMap<>();
        List<LinkedHashMap<String, Object>> dbInstances = getDBInstances(new HashMap<>());
        for(LinkedHashMap<String, Object> m : dbInstances){
            DBInstanceSchemaBO dbInstanceSchemaBO = ObjectCommonUtils.map2Object(m, DBInstanceSchemaBO.class);
            DataSource dataSource = DataSourceDTO.transDBInstanceSchemaBOToDS(dbInstanceSchemaBO);
            String name = dbInstanceSchemaBO.getName();
            log.debug("【数据库管理系统】初始化自定义数据库实例：{}",name);
            dataSourceMap.put(name,dataSource);
        }
        return dataSourceMap;
    }

    @Override
    public List<LinkedHashMap<String, Object>> getDBInstancesAndName() {
        Map<String, Object> params = new HashMap<>();
        String sql = sqlTemplateService.getSql("DBInstance.Instance.getDBInstancesAndName", params);
        List<LinkedHashMap<String, Object>> select = defaultDao.select(sql, params);
        //去除密码
        for(LinkedHashMap<String, Object> s : select){
            s.remove("password");
        }
        return select;
    }

    @Override
    public List<DBInstanceSchemaBO> getDBInstancesMenu() {
        return null;
    }

    @Override
    public DBInstanceSchemaBO getDBInstanceById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id",id);
        String sql = sqlTemplateService.getSql("DBInstance.Instance.getDBInstanceById", params);
        List<LinkedHashMap<String, Object>> select = defaultDao.select(sql, params);

        if(select.size()!=1){
            log.error("【数据库管理系统】查询id为{}的数据库实例出错了！");
            return null;
        }
        Map<String, Object> map = select.get(0);
        return ObjectCommonUtils.map2Object(map, DBInstanceSchemaBO.class);
    }

    @Override
    public List<ItemVo> getDBSchema(DBInstanceSchemaBO dbInstanceSchemaBO) {
        return null;
    }

    @Override
    public void saveOrUpdateDBInstance(DBInstanceSchemaBO dbInstanceSchemaBO) {
        Date date = new Date();
        Integer id = dbInstanceSchemaBO.getId();
        if(ObjectUtils.isEmpty(id)){
            dbInstanceSchemaBO.setUpdatedTime(date);
            dbInstanceSchemaBO.setCreatedTime(date);
            Map<String, Object> params =ObjectCommonUtils.object2Map(dbInstanceSchemaBO);
            String sql = sqlTemplateService.getSql("DBInstance.Instance.saveDBInstance", params);
            defaultDao.insert(sql,params);
        }else{
            dbInstanceSchemaBO.setUpdatedTime(date);
            Map<String, Object> params =ObjectCommonUtils.object2Map(dbInstanceSchemaBO);
            String sql = sqlTemplateService.getSql("DBInstance.Instance.UpdateDBInstance", params);
            defaultDao.update(sql,params);
        }
    }

    @Override
    public void delDBInstance(Integer dbId) {
        Map<String, Object> params = ParamsBean.newInstance().put("id", dbId).build();
        String sql = sqlTemplateService.getSql("DBInstance.Instance.delDBInstance", params);
        defaultDao.delete(sql,params);
    }
}
