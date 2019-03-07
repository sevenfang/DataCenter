package com.chezhibao.bigdata.msg.dao.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.dao.NewWareHouseDao;
import com.chezhibao.bigdata.msg.dao.AppraiserPriceDao;
import com.chezhibao.bigdata.msg.pojo.AppraiserPrice;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/10.
 */
@Repository
public class AppraiserPriceDaoImpl implements AppraiserPriceDao {

    @Reference(check = false,timeout = 3000)
    private NewWareHouseDao newWareHouseDao;

    @Autowired
    private SqlTemplateService sqlTemplateService;

    @Override
    public Boolean save(AppraiserPrice appraiserPrice) {
        Map<String, Object> params = ObjectCommonUtils.object2Map(appraiserPrice);
        String sql = sqlTemplateService.getSql("evaluate.appraiser.save", params);
        return newWareHouseDao.insert(sql, params);
    }

    @Override
    public List<Map<String,Object>> get(AppraiserPrice appraiserPrice) {
        Map<String, Object> params = ObjectCommonUtils.object2Map(appraiserPrice);
        String sql = sqlTemplateService.getSql("evaluate.appraiser.get", params);
        return newWareHouseDao.select( sql, params);
    }

    @Override
    public void updateAppraiserPrice(AppraiserPrice appraiserPrice) {
        Map<String, Object> params = ObjectCommonUtils.object2Map(appraiserPrice);
        String sql = sqlTemplateService.getSql("evaluate.appraiser.updateAppraiserPrice", params);
        newWareHouseDao.update( sql, params);
    }

    @Override
    public void updateAppraiserStatus(AppraiserPrice appraiserPrice) {
        Map<String, Object> params = ObjectCommonUtils.object2Map(appraiserPrice);
        String sql = sqlTemplateService.getSql("evaluate.appraiser.updateAppraiserStatus", params);
        newWareHouseDao.update( sql, params);
    }
}
