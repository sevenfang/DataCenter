package com.chezhibao.bigdata.dbms.server.service.impl;

import com.chezhibao.bigdata.dbms.server.RequestHelper;
import com.chezhibao.bigdata.dbms.server.auth.service.SqlVerificationService;
import com.chezhibao.bigdata.dbms.server.dao.PrestoSatementDao;
import com.chezhibao.bigdata.dbms.server.dao.SqlStatementDao;
import com.chezhibao.bigdata.dbms.server.service.StatementExecuteService;
import com.chezhibao.bigdata.dbms.server.utils.DBDataUtils;
import com.chezhibao.bigdata.dbms.server.vo.DbSqlExecuteVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/25.
 */
@Service
@Slf4j
public class StatementExecuteServiceImpl implements StatementExecuteService {

    private static final String SELECT = "SELECT";
    private static final String DELETE = "DELETE";
    private static final String INSERT = "INSERT";

    private SqlStatementDao commonDao;
    private PrestoSatementDao prestoSatementDao;
    private SqlVerificationService sqlVerificationService;


    public StatementExecuteServiceImpl(SqlStatementDao commonDao,
                                       SqlVerificationService sqlVerificationService,
                                       PrestoSatementDao prestoSatementDao) {
        this.commonDao = commonDao;
        this.sqlVerificationService = sqlVerificationService;
        this.prestoSatementDao = prestoSatementDao;
    }

    @Override
    public List<LinkedHashMap<String, Object>> execute(DbSqlExecuteVO executeVO,HttpServletRequest request) throws Exception {
        sqlVerificationService.verifySql(executeVO,RequestHelper.getAndVerifyUserId(request));
        List<LinkedHashMap<String, Object>> result = null;
        Integer dbType = executeVO.getDbType();
        String name = executeVO.getName();
        String sql = executeVO.getSql();
        Integer page = executeVO.getPage();
        Integer size = executeVO.getSize();
        HashMap<String,Object> params = new HashMap<>();
        if(!ObjectUtils.isEmpty(page)&&!ObjectUtils.isEmpty(size)){
            params.put("page",page);
            params.put("size",size);
        }
        switch (dbType) {
            case 1:
                result = mysql(name, sql,request,params);
                break;
            case 4:
                Boolean selectNext = executeVO.getSelectNext();
                if(selectNext){
                    result = prestoSatementDao.selectNext(name, sql,params,request);
                }else {
                    result = presto(name, sql,request,params);
                }

                break;
            default:
        }


        return result;
    }

    @Override
    public List<LinkedHashMap<String, Object>> mysql(String name, String sql,HttpServletRequest request,HashMap<String,Object> params ) {
        List<LinkedHashMap<String, Object>> result = null;
        sql=sql.trim();
        String s = sql.split("\\s")[0].toUpperCase();
        switch (s) {
            case SELECT:
                result = commonDao.select(name, sql, params,request);
                break;
            case DELETE:
                commonDao.delete(name, sql, params);
                break;
            case INSERT:
                commonDao.insert(name, sql, params);
                break;
            default:
                commonDao.update(name, sql, params);
        }
        return result;
    }


    @Override
    public List<LinkedHashMap<String, Object>> presto(String name, String sql, HttpServletRequest request, HashMap<String, Object> params) throws Exception{
        List<LinkedHashMap<String, Object>> result = null;
        sql=sql.trim();
        String s = sql.split("\\s")[0].toUpperCase();
        switch (s) {
            case SELECT:
                result = prestoSatementDao.executeQuery(name, sql, params);
                break;
            case DELETE:
                commonDao.delete(name, sql, params);
                break;
            case INSERT:
                commonDao.insert(name, sql, params);
                break;
            default:
                commonDao.update(name, sql, params);
        }
        return result;
    }

    @Override
    public Boolean stopStatement(DbSqlExecuteVO dbSqlExecuteVO,HttpServletRequest request) {
        String psKey = DBDataUtils.getPsKey(dbSqlExecuteVO.getName());
        return commonDao.stopStatement(psKey,request);
    }
}
