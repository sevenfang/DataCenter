package com.chezhibao.bigdata.source;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author WangCongJun
 * @date 2018/5/4
 * Created by WangCongJun on 2018/5/4.
 */
public class StaticSqlSource implements SqlSource {
    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Configuration configuration;
    private SqlSegEntry[] sqlCache;
    private List<ParameterMapping> parameterMappingsForIn;

    public StaticSqlSource(Configuration configuration, String sql) {
        this(configuration, sql, null);
    }

    public StaticSqlSource(Configuration configuration, String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.configuration = configuration;
        if (parameterMappings != null && parameterMappings.size() > 0) {
            this.sqlCache = cache(sql);
            if (sqlCache != null) {
                final int LEN = parameterMappings.size();
                HashSet<Integer> indexSet = new HashSet<>();
                for (SqlSegEntry e : sqlCache) {
                    if (e.index >= 0) {
                        indexSet.add(e.index);
                    }
                }
                ArrayList<ParameterMapping> paramMappings = new ArrayList<>(LEN);
                for (int i = 0; i < LEN; i++) {
                    if (!indexSet.contains(i)) {
                        paramMappings.add(parameterMappings.get(i));
                    }
                }
                parameterMappingsForIn = paramMappings;
            }
        }
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        String sql = this.sql;
        List<ParameterMapping> parameterMappings = this.parameterMappings;
        if (sqlCache != null && (parameterObject instanceof Map)) {
            Map<?, ?> argMap = (Map<?, ?>) parameterObject;
            StringBuilder sqlBuilder = new StringBuilder(sql.length() + 128);
            for (SqlSegEntry e : sqlCache) {
                sqlBuilder.append(e.segment);
                if (e.index >= 0) {
                    ParameterMapping m = parameterMappings.get(e.index);
                    Object obj = argMap.get(m.getProperty());
                    appendParameter(sqlBuilder, obj);
                }
            }
            sql = sqlBuilder.toString();
            parameterMappings = this.parameterMappingsForIn;
        }
        return new BoundSql(configuration, sql, parameterMappings, parameterObject);
    }

    private void appendParameter(StringBuilder sb, Object arrObj) {
        Class<?> clazz = arrObj.getClass();
        final char STR = '\'';
        if (clazz.isArray()) {
            sb.append('(');
            int len = Array.getLength(arrObj);
            if (len > 0) {
                Class<?> eClass = clazz.getComponentType();
                if (eClass.isPrimitive()) {
                    sb.append(Array.get(arrObj, 0));
                    for (int i = 1; i < len; i++) {
                        sb.append(',').append(Array.get(arrObj, i));
                    }
                } else {
                    Object[] args = (Object[]) arrObj;
                    if (CharSequence.class.isAssignableFrom(eClass)) {
                        sb.append(STR).append(args[0]).append(STR);
                        for (int i = 1; i < len; i++) {
                            sb.append(',').append(STR).append(args[i]).append(STR);
                        }
                    } else {
                        sb.append(args[0]);
                        for (int i = 1; i < len; i++) {
                            sb.append(',').append(args[i]);
                        }
                    }
                }
            }
            sb.append(')');
        } else if (Collection.class.isAssignableFrom(clazz)) {
            sb.append('(');
            Collection<?> col = (Collection<?>) arrObj;
            Iterator<?> i = col.iterator();
            if (i.hasNext()) {
                boolean isString = false;
                Object eObj = i.next();
                isString = eObj instanceof CharSequence;
                if (isString) {
                    sb.append(STR).append(eObj).append(STR);
                    while (i.hasNext()) {
                        sb.append(',').append(STR).append(i.next()).append(STR);
                    }
                } else {
                    sb.append(eObj);
                    while (i.hasNext()) {
                        sb.append(',').append(i.next());
                    }
                }
            }
            sb.append(')');
        }
    }

    private static SqlSegEntry[] cache(String sql) {
        String[] segs = sql.split("[?]");
        int countIn = 0;
        ArrayList<SqlSegEntry> list = new ArrayList<>(segs.length);
        StringBuilder sb = new StringBuilder(sql.length());
        for (int i = 0; i < segs.length; i++) {
            String s = segs[i], ts = s.trim().toLowerCase(), tm = ts;
            if (ts.endsWith("(")) {
                tm = ts.substring(0, ts.length() - 1).trim();
            }
            if (tm.endsWith(" in")) {
                int st = 0;
                if (ts.charAt(0) == ')') {
                    st = s.indexOf(')') + 1;
                }
                if (tm.length() != ts.length()) {
                    s = s.substring(st, s.lastIndexOf('('));
                } else if (st > 0) {
                    s = s.substring(st);
                }
                sb.append(s);
                SqlSegEntry e = new SqlSegEntry();
                e.index = i;
                e.segment = sb.toString();
                list.add(e);
                sb.delete(0, sb.length());
                countIn++;
            } else {
                if (ts.charAt(0) == ')') {
                    s = s.substring(s.indexOf(')') + 1);
                }
                sb.append(s);
                if (i < segs.length - 1) {
                    sb.append('?');
                }
            }
        }
        if (countIn == 0) {
            return null;
        }
        if (sb.length() > 0) {
            SqlSegEntry e = new SqlSegEntry();
            e.segment = sb.toString();
            list.add(e);
        }
        sb = null;
        return list.toArray(new SqlSegEntry[list.size()]);
    }

    // --------
    private static class SqlSegEntry {
        String segment;
        int index = -1;
    }
}
