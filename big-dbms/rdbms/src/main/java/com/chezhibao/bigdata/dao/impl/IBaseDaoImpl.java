package com.chezhibao.bigdata.dao.impl;

import com.chezhibao.bigdata.dao.BaseDao;
import com.chezhibao.bigdata.dao.IBaseDao;
import com.chezhibao.bigdata.datasource.DataSourceContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/5/7
 * Created by WangCongJun on 2018/5/7.
 */
@Repository
public class IBaseDaoImpl implements IBaseDao {

    @Autowired
    private BaseDao baseDao;


    @Override
    public void insert(String dataSource,String sql, Map<String,Object> params) {
        DataSourceContextHolder.setDB(dataSource);
        baseDao.insert(sql,params);
        DataSourceContextHolder.clearDB();
    }

    @Override
    public void delete(String dataSource,String sql, Map<String,Object> params) {
        DataSourceContextHolder.setDB(dataSource);
        baseDao.delete(sql,params);
        DataSourceContextHolder.clearDB();
    }

    @Override
    public void update(String dataSource,String sql, Map<String,Object> params) {
        DataSourceContextHolder.setDB(dataSource);
        baseDao.update(sql,params);
        DataSourceContextHolder.clearDB();
    }

    @Override
    public List<Map<String,Object>> select(String dataSource, String sql, Map<String,Object> params) {
        DataSourceContextHolder.setDB(dataSource);
        List<Map<String, Object>> select = baseDao.select(sql, params);
        DataSourceContextHolder.clearDB();
        return select;
    }


    @Override
    public List<LinkedHashMap<String,Object>> query(String dataSource, String sql, Map<String,Object> params) {
        DataSourceContextHolder.setDB(dataSource);
        List<LinkedHashMap<String, Object>> select = baseDao.query(sql, params);
        DataSourceContextHolder.clearDB();
        return select;
    }
}
