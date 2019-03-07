package com.chezhibao.bigdata.dbms.server.session;

import com.alibaba.druid.pool.DruidPooledPreparedStatement;
import com.chezhibao.bigdata.dbms.server.constants.SessionConstants;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.SQLException;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/4.
 */
@WebListener
@Slf4j
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        DruidPooledPreparedStatement attribute = (DruidPooledPreparedStatement)httpSessionEvent.getSession().getAttribute(SessionConstants.STATEMENT);
        if(attribute!=null){
            try {
                attribute.cancel();
            }catch (SQLException e){
                log.error("【数据查询服务】查询出错了！",e);
            }
        }
    }
}
