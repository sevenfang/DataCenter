package com.chezhibao.bigdata.dbms.server.dao;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author WangCongJun
 * @date 2018/4/25
 * Created by WangCongJun on 2018/4/25.
 */
@Slf4j
public class SqlProvider {

    private final Pattern inPattern = Pattern.compile("\\(#\\{(.+?)\\}\\)");
    public String insert(@Param("sql") String sql,@Param("params")Map<String,Object> params){
        return hadlerIn(sql,params);
    }
    public String delete(@Param("sql") String sql,@Param("params")Map<String,Object> params){
        return hadlerIn(sql,params);
    }
    public String update(@Param("sql") String sql,@Param("params")Map<String,Object> params){
        return hadlerIn(sql,params);
    }
    public String select(@Param("sql") String sql,@Param("params")Map<String,Object> params){

        return hadlerIn(sql,params);
    }

    private  String hadlerIn(String sql,Map<String,Object> params) {

        Matcher matcher = inPattern.matcher(sql);
        while (matcher.find()) {
            StringBuilder sb = new StringBuilder("(");
            String group = matcher.group(1);
            String[] split = group.split("\\.");
            String param = split[split.length-1];
            Object o = params.get(param);
            if(o instanceof Collection){
                Collection c = (Collection)o;
                for(int i = 0;i<c.size();i++) {
                    if(i!=0){
                        sb.append(",");
                    }
                    sb.append("#{").append(group).append("[").append(i).append("]").append("}");
                }
                sb.append(")");
                log.debug("【SQL处理】{}集合处理in语句{}",group,sb.toString());
                sql = sql.replace("(#{"+group+"})",sb.toString());
            }else if(o.getClass().isArray()){
                Object[] a = (Object[])o;
                for(int i = 0;i<a.length;i++) {
                    if(i!=0){
                        sb.append(",");
                    }
                    sb.append("#{").append(group).append("[").append(i).append("]").append("}");
                }
                sb.append(")");
                log.debug("【SQL处理】{}数组处理in语句{}",group,sb.toString());
                //替换占位符
                sql = sql.replace("(#{"+group+"})",sb.toString());
            }


        }
        return sql;
    }
}
