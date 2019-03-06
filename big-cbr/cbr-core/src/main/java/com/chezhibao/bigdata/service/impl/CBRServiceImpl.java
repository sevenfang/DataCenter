package com.chezhibao.bigdata.service.impl;

import com.chezhibao.bigdata.bo.CBRBO;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.dao.BigdataDao;
import com.chezhibao.bigdata.service.CBRService;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/11.
 */
@Service
@Slf4j
public class CBRServiceImpl implements CBRService {


    @Resource(name="bigdataDao")
    private BigdataDao bigdataDao;

    @Autowired
    private SqlTemplateService sqlTemplateService;

    @Override
    public Boolean saveCBR(CBRBO cbr) {
        Date date = new Date();
        cbr.setCreatedTime(date);
        cbr.setUpdatedTime(date);
        Map<String, Object> params = ObjectCommonUtils.object2Map(cbr);
        log.debug("【实时报表】保存报表参数{}",params);
        String sql = sqlTemplateService.getSql("cbr.cbr.saveCBR", params);
        bigdataDao.insert(sql,params);
        return true;
    }

    @Override
    public Boolean changeStatusById(String realreportId,Integer status) {
        Map<String, Object> params = ParamsBean.newInstance().put("status", status).put("realreportId", realreportId).build();
        String sql = sqlTemplateService.getSql("cbr.cbr.changeStatusById", params);
        bigdataDao.update(sql,params);
        return true;
    }

    @Override
    public Boolean changeLevelById(String realreportId, Integer level) {
        Map<String, Object> params = ParamsBean.newInstance().put("level", level).put("realreportId", realreportId).build();
        String sql = sqlTemplateService.getSql("cbr.cbr.changeLevelById", params);
        bigdataDao.update(sql,params);
        return true;
    }

    @Override
    public Boolean saveOrUpdateCBR(CBRBO cbr) {
        //查询报表是否存在
        CBRBO cbrById = getCbrById(cbr.getRealreportId());
        if(cbrById==null){
            //不存在
            saveCBR(cbr);
        }else{
            //存在就更新
            updateCBR(cbr);
        }
        return true;
    }

    @Override
    public CBRBO getCbrById(String realreportId) {
        Map<String, Object> params = ParamsBean.newInstance().put("realreportId", realreportId).build();
        String sql = sqlTemplateService.getSql("cbr.cbr.getCbrById", params);
        //查询报表信息
        List<Map<String, Object>> select = bigdataDao.select(sql, params);
        //判断报表是否的存在
        if(ObjectUtils.isEmpty(select)){
            return null;
        }
        Map<String, Object> map = select.get(0);
        //返回结果
        return ObjectCommonUtils.map2Object(map,CBRBO.class);
    }

    @Override
    public Boolean updateCBR(CBRBO cbr) {
        Date date = new Date();
        cbr.setUpdatedTime(date);
        Map<String, Object> params = ObjectCommonUtils.object2Map(cbr);
        log.debug("【实时报表】通过reqlreportId更新报表参数{}",params);
        String sql = sqlTemplateService.getSql("cbr.cbr.updateCBR", params);
        bigdataDao.update(sql,params);
        return true;
    }

    @Override
    public List<CBRBO> getAllCBR() {
        Map<String ,Object> params = new HashMap<>();
        String sql = sqlTemplateService.getSql("cbr.cbr.getAllCBR", params);
        List<Map<String, Object>> select = bigdataDao.select(sql, params);
        if(ObjectUtils.isEmpty(select)){
            return null;
        }
        return ObjectCommonUtils.map2Object(select,CBRBO.class);
    }
}
