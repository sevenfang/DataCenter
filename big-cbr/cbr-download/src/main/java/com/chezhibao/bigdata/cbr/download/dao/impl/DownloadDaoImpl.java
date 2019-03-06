package com.chezhibao.bigdata.cbr.download.dao.impl;

import com.chezhibao.bigdata.cbr.download.dao.DownloadDao;
import com.chezhibao.bigdata.dao.BigdataDao;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/12.
 */
@Repository
@Slf4j
public class DownloadDaoImpl implements DownloadDao {
    @Resource(name="bigdataDao")
    private BigdataDao bigdataDao;

    @Autowired
    private SqlTemplateService sqlTemplateService;

    @Override
    public Boolean insert(String s, Map<String, Object> map) {
        String sql = sqlTemplateService.getSql(s, map);
        return bigdataDao.insert(sql,map);
    }

    @Override
    public void delete(String s, Map<String, Object> map) {
        String sql = sqlTemplateService.getSql(s, map);
        bigdataDao.delete(sql,map);
    }

    @Override
    public void update(String s, Map<String, Object> map) {
        String sql = sqlTemplateService.getSql(s, map);
        bigdataDao.update(sql,map);
    }

    @Override
    public List<Map<String, Object>> select(String s, Map<String, Object> map) {
        String sql = sqlTemplateService.getSql(s, map);
        return bigdataDao.select(sql,map);
    }

    @Override
    public List<LinkedHashMap<String, Object>> query(String s, Map<String, Object> map) {
        String sql = sqlTemplateService.getSql(s, map);
        return bigdataDao.query(sql,map);
    }
}
