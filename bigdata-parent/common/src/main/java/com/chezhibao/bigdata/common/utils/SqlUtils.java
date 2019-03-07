package com.chezhibao.bigdata.common.utils;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author WangCongJun
 * @date 2018/5/4
 * Created by WangCongJun on 2018/5/4.
 */
public class SqlUtils {

    public static String toSqlString(Object obj){
        StringBuilder sql = new StringBuilder();
        if(obj instanceof Collection){
            Collection c = (Collection)obj;
            Iterator it = c.iterator();
            while(it.hasNext()){
                sql.append(",").append(it.next());
            }
        }
        if(sql.length()>0) {
            return sql.substring(1);
        }
        return null;
    }
}
