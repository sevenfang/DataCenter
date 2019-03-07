package com.chezhibao.bigdata.dbms.server.service.download;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.dbms.server.dao.SqlStatementDao;
import com.chezhibao.bigdata.dbms.server.enums.DbmsServerEnums;
import com.chezhibao.bigdata.dbms.server.exception.DbmsServerEception;
import com.chezhibao.bigdata.dbms.server.utils.DBDataUtils;
import com.chezhibao.bigdata.dbms.server.utils.StatementUtils;
import com.chezhibao.bigdata.dbms.server.vo.DbSqlExecuteVO;
import com.mysql.jdbc.exceptions.MySQLStatementCancelledException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/10.
 */
@Component
@Slf4j
public class SimpleDownloadService extends AbstractDataDownload {

    @Resource(name="AllDs")
    private HashMap<String,Object> allDs;


    @Override
    public Object download(DbSqlExecuteVO dbSqlExecuteVO, WebSocketSession session) {
        return super.download(dbSqlExecuteVO, session);
    }

    @Override
    public DataSource getDataSource(String name) {
        return (DataSource)allDs.get(name);
    }

    private List<LinkedHashMap<String, Object>> getData(Statement ps, String sql,Integer page,Integer size) throws Exception{
        if(page!=0&&size!=0) {
            ps.setMaxRows(page * size);
        }
        ResultSet resultSet;
        try {

            resultSet = ps.executeQuery(sql);
            if(page!=0&&size!=0) {
                resultSet.relative((page - 1) * size);
            }
        }catch (Exception e){
            log.error("【数据查询服务】查询出错了！",e);
            throw new DbmsServerEception(DbmsServerEnums.SQL_HANDLER_EXCEPTION,e);
        }
        return DBDataUtils.transQueryData(resultSet);
    }

    @Override
    public Object sendData(Statement ps, String sql,Integer page,Integer size, WebSocketSession session) throws Exception {
        List<LinkedHashMap<String, Object>> data=new ArrayList<>();
        List<LinkedHashMap<String, Object>> temp=null;

        do{
//            String newSql = "Select * from ("+sql+")t limit "+page+","+size;
            temp = getData(ps,sql,page,size);
            if(temp.size()>0){
                data.addAll(temp);
                BigdataResult result = BigdataResult.build(100,data.size()+"");
                TextMessage textMessage = new TextMessage(JSON.toJSONString(result));
                session.sendMessage(textMessage);
            }
            page++;
        }while (temp.size()>0);
        BigdataResult result = BigdataResult.ok(data);
        TextMessage textMessage = new TextMessage(JSON.toJSONString(result));
        session.sendMessage(textMessage);
        return true;
    }
}
