package com.chezhibao.bigdata.datax.service.impl;

import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.datax.dao.DataxConfigDSDao;
import com.chezhibao.bigdata.datax.pojo.DataxConfigDS;
import com.chezhibao.bigdata.datax.service.DataxConfigDSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/26.
 */
@Service
public class DataxConfigDSServiceImpl implements DataxConfigDSService {

    @Autowired
    private DataxConfigDSDao dataxConfigDSDao;

    @Override
    public List<Map<String, Object>> getDS(DataxConfigDS dataxConfigDS) {
        Map<String, Object> params = ObjectCommonUtils.object2Map(dataxConfigDS);
        return dataxConfigDSDao.getDs(params);
    }

    @Override
    public DataxConfigDS getDS(String name) {
        DataxConfigDS dataxConfigDS = new DataxConfigDS();
        dataxConfigDS.setName(name);
        List<Map<String, Object>> ds = getDS(dataxConfigDS);
        if(ObjectUtils.isEmpty(ds)){
            return null;
        }
        Map<String, Object> map = ds.get(0);
        return ObjectCommonUtils.map2Object(map,DataxConfigDS.class);
    }

    @Override
    public DataxConfigDS getDSById(Integer id) {
        DataxConfigDS dataxConfigDS = new DataxConfigDS();
        dataxConfigDS.setId(id);
        List<Map<String, Object>> ds = getDS(dataxConfigDS);
        if(ObjectUtils.isEmpty(ds)){
            return null;
        }
        Map<String, Object> map = ds.get(0);
        return ObjectCommonUtils.map2Object(map,DataxConfigDS.class);
    }

    @Override
    public Boolean saveDS(DataxConfigDS dataxConfigDS) {
        Date date = new Date();
        dataxConfigDS.setCreatedTime(date);
        dataxConfigDS.setUpdatedTime(date);
        Map<String, Object> params = ObjectCommonUtils.object2Map(dataxConfigDS);
        DataxConfigDS ds = getDS(dataxConfigDS.getName());
        if(ds!=null){
            return false;
        }
        return dataxConfigDSDao.saveDs(params);
    }

    @Override
    public Boolean delDS(DataxConfigDS dataxConfigDS) {
        Map<String, Object> params = ObjectCommonUtils.object2Map(dataxConfigDS);
        List<Map<String, Object>> ds = dataxConfigDSDao.getDs(params);
        if(ObjectUtils.isEmpty(ds)){
            return false;
        }
        return dataxConfigDSDao.delDs(params);
    }

    @Override
    public Boolean updateDS(DataxConfigDS dataxConfigDS) {
        Map<String, Object> params = ObjectCommonUtils.object2Map(dataxConfigDS);
        DataxConfigDS ds = getDSById(dataxConfigDS.getId());
        if(ObjectUtils.isEmpty(ds)){
            return false;
        }
        return dataxConfigDSDao.updateDs(params);
    }
}
