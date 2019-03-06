package com.chezhibao.bigdata.service.impl;

import com.chezhibao.bigdata.bo.SessionBO;
import com.chezhibao.bigdata.common.utils.DateUtils;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.dao.SessionDao;
import com.chezhibao.bigdata.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/6/22.
 */
@Service
@Slf4j
public class SessionServiceImpl implements SessionService {

    private SessionDao sessionDao;

    public SessionServiceImpl(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }


    @Override
    public List<String> getAllSignificantSession(String start) {
        List<Map<String, Object>> allSessions = sessionDao.getAllSessions(start, null, new ArrayList<String>());
        List<String> sessions = new ArrayList<>();
        for (Map<String, Object> m : allSessions) {
            String name = m.get("name") + "";
            sessions.add(name);
        }
        return sessions;
    }

    @Override
    public List<Map<String,Object>> getAllSignificantSessions(String start) {
        List<Map<String, Object>> allSessions = sessionDao.getAllSessions(start, null, new ArrayList<String>());
        List<Map<String,Object>> sessionsId = new ArrayList<>();
        Map<String,Object> s;
        for (Map<String, Object> m : allSessions) {
            String id = m.get("id") + "";
            String name = m.get("name") + "";
            s=new HashMap<>();
            s.put("sessionName",name);
            s.put("sessionId",id);
            sessionsId.add(s);
        }
        return sessionsId;
    }

    @Override
    public List<Integer> getAllSignificantSessionsIdByTime(Date start, Date end) {
        String s = DateUtils.format(start, "yyyy-MM-dd HH:mm:ss");
        String e = DateUtils.format(end, "yyyy-MM-dd HH:mm:ss");
        List<Map<String, Object>> allSessions = sessionDao.getAllSessions(s, e, new ArrayList<String>());
        List<Integer> sessions = new ArrayList<>();
        for (Map<String, Object> m : allSessions) {
            String name = m.get("name") + "";
            sessions.add(Integer.parseInt(name));
        }
        return sessions;
    }

    @Override
    public List<Integer> getAllSignificantSessionsIdByTime(String start, String end) {
        List<Map<String, Object>> allSessions = sessionDao.getAllSessions(start, end, new ArrayList<String>());
        List<Integer> sessions = new ArrayList<>();
        for (Map<String, Object> m : allSessions) {
            String name = m.get("name") + "";
            sessions.add(Integer.parseInt(name));
        }
        return sessions;
    }

    @Override
    public void addExcludeSession(String name) {
        List<Map<String, Object>> sessionByName = sessionDao.getSessionByName(name);
        if(!ObjectUtils.isEmpty(sessionByName)){
           log.error("【实时报表】添加过滤场次名称已存在！"+name);
           return;
        }
        sessionDao.addExcludeSession(name);
    }

    @Override
    public void delExcludeSession(String name) {
        List<Map<String, Object>> sessionByName = sessionDao.getSessionByName(name);
        if(ObjectUtils.isEmpty(sessionByName)){
            log.error("【实时报表】删除过滤场次名称不存在！"+name);
            return;
        }
        sessionDao.delExcludeSession(name);
    }

    @Override
    public List<String> getAllExcludeSession() {
        List<Map<String, Object>> allExcludeSessions = sessionDao.getAllExcludeSessions();
        List<String> sessions = new ArrayList<>();
        for (Map<String, Object> m : allExcludeSessions) {
            String name = m.get("name") + "";
            sessions.add(name);
        }
        return sessions;
    }

    @Override
    public void addExcludeSession(List<String> name) {
        for(String n:name){
            addExcludeSession(n);
        }
    }

    @Override
    public void delExcludeSession(List<String> name) {
        for(String n:name){
            delExcludeSession(n);
        }
    }

    @Override
    public List<Integer> getAllSessionIdByTimeORNames(SessionBO sessionBO) {
        Map<String,Object> params = ObjectCommonUtils.object2Map(sessionBO);
        List<Map<String, Object>> allSessionIds = sessionDao.getAllSessions(params);
        List<Integer> sessionId = new ArrayList<>();
        for(Map<String,Object> m:allSessionIds){
            String id = m.get("id")+"";
            sessionId.add(Integer.parseInt(id));
        }
        return sessionId;
    }

    @Override
    public List<Integer> getAllSessionIdIn24Hours() {
        Date date = new Date();
        Date ydate = org.apache.commons.lang.time.DateUtils.addDays(date, -1);
        SessionBO sessionBO = new SessionBO();
        sessionBO.setStart(ydate);
        sessionBO.setEnd(date);
        Map<String,Object> params = ObjectCommonUtils.object2Map(sessionBO);
        List<Map<String, Object>> allSessionIds = sessionDao.getAllSessions(params);
        List<Integer> sessionId = new ArrayList<>();
        for(Map<String,Object> m:allSessionIds){
            String id = m.get("id")+"";
            sessionId.add(Integer.parseInt(id));
        }
        return sessionId;
    }
}
