package com.chezhibao.bigdata.dbms.server.auth.service.impl;

import com.chezhibao.bigdata.dbms.server.auth.bo.DbAuthBO;
import com.chezhibao.bigdata.dbms.server.auth.service.AuthenticationService;
import com.chezhibao.bigdata.dbms.server.auth.service.SqlVerificationService;
import com.chezhibao.bigdata.dbms.server.vo.DbSqlExecuteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/16.
 */
@Service
public class SqlVerificationServiceImpl implements SqlVerificationService {

    private static final Pattern FROM=Pattern.compile("from\\s+?(.*?)[\\s|)]+?\\S*",Pattern.CASE_INSENSITIVE);
    private static final Pattern JOIN=Pattern.compile("join\\s+?(.*?)\\s+?\\S*",Pattern.CASE_INSENSITIVE);

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public void verifySql(DbSqlExecuteVO executeVO,Integer userId) {
        //校验sql
        DbAuthBO dbAuthBO = new DbAuthBO();
        dbAuthBO.setUserId(userId);
        dbAuthBO.setSql(executeVO.getSql());
        dbAuthBO.setDatasource(executeVO.getName());
        Boolean aBoolean = verifySql(dbAuthBO);
        if(!aBoolean){
           throw new RuntimeException("权限不足！！");
        }
    }

    @Override
    public Boolean verifySql(DbAuthBO dbAuthBO) {
        Boolean administrator = authenticationService.isAdministrator(dbAuthBO.getUserId());
        if (administrator){
            return true;
        }
        List<String> dbInstance = authenticationService.getDbInstance(dbAuthBO);
        if(!dbInstance.contains(dbAuthBO.getDatasource())){
            return false;
        }
        //获取sql中的schema，获取sql中的table
        String sql = dbAuthBO.getSql();
        //处理下sql 防止sql末端没有字符
        if(sql.endsWith(";")){
            sql=sql.substring(sql.length()-1);
        }else{
            sql+=" ";
        }
        Map<String, List<String>> schemaAndTable = getSchemaAndTable(sql+" ",FROM,JOIN);
        //TODO 处理default
        List<String> strings = schemaAndTable.get("default");
        schemaAndTable.remove("default");
        schemaAndTable.put(getDefaultSchema(dbAuthBO.getDatasource()),strings);
        //校验schema
        List<String> schema = authenticationService.getSchema(dbAuthBO);
        if(!schema.containsAll(schemaAndTable.keySet())){
            return false;
        }
        //TODO 校验table
        return true;
    }
    private Map<String,List<String>> getSchemaAndTable(String sql,Pattern... patterns){
        Map<String,List<String>> result = new HashMap<>();
        for(Pattern pattern :patterns){
            getSchemaAndTable(sql,pattern,result);
        }
        return result;
    }
    private void getSchemaAndTable(String sql,Pattern pattern,Map<String,List<String>> result){
        Matcher matcher = pattern.matcher(sql);
        String g;
        while (matcher.find()){
            int i = matcher.groupCount();
            for (int j = 1;j<=i;j++){
               g =  matcher.group(j).trim();
                String schema ;
                String table ;
               if(g.contains(".")){
                   String[] split = g.split("\\.");
                   schema = split[0];
                   table = split[1];
                   //校验表是否合规
//                   verifyTable(table);
               }else {
                   schema="default";
                   table = g;
                   //校验表是否合规
//                   verifyTable(table);
               }
                if(!result.containsKey(schema)){
                    result.put(schema,new ArrayList<>());
                }
                result.get(schema).add(table);
            }
        }
    }

    /**
     * 校验表的规则  此规则的适用范围是全部用户
     * @param table
     */
    private void verifyTable(String table){
        if(table.endsWith("_cre")){
            throw new RuntimeException(String.format("无权查看%s",table));
        }
    }
    /**
     * 数据源中的默认schema
     * @param datasource
     * @return
     */
    private String getDefaultSchema(String datasource){
        switch (datasource){
            case "presto":
                return "da";
                default:
                    return null;
        }
    }
}
