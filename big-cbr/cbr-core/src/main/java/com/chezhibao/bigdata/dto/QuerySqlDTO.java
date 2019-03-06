package com.chezhibao.bigdata.dto;

import com.chezhibao.bigdata.bo.QuerySqlBO;
import com.chezhibao.bigdata.pojo.CBR;
import com.chezhibao.bigdata.pojo.QuerySql;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/11.
 */
@Slf4j
public class QuerySqlDTO {
    /**
     * 将前端sql对象转换成后端的对象
     *
     * @param cbr
     * @return
     */
    public static List<QuerySqlBO> transCbr2QuerySqlBO(CBR cbr) {
        return transCbr2QuerySqlBO(cbr.getRealreportId(),cbr.getQuerySql());
    }
    public static List<QuerySqlBO> transCbr2QuerySqlBO(String realreportId,List<QuerySql> querySqls) {
        List<QuerySqlBO> querySqlBOS = new ArrayList<>();
        QuerySqlBO querySqlBO;
        for (int i = 0; i < querySqls.size(); i++) {
            querySqlBO = new QuerySqlBO();
            BeanUtils.copyProperties(querySqls.get(i), querySqlBO);
            querySqlBO.setRealreportId(realreportId);
            querySqlBO.setOrder(i);
            try {
                String encode = URLEncoder.encode(querySqlBO.getSql(), "UTF-8");
                querySqlBO.setSql(encode);
            } catch (Exception e) {
                log.error("【CBR】字符转换出错了",e);
            }
            querySqlBOS.add(querySqlBO);
        }
        return querySqlBOS;
    }
    /**
     * 将后端对象转换成前端对象
     *
     * @param querySqlBOS
     * @return
     */
    public static List<QuerySql> transQuerySqlBOToQuerySql(List<QuerySqlBO> querySqlBOS) {
        List<QuerySql> querySqls = new ArrayList<>();
        QuerySql querySql;
        for (QuerySqlBO querySqlBO : querySqlBOS) {
            querySql = transQuerySqlBOToQuerySql(querySqlBO);
            querySqls.add(querySql);
        }
        return querySqls;
    }

    /**
     * 将后端对象转换成前端对象
     *
     * @param querySqlBO
     * @return
     */
    public static QuerySql transQuerySqlBOToQuerySql(QuerySqlBO querySqlBO) {
        QuerySql querySql = new QuerySql();
        BeanUtils.copyProperties(querySqlBO, querySql);
        //解码数据
        try {
            String decode = URLDecoder.decode(querySql.getSql(), "UTF-8");
            querySql.setSql(decode);
        } catch (Exception e) {
            log.error("【】字符转换出错了",e);
        }
        return querySql;
    }
}
