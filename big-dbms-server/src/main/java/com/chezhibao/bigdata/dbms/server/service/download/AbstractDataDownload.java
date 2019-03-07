package com.chezhibao.bigdata.dbms.server.service.download;

import com.chezhibao.bigdata.dbms.server.enums.DbmsServerEnums;
import com.chezhibao.bigdata.dbms.server.exception.DbmsServerEception;
import com.chezhibao.bigdata.dbms.server.vo.DbSqlExecuteVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/10.
 */
@Slf4j
public abstract class AbstractDataDownload implements DataDownload {
    @Override
    public Object download(DbSqlExecuteVO dbSqlExecuteVO, WebSocketSession session) {
        String dataSource = dbSqlExecuteVO.getName();
        String sql = dbSqlExecuteVO.getSql();

        Connection connection=null;
        Statement ps=null;
        Integer page = 1;
        Integer size = dbSqlExecuteVO.getSize();
        try {
            connection = getDataSource(dataSource).getConnection();
            ps = connection.createStatement();
            sendData(ps,sql,page,size,session);
        }catch (Exception e){
            log.error("【数据查询服务】查询出错了！name:{},sql:{}",dataSource,sql,e);
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e1){
                log.error("【数据查询服务】查询出错了！name:{},sql:{}",dataSource,sql,e1);
            }
            throw new DbmsServerEception(DbmsServerEnums.DOWNLOAD_EXCEPTION,e);
        }
        return true;
    }

    public abstract DataSource getDataSource(String name);
}
