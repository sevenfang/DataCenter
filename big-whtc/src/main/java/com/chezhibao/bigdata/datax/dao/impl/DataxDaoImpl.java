package com.chezhibao.bigdata.datax.dao.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.dao.BigdataDao;
import com.chezhibao.bigdata.dao.WareHouseDao;
import com.chezhibao.bigdata.datax.dao.DataxDao;
import com.chezhibao.bigdata.datax.pojo.DataxConfig;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/4/27
 * Created by WangCongJun on 2018/4/27.
 */
@Repository
@Slf4j
public class DataxDaoImpl implements DataxDao {


    @Reference(check = false)
    private BigdataDao wareHouseDao;


    private SqlTemplateService sqlTemplateService;

    @Autowired
    public DataxDaoImpl(SqlTemplateService sqlTemplateService) {

        this.sqlTemplateService = sqlTemplateService;
    }

    @Override
    public Boolean saveOrUpdate(DataxConfig dataxConfig) {
        String sql;
        if(StringUtils.isEmpty(dataxConfig.getId())){
            sql = sqlTemplateService.getSql("datax.web_config.save",ObjectCommonUtils.object2Map(dataxConfig));
            wareHouseDao.insert(sql, ObjectCommonUtils.object2Map(dataxConfig));
        }else{
            sql = sqlTemplateService.getSql("datax.web_config.update",ObjectCommonUtils.object2Map(dataxConfig));
            wareHouseDao.update(sql, ObjectCommonUtils.object2Map(dataxConfig));
        }

        return true;
    }

    @Override
    public List<Map<String, Object>> getJobInfo(Map<String,Object> params) {
        String sql = sqlTemplateService.getSql("datax.web_config.select", params);
        List<Map<String, Object>> result = wareHouseDao.select( sql, params);
        return result;
    }

    @Override
    public Integer count(Map<String,Object> params) {
        String sql = sqlTemplateService.getSql("datax.web_config.count", params);
        List<Map<String, Object>> result = wareHouseDao.select( sql, params);
        log.debug("【DataX页面配置】总数量：{}",result);
        if(ObjectUtils.isEmpty(result)){
            return 0;
        }
        String count = result.get(0).get("count").toString();
        if(StringUtils.isEmpty(count)){
            return 0;
        }
        return Integer.parseInt(count);
    }

    @Override
    public Boolean delete(Map<String,Object> params) {
        try {
            String sql = sqlTemplateService.getSql("datax.web_config.delete", params);
            wareHouseDao.delete(sql, params);
            return true;
        }catch (Exception e){
            log.error("【DataX页面配置】删除任务出错：参数params{}",params,e);
        }

        return false;
    }

    @Override
    public Boolean update(Map<String, Object> params) {
        try {
            String sql = sqlTemplateService.getSql("datax.web_config.update", params);
            wareHouseDao.update( sql, params);
            return true;
        }catch (Exception e){
            log.error("【DataX页面配置】删除任务出错：参数params{}",params,e);
        }

        return false;
    }
}
