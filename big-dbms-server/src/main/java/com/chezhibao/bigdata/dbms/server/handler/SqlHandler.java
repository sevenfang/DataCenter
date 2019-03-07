package com.chezhibao.bigdata.dbms.server.handler;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/5.
 */
public class SqlHandler {

    public static String getCountSql(String sql){
        return "select COUNT(1) resultCount from ("+sql+" )t_tmp_dbms";
    }

    public static String getLimitSql(String sql,Integer page,Integer size){
        return "select * from ("+sql+" )t limit "+ page+","+size;
    }
}
