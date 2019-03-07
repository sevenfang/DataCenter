package com.chezhibao.bigdata.dbms.server.controller;

import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.dbms.server.constants.DbTypeConstants;
import com.chezhibao.bigdata.dbms.server.handler.SqlHandler;
import com.chezhibao.bigdata.dbms.server.service.StatementExecuteService;
import com.chezhibao.bigdata.dbms.server.vo.DbSqlExecuteVO;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/25.
 */
@RestController
@RequestMapping("/dbms/server/execute")
public class StatementExecuteController {


    private StatementExecuteService statementExecuteService;


    public StatementExecuteController(StatementExecuteService statementExecuteService) {
        this.statementExecuteService = statementExecuteService;
    }

    @RequestMapping("/data")
    public BigdataResult executeMysql(@RequestBody DbSqlExecuteVO dbSqlExecuteVO,HttpServletRequest request){
        try {
            List<LinkedHashMap<String, Object>> mysql = statementExecuteService.execute(dbSqlExecuteVO, request);
            return BigdataResult.ok(mysql);
        }catch (Exception e){
            return BigdataResult.build(4003,e.getMessage());
        }

    }
    @RequestMapping("/count")
    public BigdataResult executeMysqlCount(@RequestBody DbSqlExecuteVO dbSqlExecuteVO,HttpServletRequest request) throws Exception{
        Integer count;
        String sql = dbSqlExecuteVO.getSql();
        //判断是否是查询语句
        String s = sql.trim().split(" ")[0].toUpperCase();
        if(!s.startsWith("SELECT")){
            return BigdataResult.ok(1);
        }
        Integer dbType = dbSqlExecuteVO.getDbType();
        //如果是presto则使用mysql的数量查询方法
        if(dbType==DbTypeConstants.Presto){
            dbSqlExecuteVO.setDbType(1);
        }

        String queryCountSql = SqlHandler.getCountSql(sql);
        dbSqlExecuteVO.setSql(queryCountSql);
        List<LinkedHashMap<String, Object>> counts = statementExecuteService.execute(dbSqlExecuteVO, request);
        if(!ObjectUtils.isEmpty(counts)) {
            Object resultCount = counts.get(0).get("resultCount");
            count=resultCount!=null?Integer.parseInt(resultCount+""):0;
        }else {
            return BigdataResult.ok();
        }
        return BigdataResult.ok(count);
    }
    @RequestMapping("/stop")
    public BigdataResult stopStatement(@RequestBody DbSqlExecuteVO dbSqlExecuteVO,HttpServletRequest request){
        Boolean aBoolean = statementExecuteService.stopStatement(dbSqlExecuteVO,request);
        if(aBoolean){
            return BigdataResult.ok();
        }else{
            return BigdataResult.build(3002,"停止失败！");
        }
    }
}
