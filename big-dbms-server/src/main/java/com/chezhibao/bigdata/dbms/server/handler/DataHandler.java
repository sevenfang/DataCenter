package com.chezhibao.bigdata.dbms.server.handler;

import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/7.
 */
public interface DataHandler {
    /**
     * 将查询的到的结果转换成需要的格式
     * @param resultSet
     * @param page
     * @param size
     * @return
     */
    public List<LinkedHashMap<String, Object>> transQueryData(ResultSet resultSet, Integer page, Integer size);

}
