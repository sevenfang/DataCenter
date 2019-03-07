package com.chezhibao.bigdata.dbms.server.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author WangCongJun
 *
 * Created by WangCongJun on 2018/9/7.
 */
@Component
@Slf4j
public class PrestoDataHandler implements  DataHandler {

    private Desensitization<LinkedHashMap<String,Object>> desensitization;

    public PrestoDataHandler(Desensitization<LinkedHashMap<String, Object>> desensitization) {
        this.desensitization = desensitization;
    }

    @Override
    public List<LinkedHashMap<String, Object>> transQueryData(ResultSet resultSet, Integer page, Integer size) {
        int offset = 1;
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        if(resultSet==null){
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
                // 脱敏处理
                col = desensitization.desensitization(col);
                result.add(col);

                if(offset==size){
                    break;
                }
                offset++;
            }
        }catch (SQLException e){
            log.error("【数据查询服务】查询出错了！",e);
        }
        return result;
    }
}
