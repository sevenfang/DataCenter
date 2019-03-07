package com.chezhibao.bigdata.dbms.server.service;

import com.chezhibao.bigdata.dbms.server.vo.DbSqlExecuteVO;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 数据操作服务
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/23.
 */
public interface StatementExecuteService {
    /**
     * 执行的数据操作语句
     * @return
     */
    List<LinkedHashMap<String,Object>> execute(DbSqlExecuteVO dbSqlExecuteVO, HttpServletRequest request)throws Exception;

    /**
     * 执行mysql类的数据sql
     * @param name
     * @param executeStatement
     * @return
     */
    List<LinkedHashMap<String,Object>> mysql(String name, String executeStatement, HttpServletRequest request, HashMap<String, Object> params);
    List<LinkedHashMap<String,Object>> presto(String name, String executeStatement, HttpServletRequest request, HashMap<String, Object> params) throws Exception;

    Boolean stopStatement(DbSqlExecuteVO dbSqlExecuteVO,HttpServletRequest request);
}
