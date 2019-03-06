package com.chezhibao.bigdata.service.impl;

import com.chezhibao.bigdata.bo.QueryDataBO;
import com.chezhibao.bigdata.dao.CBRCommonDao;
import com.chezhibao.bigdata.dao.CommonDao;
import com.chezhibao.bigdata.pojo.QuerySql;
import com.chezhibao.bigdata.service.QueryDataService;
import com.chezhibao.bigdata.service.QuerySqlService;
import com.chezhibao.bigdata.vo.QueryDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 获取报表数据服务
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/30.
 */
@Service
public class QueryDataServiceImpl implements QueryDataService {

    public static final String ITEM = "item";

    @Resource(name = "commonDao")
    private CommonDao commonDao;

    @Resource(name = "cBRCommonDao")
    private CBRCommonDao cbrCommonDao;

    @Autowired
    private QuerySqlService querySqlService;

    @Override
    public List<LinkedHashMap<String, Object>> getCbrData(QueryDataVo queryDataVo) {
        String sql = queryDataVo.getSql();
        if(StringUtils.isEmpty(sql)){
            return getData(queryDataVo);
        }
        return getDataBySql(queryDataVo);
    }

    @Override
    public List<LinkedHashMap<String, Object>> getDataBySql(QueryDataVo queryDataVo) {
        return cbrCommonDao.query(queryDataVo.getSql(),queryDataVo.getParams());
    }

    @Override
    public List<LinkedHashMap<String, Object>> getData(QueryDataVo queryDataVo) {
        //获取sql查询、拼装参数
        Map<String, Object> params = queryDataVo.getParams()==null?new HashMap<>():queryDataVo.getParams();
        //组装QuerySqlBO对象
        QueryDataBO queryDataBO = new QueryDataBO();
        queryDataBO.setLevel(queryDataVo.getLevel());
        queryDataBO.setParams(params);
        queryDataBO.setRealreportId(queryDataVo.getRealreportId());
        //获取查询的sql
        List<QuerySql> sqls = querySqlService.getSql(queryDataBO);
        //获取当前的sql数据
        QuerySql curSql = sqls.get(0);
        List<LinkedHashMap<String, Object>> cur = commonDao.query(curSql.getDatasource(),curSql.getSql(), params);
        //获取对比的sql数据
        QuerySql constrastSql = sqls.get(1);
        List<LinkedHashMap<String, Object>> contrast = commonDao.query(constrastSql.getDatasource(),constrastSql.getSql(), params);
        return merge(cur,contrast);
    }



    private List<LinkedHashMap<String, Object>> merge(List<LinkedHashMap<String, Object>> cur, List<LinkedHashMap<String, Object>> contrast){
        Map<String,LinkedHashMap<String, Object>> a=new LinkedHashMap<>();
        Map<String,LinkedHashMap<String, Object>> b=new LinkedHashMap<>();
        for(LinkedHashMap<String, Object> m : cur){
            a.put(m.get(ITEM)+"",m);
        }
        for(LinkedHashMap<String, Object> m : contrast){
            b.put(m.get(ITEM)+"",m);
        }
        Set<String> keys = new LinkedHashSet<>(a.keySet());
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        keys.addAll(b.keySet());
        for(String key : keys){
            LinkedHashMap<String, Object> curData = a.get(key);
            LinkedHashMap<String, Object> yesData = b.get(key);
            //将两组数据合并为一组数据
            if(ObjectUtils.isEmpty(curData)){
                curData=new LinkedHashMap<>();
            }
            if(ObjectUtils.isEmpty(yesData)){
                yesData=new LinkedHashMap<>();
            }
            for(Map.Entry<String,Object> e:yesData.entrySet()){
                if(ITEM.equals(e.getKey())){
                    curData.put(e.getKey(),e.getValue());
                }
                curData.put("_"+e.getKey(),e.getValue());
            }
            result.add(curData);
        }
        return result;
    }
}
