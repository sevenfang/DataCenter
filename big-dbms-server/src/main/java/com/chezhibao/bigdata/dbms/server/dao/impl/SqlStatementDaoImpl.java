package com.chezhibao.bigdata.dbms.server.dao.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.pool.DruidPooledPreparedStatement;
import com.chezhibao.bigdata.dbms.server.constants.SessionConstants;
import com.chezhibao.bigdata.dbms.server.dao.SqlStatementDao;
import com.chezhibao.bigdata.dbms.server.enums.DbmsServerEnums;
import com.chezhibao.bigdata.dbms.server.exception.DbmsServerEception;
import com.chezhibao.bigdata.dbms.server.utils.DBDataUtils;
import com.chezhibao.bigdata.dbms.server.utils.StatementUtils;
import com.mysql.jdbc.exceptions.MySQLStatementCancelledException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/4.
 */
@Repository
@Slf4j
public class SqlStatementDaoImpl implements SqlStatementDao {

    @Resource(name="AllDs")
    private HashMap<String,Object> allDs;
//    private ThreadLocal<HashMap<String,Statement>> statements=new ThreadLocal<>();


    @Override
    public Boolean insert(String dataSource, String sql, Map<String, Object> params) {
        throw new DbmsServerEception(DbmsServerEnums.INSERT_NOT_IMPLEMENTED_EXCEPTION);
    }

    @Override
    public void delete(String dataSource, String sql, Map<String, Object> params) {
        throw new DbmsServerEception(DbmsServerEnums.DELETE_NOT_IMPLEMENTED_EXCEPTION);
    }

    @Override
    public void update(String dataSource, String sql, Map<String, Object> params) {
        throw new DbmsServerEception(DbmsServerEnums.UPDATE_NOT_IMPLEMENTED_EXCEPTION);
    }

    @Override
    public List<LinkedHashMap<String, Object>> select(String dataSource, String sql, Map<String, Object> params, HttpServletRequest request) {
        List<LinkedHashMap<String, Object>> result=null;
        DruidPooledConnection connection=null;
        PreparedStatement ps=null;
        Integer page = params.get("page")!=null?Integer.parseInt(params.get("page")+""):0;
        Integer size = params.get("size")!=null?Integer.parseInt(params.get("size")+""):0;
        try {
            DruidDataSource druidDataSource = (DruidDataSource) allDs.get(dataSource);
            connection = druidDataSource.getConnection();
            ps = connection.prepareStatement(sql);
            if(page!=0&&size!=0) {
                ps.setMaxRows(page * size);
            }
            StatementUtils.updatePs(request,DBDataUtils.getPsKey(dataSource),ps);
            ResultSet resultSet=null;

            try {
                 resultSet = ps.executeQuery();
                if(page!=0&&size!=0) {
                    resultSet.relative((page - 1) * size);
                }
            }catch (MySQLStatementCancelledException e){
                log.error("【数据查询服务】用户停止了查询！！",e);
            }catch (Exception e){
                log.error("【数据查询服务】用户停止了查询！！",e);
                throw new DbmsServerEception(DbmsServerEnums.SQL_HANDLER_EXCEPTION,e);
            }
            result = DBDataUtils.transQueryData(resultSet);
        }catch (SQLException e){
            log.error("【数据查询服务】查询出错了！name:{},sql:{}",dataSource,sql);
        }finally {
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
        }
        return result;
    }

    @Override
    public Boolean stopStatement(String psId,HttpServletRequest request) {
        DruidPooledPreparedStatement statement = (DruidPooledPreparedStatement)request.getSession().getAttribute(psId);
        if(ObjectUtils.isEmpty(statement)){
            return false;
        }
        try {
            statement.cancel();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            throw new DbmsServerEception(DbmsServerEnums.SQL_HANDLER_EXCEPTION,e);
        }
    }
}
