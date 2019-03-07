package com.chezhibao.bigdata.mybatis;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.ParamsUtils;
import com.chezhibao.bigdata.annotation.DS;
import com.chezhibao.bigdata.dao.AucsCommonDao;
import com.chezhibao.bigdata.dao.BaseDao;
import com.chezhibao.bigdata.exception.DbmsException;
import org.springframework.stereotype.Repository;
import  static com.chezhibao.bigdata.dbms.common.LoggerUtil.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/30.
 */
@Service(interfaceClass = AucsCommonDao.class)
@Repository
public class AucsCommonDaoImpl implements AucsCommonDao {

    private static final String ds="aucs";


    private BaseDao iBaseDao;

    public AucsCommonDaoImpl(BaseDao iBaseDao) {
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
    @SentinelResource(value = "com.chezhibao.bigdata.mybatis.AucsCommonDaoImpl.select", blockHandler = "exceptionHandler",fallback = "helloFallback")
    public List<Map<String, Object>> select( String sql, Map<String, Object> params) {
        params = ParamsUtils.handlerDate(params);
        try{
            return iBaseDao.select(sql,params);
        }catch (Exception e){
            throw new DbmsException(2001,"【dbms】获取据出错了;sql:"+sql,e);
        }
    }

    /**Fallback 函数，函数签名与原函数一致.
     *
     * @param sql
     * @param params
     * @return
     */
    public List<Map<String, Object>> helloFallback( String sql, Map<String, Object> params) {
        RDBMS_LOG.error("【dbms】AucsCommonDaoImpl.select熔断处理......sql:{},params:{}",sql,params);
        return new ArrayList();
    }
    public List<Map<String, Object>> exceptionHandler(String sql, Map<String, Object> params, BlockException ex) {
        RDBMS_LOG.error("【dbms】AucsCommonDaoImpl.select熔断处理......sql:{},params:{}",sql,params);
        ex.printStackTrace();
        return new ArrayList<>();
    }
    @Override
    @DS(ds)
    @SentinelResource(value = "com.chezhibao.bigdata.mybatis.AucsCommonDaoImpl.select", blockHandler = "exceptionHandler",fallback = "helloFallback")
    public List<LinkedHashMap<String, Object>> query(String sql, Map<String, Object> params) {
        params = ParamsUtils.handlerDate(params);
        try{
            return iBaseDao.query(sql,params);
        }catch (Exception e){
            throw new DbmsException(2001,"【dbms】查询数据出错了;sql:"+sql,e);
        }
    }
}
