package com.chezhibao.bigdata.cbrconfig.dao.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.cbrconfig.dao.LargeAreaDao;
import com.chezhibao.bigdata.dao.CBRCommonDao;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/18.
 */
@Repository
public class LargeAreaDaoImpl implements LargeAreaDao {

    @Reference(check = false)
    private CBRCommonDao cbrCommonDao;
    private SqlTemplateService sqlTemplateService;

    public LargeAreaDaoImpl( SqlTemplateService sqlTemplateService) {
        this.sqlTemplateService = sqlTemplateService;
    }

    @Override
    public List<LinkedHashMap<String, Object>> getAllLargeArea() {
        HashMap<String, Object> map = new HashMap<>();
        String sql = sqlTemplateService.getSql("cbrconfig.LargeArea.getAllLargeArea", map);
        return cbrCommonDao.query(sql,map);
    }

    @Override
    public void updateLargeArea(Map<String, Object> params) {
        String sql = sqlTemplateService.getSql("cbrconfig.LargeArea.updateLargeArea", params);
        cbrCommonDao.update(sql,params);
    }

    @Override
    public void delLargeArea(Integer id) {
        Map<String, Object> params = ParamsBean.newInstance().put("id", id).build();
        String sql = sqlTemplateService.getSql("cbrconfig.LargeArea.delLargeArea", params);
        cbrCommonDao.delete(sql,params);
    }

    @Override
    public void saveLargeArea(Map<String, Object> params) {
        String sql = sqlTemplateService.getSql("cbrconfig.LargeArea.saveLargeArea", params);
        cbrCommonDao.insert(sql,params);
    }
}
