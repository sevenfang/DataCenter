package com.chezhibao.bigdata.dbms.server.dao.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.chezhibao.bigdata.dbms.server.dao.PrestoSatementDao;
import com.chezhibao.bigdata.dbms.server.enums.DbmsServerEnums;
import com.chezhibao.bigdata.dbms.server.exception.DbmsServerEception;
import com.chezhibao.bigdata.dbms.server.handler.PrestoDataHandler;
import com.chezhibao.bigdata.dbms.server.utils.DBDataUtils;
import com.chezhibao.bigdata.dbms.server.utils.StatementUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/6.
 */
@Repository
@Slf4j
public class PrestoSatementDaoImpl implements PrestoSatementDao {
    @Resource(name = "AllDs")
    private HashMap<String, Object> allDs;

    private PrestoDataHandler prestoDataHandler;

    public PrestoSatementDaoImpl(PrestoDataHandler prestoDataHandler) {
        this.prestoDataHandler = prestoDataHandler;
    }

    private final static ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public List<LinkedHashMap<String, Object>> selectNext(String dataSource, String sql, Map<String, Object> params, HttpServletRequest request) throws  Exception {
        log.debug("【数据服务系统】presto继续翻页查询：sql：{},param:{}",sql,params);
        Integer page = params.get("page") != null ? Integer.parseInt(params.get("page") + "") : 0;
        Integer size = params.get("size") != null ? Integer.parseInt(params.get("size") + "") : 0;
        Statement attribute = StatementUtils.getStatement(request,DBDataUtils.getPrestoQueryKey(dataSource));
        ResultSet resultSet = attribute.getResultSet();
        log.debug("【数据服务系统】presto继续翻页查询:resultSet:{}",resultSet);
        return prestoDataHandler.transQueryData(resultSet,page,size);
    }

    @Override
    public List<LinkedHashMap<String, Object>> executeQuery(String dataSource, String sql, Map<String, Object> params, HttpServletRequest request) throws Exception {
        List<LinkedHashMap<String, Object>> result = null;
        DruidPooledConnection connection = null;

        Integer page = params.get("page") != null ? Integer.parseInt(params.get("page") + "") : 0;
        Integer size = params.get("size") != null ? Integer.parseInt(params.get("size") + "") : 0;
        DruidDataSource druidDataSource = (DruidDataSource) allDs.get(dataSource);
        connection = druidDataSource.getConnection();
        final Statement ps = connection.createStatement();
        Future<PrestoQueryResult> submit = executorService.submit(new Callable<PrestoQueryResult>() {
            @Override
            public PrestoQueryResult call() {
                PrestoQueryResult queryResult;
                try {
                    queryResult = PrestoQueryResult.getInstance(ps.execute(sql));
                } catch (SQLException e) {
                    log.error("【数据查询服务】查询出错了！name:{},sql:{}", dataSource, sql,e);
                    try {
                        ps.close();
                    } catch (SQLException w) {
                        log.error("【数据查询服务】查询出错了！name:{},sql:{}", dataSource, sql,e);
                    }
                    queryResult = PrestoQueryResult.getInstance(false);
                    queryResult.setE(e);
                }
                return queryResult;
            }
        });
        //处理session中的statement
        StatementUtils.updatePs(request,DBDataUtils.getPrestoQueryKey(dataSource),ps);

        Thread.sleep(1000);

        PrestoQueryResult queryResult = submit.get();

        if(queryResult.getIsSuccess()){
            ResultSet resultSet = ps.getResultSet();
            result = prestoDataHandler.transQueryData(resultSet,page,size);
        }else{
            throw new DbmsServerEception(DbmsServerEnums.SQL_HANDLER_EXCEPTION,queryResult.getE());
        }


        return result;
    }

    @Override
    public List<LinkedHashMap<String, Object>> executeQuery(String dataSource, String sql, Map<String, Object> params) throws Exception {
        List<LinkedHashMap<String, Object>> result;
        DruidPooledConnection connection ;
        PrestoQueryResult queryResult;

        DruidDataSource druidDataSource = (DruidDataSource) allDs.get(dataSource);
        connection = druidDataSource.getConnection();
        final Statement ps = connection.createStatement();
        queryResult = PrestoQueryResult.getInstance(ps.execute(sql));

        if(queryResult.getIsSuccess()){
            ResultSet resultSet = ps.getResultSet();
            //默认500条数据
            try {
                result = prestoDataHandler.transQueryData(resultSet,1,500);
                resultSet.close();
                ps.close();
                connection.close();
            }catch (Exception e){
                resultSet.close();
                ps.close();
                connection.close();
                throw new DbmsServerEception(DbmsServerEnums.SQL_HANDLER_EXCEPTION,e);
            }
        }else{
            ps.close();
            connection.close();
            throw new DbmsServerEception(DbmsServerEnums.SQL_HANDLER_EXCEPTION,queryResult.getE());
        }


        return result;
    }

    @Data
    public static class PrestoQueryResult{
        private Boolean isSuccess;
        private Exception e;

        private PrestoQueryResult(Boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

         public static PrestoQueryResult getInstance(Boolean isSuccess){
            return new PrestoQueryResult(isSuccess);
        }

    }
}
