package com.chezhibao.bigdata.vo;

import com.chezhibao.bigdata.pojo.QuerySql;
import lombok.Data;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/13.
 */
@Data
public class QuerySqlVo {
    private List<QuerySql> value;
    private List<Integer> delSqlIndex;
}
