package com.chezhibao.bigdata.dbms.server.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Statement;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/6.
 */
@Slf4j
public class StatementUtils {
    /**
     * 关闭session中的statement 并将新的statement写入session
     * @param request
     * @param key
     * @param statement
     */
    public static HttpSession updatePs(HttpServletRequest request, String key, Statement statement) {
        HttpSession session = request.getSession();
        //关闭原先的Statement
        Statement attribute = (Statement) session.getAttribute(key);
        if (attribute != null) {
            try {
                attribute.close();
            } catch (Exception e) {
                log.error("【数据查询服务】查询出错了！",e);
            }
        }
        session.setAttribute(key, statement);
        return session;
    }

    public static Statement getStatement(HttpServletRequest request, String key){
        return (Statement)request.getSession().getAttribute(key);
    }
}
