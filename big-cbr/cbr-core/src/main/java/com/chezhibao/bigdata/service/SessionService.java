package com.chezhibao.bigdata.service;


import com.chezhibao.bigdata.bo.SessionBO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/6/22.
 */
public interface SessionService {

    /**
     * {@link SessionService#getAllSignificantSessions(String)}
     * @param start
     * @return
     */
    @Deprecated
    List<String> getAllSignificantSession(String start);

    /**
     * 查询所有有效的场次（id，name）
     * @param start
     * @return
     */
    List<Map<String,Object>> getAllSignificantSessions(String start);

    /**
     * 查询指定时间段内的场次Id
     * @param start
     * @param end
     * @return
     */
    List<Integer> getAllSignificantSessionsIdByTime(Date start, Date end);

    /**
     * 查询指定时间段内的场次Id
     * @param start 时间字符串 yyyy-MM-dd HH:mm:ss
     * @param end 时间字符串 yyyy-MM-dd HH:mm:ss
     * @return
     */
    List<Integer> getAllSignificantSessionsIdByTime(String start, String end);

    /**
     * 添加过滤场次名称
     * @param name
     */
    void addExcludeSession(String name);

    /**
     * 添加多个过滤场次名称
     * @param name
     */
    void addExcludeSession(List<String> name);

    /**
     * 获取所有过滤场次名称
     * @return
     */
    List<String> getAllExcludeSession();

    /**
     * 删除过滤场次名称
     * @param name
     */
    void delExcludeSession(String name);

    /**
     * 删除多个过滤场次名称
     * @param name
     */
    void delExcludeSession(List<String> name);

    /**
     * 获取所有场次ID
     * @param sessionBO
     */
    List<Integer> getAllSessionIdByTimeORNames(SessionBO sessionBO);

    /**
     * 获取24小时场次ID
     */
    List<Integer> getAllSessionIdIn24Hours();
}
