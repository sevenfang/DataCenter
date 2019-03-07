package com.chezhibao.bigdata.msg.dao.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.dao.CommonDao;
import com.chezhibao.bigdata.dao.NewWareHouseDao;
import com.chezhibao.bigdata.msg.dao.AppraiserDao;
import com.chezhibao.bigdata.msg.pojo.Appraiser;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/11.
 */
@Repository("appraiserDaoImpl")
public class AppraiserDaoImpl implements AppraiserDao {
    @Reference(check = false,timeout = 3000)
    private NewWareHouseDao newWareHouseDao;
    private SqlTemplateService sqlTemplateService;

    public AppraiserDaoImpl( SqlTemplateService sqlTemplateService) {
        this.sqlTemplateService = sqlTemplateService;
    }

    @Override
    public List<Map<String,Object>> queryAppraiserByCity(String city) {
        Map<String, Object> params = ParamsBean.newInstance().put("city",city).build();
        String sql = sqlTemplateService.getSql("evaluate.appraiser.queryAppraiserByCity", params);
        return newWareHouseDao.select(sql, params);
    }
}
