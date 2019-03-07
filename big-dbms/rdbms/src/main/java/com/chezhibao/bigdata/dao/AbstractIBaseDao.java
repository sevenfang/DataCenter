package com.chezhibao.bigdata.dao;

import com.chezhibao.bigdata.datasource.DataSourceContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/4/25
 * Created by WangCongJun on 2018/4/25.
 */

public abstract class AbstractIBaseDao implements IBaseDao{

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
    public List<Map<String,Object>> select(String dataSource,String sql, Map<String,Object> params) {
        DataSourceContextHolder.setDB(dataSource);
        List<Map<String, Object>> select = baseDao.select(sql, params);
        DataSourceContextHolder.clearDB();
        return select;
    }
}
