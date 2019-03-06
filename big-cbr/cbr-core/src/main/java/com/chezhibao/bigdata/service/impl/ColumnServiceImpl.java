package com.chezhibao.bigdata.service.impl;

import com.chezhibao.bigdata.bo.CBRColumnBO;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.dao.BigdataDao;
import com.chezhibao.bigdata.service.ColumnService;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/11.
 */
@Service
@Slf4j
public class ColumnServiceImpl implements ColumnService {

    @Resource(name="bigdataDao")
    private BigdataDao bigdataDao;

    @Autowired
    private SqlTemplateService sqlTemplateService;

    @Override
    public Boolean saveColumn(CBRColumnBO cbrColumnBO) {
        //定义创建、更新时间
        Date date = new Date();
        cbrColumnBO.setCreatedTime(date);
        cbrColumnBO.setUpdatedTime(date);
        Map<String, Object> params = ObjectCommonUtils.object2Map(cbrColumnBO);
        log.debug("【实时报表】保存字段参数{}",params);
        String sql = sqlTemplateService.getSql("cbr.Column.saveColumn", params);
        return bigdataDao.insert(sql, params);
    }

    @Override
    public Boolean updateColumnByRealreportIdAndKey(CBRColumnBO cbrColumnBO) {
        //定义更新时间
        Date date = new Date();
        cbrColumnBO.setUpdatedTime(date);
        Map<String, Object> params = ObjectCommonUtils.object2Map(cbrColumnBO);
        log.debug("【实时报表】通过字段名称更新字段参数{}",params);
        String sql = sqlTemplateService.getSql("cbr.Column.updateColumnByRealreportIdAndKey", params);
       bigdataDao.update(sql, params);
        return true;
    }

    @Override
    public Boolean updateColumnById(CBRColumnBO cbrColumnBO) {
        //定义更新时间
        Date date = new Date();
        cbrColumnBO.setUpdatedTime(date);
        Map<String, Object> params = ObjectCommonUtils.object2Map(cbrColumnBO);
        log.debug("【实时报表】通过字段名称更新字段参数{}", params);
        String sql = sqlTemplateService.getSql("cbr.Column.updateColumnById", params);
        bigdataDao.update(sql, params);
        return true;
    }

    @Override
    public Boolean saveOrUpdateColumn(List<CBRColumnBO> cbrColumnBOS) {
        for(CBRColumnBO cbrColumnBO : cbrColumnBOS){
            //判断是否存在
//            CBRColumnBO columnByOrder = getColumnByRealreportIdAndKey(cbrColumnBO.getRealreportId(), cbrColumnBO.getKey());
            if(cbrColumnBO.getId()==null){
                //不存在就新增
                saveColumn(cbrColumnBO);
            }else{
                //更新
                updateColumnById(cbrColumnBO);
            }
        }
        return true;
    }



    @Override
    public CBRColumnBO getColumnByRealreportIdAndKey(String realreportId, String key) {
        //组装参数和sql
        Map<String, Object> params = ParamsBean.newInstance().put("realreportId", realreportId).put("key", key).build();
        String sql = sqlTemplateService.getSql("cbr.Column.getColumnByRealreportIdAndKey", params);
        //查询数据
        List<Map<String, Object>> select = bigdataDao.select(sql, params);
        //判断是否有数据返回
        if(ObjectUtils.isEmpty(select)){
            return null;
        }
        Map<String, Object> map = select.get(0);
        //转换成CBRColumnBO返回
        return ObjectCommonUtils.map2Object(map,CBRColumnBO.class);
    }

    @Override
    public List<CBRColumnBO> getColumnByRealreportId(String realreportId) {
        //组装参数和sql
        Map<String, Object> params = ParamsBean.newInstance().put("realreportId", realreportId).build();
        String sql = sqlTemplateService.getSql("cbr.Column.getColumnByRealreportId", params);
        //查询数据
        List<Map<String, Object>> select = bigdataDao.select(sql, params);
        //判断是否有数据返回
        if(ObjectUtils.isEmpty(select)){
            return null;
        }
        //转换成CBRColumnBO返回
        return ObjectCommonUtils.map2Object(select,CBRColumnBO.class);
    }

    @Override
    public Boolean deleteColumnById(List<Integer> ids) {
        if (ObjectUtils.isEmpty(ids)){
            return true;
        }
        try {
            Map<String, Object> params = ParamsBean.newInstance().put("ids", ids).build();
            String sql = sqlTemplateService.getSql("cbr.Column.deleteColumnById", params);
            bigdataDao.delete(sql,params);
        } catch (Exception e) {
            log.error("【CBR】删除报表字段出错了！",e);
            return false;
        }
        return true;
    }

    @Override
    public CBRColumnBO getColumnById(Integer id) {
        Map<String, Object> params = ParamsBean.newInstance().put("id", id).build();
        String sql = sqlTemplateService.getSql("cbr.Column.getColumnById", params);
        List<Map<String, Object>> select = bigdataDao.select(sql, params);
        //判断是否存在
        if(ObjectUtils.isEmpty(select)){
            return null;
        }
        //返回数据
        return ObjectCommonUtils.map2Object(select.get(0),CBRColumnBO.class);
    }
}
