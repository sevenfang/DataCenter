package com.chezhibao.bigdata.dbms.server.utils;

import com.chezhibao.bigdata.dbms.server.constants.SessionConstants;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/4.
 */
@Slf4j
public class DBDataUtils {
    public static List<LinkedHashMap<String, Object>> transQueryData(ResultSet resultSet) {
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        if (resultSet == null) {
            return result;
        }
        LinkedHashMap<String, Object> col;
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                col = new LinkedHashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    col.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
                result.add(col);
            }
        } catch (SQLException e) {
            log.error("【数据查询服务】查询出错了！",e);
        }
        return result;
    }

    public static String getPsKey(String ds) {
        return SessionConstants.STATEMENT + "-" + ds;
    }

    public static String getPrestoQueryKey(String ds) {
        return SessionConstants.STATEMENT + "-Query-" + ds;
    }

    public static String encode(String data){
        try {
            return  URLEncoder.encode(data, "UTF-8");
        }catch (UnsupportedEncodingException e){
            log.error("【数据查询服务】编码数据出错了！！{}",data);
        }
        return data;
    }
    public static String decode(String data){
        try {
            return  URLDecoder.decode(data, "UTF-8");
        }catch (UnsupportedEncodingException e){
            log.error("【数据查询服务】解码数据出错了！！{}",data);
        }
        return data;
    }
}
