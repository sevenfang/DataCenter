package com.chezhibao.bigdata.dbms.server.service;

import com.chezhibao.bigdata.dbms.server.vo.DbSqlExecuteVO;

import javax.websocket.Session;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/10.
 */
public interface DataDownloadService {
    void downloadByWebsocket(DbSqlExecuteVO dbSqlExecuteVO, Session session);
}
