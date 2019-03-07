package com.chezhibao.bigdata.dbms.server.service.download;

import com.chezhibao.bigdata.dbms.server.vo.DbSqlExecuteVO;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/10.
 */
public interface DataDownload {
    Object download(DbSqlExecuteVO dbSqlExecuteVO, WebSocketSession session);
    Object sendData(Statement ps, String sql,Integer page, Integer size, WebSocketSession session) throws Exception;
}
