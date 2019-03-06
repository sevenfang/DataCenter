package com.chezhibao.bigdata.service.impl;

import com.chezhibao.bigdata.bo.QueryDataBO;
import com.chezhibao.bigdata.bo.QuerySqlBO;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.dao.BigdataDao;
import com.chezhibao.bigdata.dto.QuerySqlDTO;
import com.chezhibao.bigdata.pojo.QuerySql;
import com.chezhibao.bigdata.service.QuerySqlService;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/1.
 */
@Service
@Slf4j
public class QuerySqlServiceImpl implements QuerySqlService {

    @Autowired
    private SqlTemplateService sqlTemplateService;

    @Resource(name="bigdataDao")
    private BigdataDao bigdataDao;

    @Override
    public List<QuerySql> getSql(QueryDataBO queryDataBO) {
        //定义返回的对象
        List<QuerySql> sqls = new ArrayList<>();
        //抽取请求的参数
        String realreportId = queryDataBO.getRealreportId();
        Map<String, Object> params = queryDataBO.getParams();
        Integer level = queryDataBO.getLevel();
        //获取当前的sql
        sqls.add(getQuerySqlByName(realreportId,""+level,params));
        //获取对比的sql
        sqls.add(getQuerySqlByName(realreportId,"_"+level,params));
        return sqls;
    }
    private QuerySql getQuerySqlByName(String realreportId,String name,Map<String,Object> params){
        QuerySqlBO curSqlBO = getSqlByName(realreportId, name);
        QuerySql querySql = QuerySqlDTO.transQuerySqlBOToQuerySql(curSqlBO);
        String curSql = sqlTemplateService.getSqlFromStringTemplate(querySql.getSql(), params);
        QuerySql curQuerySql = new QuerySql();
        curQuerySql.setSql(curSql);
        curQuerySql.setDatasource(querySql.getDatasource());
        return curQuerySql;
    }

    @Override
    public Boolean saveSql(QuerySqlBO querySql) {
        Date date=new Date();
        querySql.setCreatedTime(date);
        querySql.setUpdatedTime(date);
        Map<String, Object> params = ObjectCommonUtils.object2Map(querySql);
        log.debug("【实时报表】存储sql参数{}",params);
        String sql = sqlTemplateService.getSql("cbr.QuerySql.saveSql", params);
        bigdataDao.insert(sql,params);
        return true;
    }

    @Override
    public Boolean updateSqlByName(QuerySqlBO querySql) {
        Date date=new Date();
        querySql.setUpdatedTime(date);
        Map<String, Object> params = ObjectCommonUtils.object2Map(querySql);
        log.debug("【实时报表】通过名称更新sql参数{}",params);
        String sql = sqlTemplateService.getSql("cbr.QuerySql.updateSqlByName", params);
        bigdataDao.update(sql,params);
        return true;
    }

    @Override
    public Boolean updateSqlById(QuerySqlBO querySql) {
        Date date=new Date();
        querySql.setUpdatedTime(date);
        Map<String, Object> params = ObjectCommonUtils.object2Map(querySql);
        log.debug("【实时报表】通过名称更新sql参数{}",params);
        String sql = sqlTemplateService.getSql("cbr.QuerySql.updateSqlById", params);
        bigdataDao.update(sql,params);
        return true;
    }



    @Override
    public Boolean saveOrupdateSql(List<QuerySqlBO> querySqls) {
        for(QuerySqlBO querySql : querySqls){
            //查询是否存在
//            QuerySqlBO sqlByName = getSqlByName(querySql.getRealreportId(), querySql.getName());
            //判断是否存在
            if(querySql.getId()==null){
                //不存在就保存
                saveSql(querySql);
            }else {
                //存在就更新
                updateSqlById(querySql);
            }
        }
        return true;
    }


    @Override
    public QuerySqlBO getSqlByName(String realreportId, String name) {
        //组装查询请求
        Map<String, Object> params = ParamsBean.newInstance().put("realreportId", realreportId).put("name", name).build();
        String sql = sqlTemplateService.getSql("cbr.QuerySql.getSqlByName", params);
        List<Map<String, Object>> select = bigdataDao.select(sql, params);
        //判断查询结果
        if(ObjectUtils.isEmpty(select)){
            return null;
        }
        Map<String, Object> map = select.get(0);
        //返回查询结果
        return ObjectCommonUtils.map2Object(map,QuerySqlBO.class);
    }

    @Override
    public List<QuerySqlBO> getSqlByRealreportId(String realreportId) {
        //组装查询请求
        Map<String, Object> params = ParamsBean.newInstance().put("realreportId", realreportId).build();
        String sql = sqlTemplateService.getSql("cbr.QuerySql.getSqlByRealreportId", params);
        List<Map<String, Object>> select = bigdataDao.select(sql, params);
        //判断查询结果
        if(ObjectUtils.isEmpty(select)){
            return null;
        }
        //返回查询结果
        return ObjectCommonUtils.map2Object(select,QuerySqlBO.class);
    }

    @Override
    public Boolean delSqlLevel(String realreportId,List<Integer> index) {
        if(ObjectUtils.isEmpty(index)){
            return true;
        }
        try {
            List<String> names = new ArrayList<>(index.size()*2);
            //组装查询请求
            for (Integer i : index){
                names.add(""+i);
                names.add("_"+i);
            }
            Map<String, Object> params = ParamsBean.newInstance().put("realreportId", realreportId).put("names",names).build();
            String sql = sqlTemplateService.getSql("cbr.QuerySql.delSqlLevel", params);
            bigdataDao.delete(sql,params);
        } catch (Exception e) {
            log.error("【CBR】删除查询sql出错了！！",e);
            return false;
        }
        return true;
    }
}
