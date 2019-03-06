package com.chezhibao.bigdata.dao.impl;

import com.chezhibao.bigdata.dao.CBRCommonDao;
import com.chezhibao.bigdata.dao.SessionDao;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/6/22.
 */
@Repository
public class SessionDaoImpl implements SessionDao {

    @Resource(name="cBRCommonDao")
    private CBRCommonDao commonDao;

    private SqlTemplateService sqlTemplateService;

    public SessionDaoImpl(SqlTemplateService sqlTemplateService) {
        this.sqlTemplateService = sqlTemplateService;
    }

    @Override
    public List<Map<String, Object>> getAllSessions(String start,String end,List<String> excludeSessions) {

        Map<String, Object> params = ParamsBean.newInstance().put("start", start).put("end", end).put("excludeSessions", excludeSessions).build();
        String sql = sqlTemplateService.getSql("auction.session.getAllSessions", params);
        List<Map<String, Object>> names = commonDao.select( sql, params);
        return names;
    }

    @Override
    public List<Map<String, Object>> getAllSessions(Map<String,Object> params) {
        String sql = sqlTemplateService.getSql("auction.session.getAllSessionIds", params);
        List<Map<String, Object>> sessions = commonDao.select( sql, params);
        return sessions;
    }

    @Override
    public void addExcludeSession(String name) {
        Map<String, Object> params = ParamsBean.newInstance().put("name",name).build();
        String sql = sqlTemplateService.getSql("auction.session.addExcludeSession", params);
        commonDao.insert(sql,params);
    }

    @Override
    public void delExcludeSession(String name) {
        Map<String, Object> params = ParamsBean.newInstance().put("name",name).build();
        String sql = sqlTemplateService.getSql("auction.session.delExcludeSession", params);
        commonDao.insert(sql,params);
    }

    @Override
    public List<Map<String, Object>> getAllExcludeSessions() {

        Map<String, Object> params = new HashMap<>();
        String sql = sqlTemplateService.getSql("auction.session.getAllExcludeSessions", params);
        List<Map<String, Object>> names = commonDao.select( sql, params);

        return names;
    }

    @Override
    public List<Map<String, Object>> getSessionByName(String name) {
        Map<String, Object> params = ParamsBean.newInstance().put("name",name).build();
        String sql = sqlTemplateService.getSql("auction.session.getSessionByName", params);
        List<Map<String, Object>> names = commonDao.select(sql, params);
        return names;
    }
}
