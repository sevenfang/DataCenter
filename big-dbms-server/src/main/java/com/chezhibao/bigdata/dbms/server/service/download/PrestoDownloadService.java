package com.chezhibao.bigdata.dbms.server.service.download;

import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.dbms.server.dao.impl.PrestoSatementDaoImpl;
import com.chezhibao.bigdata.dbms.server.enums.DbmsServerEnums;
import com.chezhibao.bigdata.dbms.server.exception.DbmsServerEception;
import com.chezhibao.bigdata.dbms.server.handler.PrestoDataHandler;
import com.chezhibao.bigdata.dbms.server.utils.DBDataUtils;
import com.chezhibao.bigdata.dbms.server.vo.DbSqlExecuteVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/10.
 */
@Component
@Slf4j
public class PrestoDownloadService extends AbstractDataDownload {

    @Resource(name="AllDs")
    private HashMap<String,Object> allDs;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);


    @Autowired
    private PrestoDataHandler prestoDataHandler;

    @Override
    public Object download(DbSqlExecuteVO dbSqlExecuteVO, WebSocketSession session) {

        return super.download(dbSqlExecuteVO, session);
    }

    @Override
    public DataSource getDataSource(String name) {
        return (DataSource)allDs.get(name);
    }

    @Override
    public Object sendData(Statement ps,String sql, Integer page, Integer size, WebSocketSession session) throws Exception {
        List<LinkedHashMap<String, Object>> data=new ArrayList<>();
        List<LinkedHashMap<String, Object>> temp=null;
        Future<PrestoSatementDaoImpl.PrestoQueryResult> submit = executorService.submit(new Callable<PrestoSatementDaoImpl.PrestoQueryResult>() {
            @Override
            public PrestoSatementDaoImpl.PrestoQueryResult call() {
                PrestoSatementDaoImpl.PrestoQueryResult queryResult;
                try {
                    queryResult = PrestoSatementDaoImpl.PrestoQueryResult.getInstance(ps.execute(sql));
                } catch (SQLException e) {
                    log.error("【数据查询服务】presto下载查询出错了！",e);
                    try {
                        ps.close();
                    } catch (SQLException w) {
                        log.error("【数据查询服务】presto下载查询出错了！",w);
                    }
                    queryResult = PrestoSatementDaoImpl.PrestoQueryResult.getInstance(false);
                    queryResult.setE(e);
                }
                return queryResult;
            }
        });


        PrestoSatementDaoImpl.PrestoQueryResult queryResult = submit.get();

        if(queryResult.getIsSuccess()){
            do{
                Thread.sleep(1000);
                ResultSet resultSet = ps.getResultSet();
                temp = prestoDataHandler.transQueryData(resultSet, page, size);
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
        }else{
            throw new DbmsServerEception(DbmsServerEnums.SQL_HANDLER_EXCEPTION,queryResult.getE());
        }

        return true;
    }
}
