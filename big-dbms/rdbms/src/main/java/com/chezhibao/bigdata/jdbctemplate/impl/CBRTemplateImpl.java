package com.chezhibao.bigdata.jdbctemplate.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.annotation.DS;
import com.chezhibao.bigdata.dao.jdbctemplate.CBRTemplate;
import com.chezhibao.bigdata.exception.DbmsException;
import com.chezhibao.bigdata.jdbctemplate.BaseJdbcTemplate;
import com.chezhibao.bigdata.util.SqlVerifyUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/20.
 */
@Service(interfaceClass = CBRTemplate.class)
@Repository
public class CBRTemplateImpl implements CBRTemplate {

    private static final String ds = "cbr";

    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final Integer LIMIT = 1000;

    public CBRTemplateImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @DS(ds)
    public List<Map<String, Object>> simpleQueryForList(String sql, Map<String, Object> params) {
        Boolean legalSql = SqlVerifyUtils.isLegalSql(sql, LIMIT, params);
        if(!legalSql){
            throw new DbmsException(2001,"查询sql不合法！不可包含group by 、join 并且 一次返回的条数不能大于："+ LIMIT);
        }
        return jdbcTemplate.queryForList(sql,params);
    }
}
