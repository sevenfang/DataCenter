package com.chezhibao.bigdata.dbms.server.dao.impl;

import com.chezhibao.bigdata.dbms.server.dao.CommonDao;
import com.chezhibao.bigdata.dbms.server.dao.DefaultDao;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/23.
 */
@Repository
public class DefaultDaoImpl implements DefaultDao {

    private CommonDao commonDao;

    private static final String DEFAULT_DS="default";

    public DefaultDaoImpl(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

    @Override
    public Boolean insert(String sql, Map<String, Object> params) {
        return commonDao.insert(DEFAULT_DS,sql,params);
    }

    @Override
    public void delete(String sql, Map<String, Object> params) {
        commonDao.delete(DEFAULT_DS,sql,params);
    }

    @Override
    public void update(String sql, Map<String, Object> params) {
        commonDao.select(DEFAULT_DS,sql,params);
    }

    @Override
    public List<LinkedHashMap<String, Object>> select(String sql, Map<String, Object> params) {
        return commonDao.select(DEFAULT_DS,sql,params);
    }
}
