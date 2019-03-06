package com.chezhibao.bigdata.cbr.download.service.impl;

import com.chezhibao.bigdata.cbr.download.bo.DownloadSqlBO;
import com.chezhibao.bigdata.cbr.download.service.DownloadDataService;
import com.chezhibao.bigdata.cbr.download.service.DownloadSqlService;
import com.chezhibao.bigdata.dao.CommonDao;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/12.
 */
@Service
@Slf4j
public class DownloadDataServiceImp implements DownloadDataService {

    public static final String ITEM = "item";

    @Resource(name = "commonDao")
    private CommonDao commonDao;

    @Autowired
    private DownloadSqlService downloadSqlService;

    @Autowired
    private SqlTemplateService sqlTemplateService;

    @Override
    //dimensionCol
    public List<LinkedHashMap<String, Object>> download(String realreportId,Map<String,Object> params) {
        List<DownloadSqlBO> allSql = downloadSqlService.getAllSql(realreportId);
        List<LinkedHashMap<String, Object>> c = getData(allSql.get(0),params);
        List<LinkedHashMap<String, Object>> y = getData(allSql.get(1),params);
        return merge(c,y);
    }

    private List<LinkedHashMap<String,Object>> getData(DownloadSqlBO sqlBO,Map<String,Object> params){
        String sql = sqlTemplateService.getSqlFromStringTemplate(sqlBO.getSql(), params);
        return commonDao.query(sqlBO.getDatasource(),sql,params);
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
