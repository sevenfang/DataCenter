package com.chezhibao.bigdata.datax.dao.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.dao.BigdataDao;
import com.chezhibao.bigdata.dao.WareHouseDao;
import com.chezhibao.bigdata.datax.dao.DataxConfigDSDao;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/26.
 */
@Repository
public class DataxConfigDSDaoImpl implements DataxConfigDSDao {

    @Reference(check = false)
    private BigdataDao wareHouseDao;

    @Autowired
    private SqlTemplateService sqlTemplateService;

    @Override
    public List<Map<String, Object>> getDs(Map<String, Object> params) {
        String sql = sqlTemplateService.getSql("datax.DataxConfigDS.getDs", params);
        return wareHouseDao.select(sql,params);
    }

    @Override
    public Boolean saveDs(Map<String, Object> params) {
        String sql = sqlTemplateService.getSql("datax.DataxConfigDS.saveDs", params);
        wareHouseDao.insert(sql,params);
        return true;
    }

    @Override
    public Boolean updateDs(Map<String, Object> params) {
        String sql = sqlTemplateService.getSql("datax.DataxConfigDS.updateDs", params);
        wareHouseDao.update(sql,params);
        return true;
    }

    @Override
    public Boolean delDs(Map<String, Object> params) {
        String sql = sqlTemplateService.getSql("datax.DataxConfigDS.delDs", params);
        wareHouseDao.delete(sql,params);
        return true;
    }
}
