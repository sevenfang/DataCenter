package com.chezhibao.bigdata.jdbctemplate.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.annotation.DS;
import com.chezhibao.bigdata.dao.jdbctemplate.NewWareHouseTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/17.
 */
@Service(interfaceClass = NewWareHouseTemplate.class)
@Component
public class NewWareHouseTemplateImpl implements NewWareHouseTemplate {
    private static final String ds = "new_warehouse";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public NewWareHouseTemplateImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @DS(ds)
    public List<Map<String, Object>> simpleQueryForList(String sql, Map<String, Object> params) {
        return jdbcTemplate.queryForList(sql,params);
    }
}
