package com.chezhibao.bigdata.dao;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/6/22.
 */
public interface SessionDao {
    List<Map<String, Object>> getAllSessions(String start, String end, List<String> excludeSessions);

    List<Map<String, Object>> getAllSessions(Map<String, Object> params);

    void addExcludeSession(String name);

    List<Map<String, Object>> getAllExcludeSessions();

    void delExcludeSession(String name);

    List<Map<String, Object>> getSessionByName(String name);

}
