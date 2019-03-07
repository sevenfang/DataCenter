package com.chezhibao.bigdata.mybatis;

import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.ParamsUtils;
import com.chezhibao.bigdata.annotation.DS;
import com.chezhibao.bigdata.dao.BaseDao;
import com.chezhibao.bigdata.dao.BigdataDao;
import com.chezhibao.bigdata.exception.DbmsException;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/12.
 */
@Service(interfaceClass = BigdataDao.class)
@Repository
public class BigdataDaoImpl implements BigdataDao {

    private static final String ds="hiveMeta";


    private BaseDao iBaseDao;

    public BigdataDaoImpl(BaseDao iBaseDao) {
        this.iBaseDao = iBaseDao;
    }

    @Override
    @DS(ds)
    public Boolean insert( String sql, Map<String, Object> params) {
        params = ParamsUtils.handlerDate(params);
        try {
            iBaseDao.insert(sql, params);
        }catch (Exception e){
            throw new DbmsException(2001,"【dbms】插入数据出错了;sql:"+sql,e);
        }
        return true;
    }

    @Override
    @DS(ds)
    public void delete(String sql, Map<String, Object> params) {
        params = ParamsUtils.handlerDate(params);
        try{
            iBaseDao.delete(sql,params);
        }catch (Exception e){
            throw new DbmsException(2001,"【dbms】插入数据出错了;sql:"+sql,e);
        }
    }

    @Override
    @DS(ds)
    public void update( String sql, Map<String, Object> params) {
        params = ParamsUtils.handlerDate(params);
        try{
            iBaseDao.update(sql,params);
        }catch (Exception e){
            throw new DbmsException(2001,"【dbms】插入数据出错了;sql:"+sql,e);
        }
    }

    @Override
    @DS(ds)
    public List<Map<String, Object>> select( String sql, Map<String, Object> params) {
        params = ParamsUtils.handlerDate(params);
        try{
            return iBaseDao.select(sql,params);
        }catch (Exception e){
            throw new DbmsException(2001,"【dbms】插入数据出错了;sql:"+sql,e);
        }
    }

    @Override
    @DS(ds)
    public List<LinkedHashMap<String, Object>> query(String sql, Map<String, Object> params) {
        params = ParamsUtils.handlerDate(params);
        try{
            return iBaseDao.query(sql,params);
        }catch (Exception e){
            throw new DbmsException(2001,"【dbms】插入数据出错了;sql:"+sql,e);
        }
    }
}
