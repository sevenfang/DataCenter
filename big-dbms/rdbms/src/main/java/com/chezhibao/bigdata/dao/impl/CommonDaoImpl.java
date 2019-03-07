package com.chezhibao.bigdata.dao.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.ParamsUtils;
import com.chezhibao.bigdata.dao.CommonDao;
import com.chezhibao.bigdata.dao.IBaseDao;
import com.chezhibao.bigdata.dbms.common.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 *  * @date 2018/5/7
 * Created by WangCongJun on 2018/5/7.
 */
@Service(interfaceClass = CommonDao.class)
@Repository
public class CommonDaoImpl implements CommonDao {

    private static Logger log = LoggerUtil.RDBMS_LOG;

    @Resource(name="IBaseDaoImpl")
    private IBaseDao iBaseDao;

    @Override
    public Boolean insert(String dataSource, String sql, Map<String, Object> params) {
        params = ParamsUtils.handlerDate(params);
        try {
            iBaseDao.insert(dataSource, sql, params);
        }catch (Exception e){
            log.error("【dbms】插入数据出错了：dataSource:{};sql:{}",dataSource,sql,e);
            return false;
        }
        return true;
    }

    @Override
    public void delete(String dataSource, String sql, Map<String, Object> params) {
        params = ParamsUtils.handlerDate(params);
        iBaseDao.delete(dataSource,sql,params);
    }

    @Override
    public void update(String dataSource, String sql, Map<String, Object> params) {
        params = ParamsUtils.handlerDate(params);
        iBaseDao.update(dataSource,sql,params);
    }

    @Override
    public List<Map<String, Object>> select(String dataSource, String sql, Map<String, Object> params) {
        params = ParamsUtils.handlerDate(params);
        return iBaseDao.select(dataSource,sql,params);
    }

    @Override
    public List<LinkedHashMap<String, Object>> query(String dataSource, String sql, Map<String, Object> params) {
        params = ParamsUtils.handlerDate(params);
        return iBaseDao.query(dataSource,sql,params);
    }
}
