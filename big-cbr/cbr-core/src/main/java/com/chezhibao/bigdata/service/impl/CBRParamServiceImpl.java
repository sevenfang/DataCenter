package com.chezhibao.bigdata.service.impl;

import com.chezhibao.bigdata.bo.CBRParamBO;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.dao.BigdataDao;
import com.chezhibao.bigdata.service.CBRParamService;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/15.
 */
@Service
@Slf4j
public class CBRParamServiceImpl implements CBRParamService {
    @Resource(name="bigdataDao")
    private BigdataDao bigdataDao;

    @Autowired
    private SqlTemplateService sqlTemplateService;

    @Override
    public Boolean saveParam(CBRParamBO cbrParamBO) {
        Date date = new Date();
        cbrParamBO.setCreatedTime(date);
        cbrParamBO.setUpdatedTime(date);
        Map<String, Object> params = ObjectCommonUtils.object2Map(cbrParamBO);
        String sql = sqlTemplateService.getSql("cbr.CBRParam.saveParam", params);
        return bigdataDao.insert(sql, params);
    }

    @Override
    public Boolean updateParamByName(CBRParamBO cbrParamBO) {
        Date date = new Date();
        cbrParamBO.setUpdatedTime(date);
        Map<String, Object> params = ObjectCommonUtils.object2Map(cbrParamBO);
        String sql = sqlTemplateService.getSql("cbr.CBRParam.updateParamByName", params);
        bigdataDao.update(sql, params);
        return true;
    }

    @Override
    public Boolean updateParamById(CBRParamBO cbrParamBO) {
        Date date = new Date();
        cbrParamBO.setUpdatedTime(date);
        Map<String, Object> params = ObjectCommonUtils.object2Map(cbrParamBO);
        String sql = sqlTemplateService.getSql("cbr.CBRParam.updateParamById", params);
        bigdataDao.update(sql, params);
        return true;
    }

    @Override
    public Boolean saveOrupdateParam(List<CBRParamBO> cbrParamBOs) {
        for(CBRParamBO cbrParamBO : cbrParamBOs){
            //获取数据库中的参数信息
//            CBRParamBO paramByParamName = getParamByParamName(cbrParamBO.getRealreportId(), cbrParamBO.getParamName());
            //判断是否存在
            if(ObjectUtils.isEmpty(cbrParamBO.getId())){
                //不存在就存储
                saveParam(cbrParamBO);
            }else {
                //存在就更新
                updateParamById(cbrParamBO);
            }
        }
        return true;
    }

    @Override
    public List<CBRParamBO> getAllParamsByRealreportId(String realreportId) {
        Map<String, Object> params = ParamsBean.newInstance().put("realreportId",realreportId).build();
        String sql = sqlTemplateService.getSql("cbr.CBRParam.getAllParamsByRealreportId", params);
        List<Map<String, Object>> select = bigdataDao.select(sql, params);
        //判断返回的是否为空
        if(ObjectUtils.isEmpty(select)){
            return new ArrayList<>();
        }
        //返回数据
        return  ObjectCommonUtils.map2Object(select,CBRParamBO.class);
    }

    @Override
    public CBRParamBO getParamByParamName(String realreportId, String paramName) {
        Map<String, Object> params = ParamsBean.newInstance().put("realreportId",realreportId).put("paramName",paramName).build();
        String sql = sqlTemplateService.getSql("cbr.CBRParam.getParamByParamName", params);
        List<Map<String, Object>> select = bigdataDao.select(sql, params);
        //判断返回的是否为空
        if(ObjectUtils.isEmpty(select)){
            return null;
        }
        //返回数据
        return  ObjectCommonUtils.map2Object(select.get(0),CBRParamBO.class);
    }


    @Override
    public Boolean deleteParamById(List<Integer> ids) {
        if(ObjectUtils.isEmpty(ids)){
            return true;
        }
        try {
            Map<String, Object> params = ParamsBean.newInstance().put("ids",ids).build();
            String sql = sqlTemplateService.getSql("cbr.CBRParam.deleteParamById", params);
            bigdataDao.delete(sql,params);
            return true;
        } catch (Exception e) {
            log.error("【CBR】删除指定参数出错了！！ids:{}",ids,e);
        }
        return false;
    }

    @Override
    public CBRParamBO getParamById(Integer id) {
        Map<String, Object> params = ParamsBean.newInstance().put("id",id).build();
        String sql = sqlTemplateService.getSql("cbr.CBRParam.getParamById", params);
        List<Map<String, Object>> select = bigdataDao.select(sql, params);
        //判断是否存在
        if(ObjectUtils.isEmpty(select)){
            return null;
        }
        //返回数据
        return ObjectCommonUtils.map2Object(select.get(0),CBRParamBO.class);
    }
}
