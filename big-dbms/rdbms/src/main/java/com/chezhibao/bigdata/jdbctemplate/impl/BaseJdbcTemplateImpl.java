package com.chezhibao.bigdata.jdbctemplate.impl;

import com.chezhibao.bigdata.datasource.DataSourceContextHolder;
import com.chezhibao.bigdata.dbms.common.LoggerUtil;
import com.chezhibao.bigdata.jdbctemplate.BaseJdbcTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/26.
 */
@Repository
public class BaseJdbcTemplateImpl implements BaseJdbcTemplate {

    private static Logger log = LoggerUtil.RDBMS_LOG;

    private NamedParameterJdbcTemplate jdbcTemplate;

    public BaseJdbcTemplateImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T execute(String ds, String sql, SqlParameterSource paramSource, PreparedStatementCallback<T> action) throws DataAccessException {
        return null;
    }

    @Override
    public <T> T execute(String ds, String sql, Map<String, ?> paramMap, PreparedStatementCallback<T> action) throws DataAccessException {
        return null;
    }

    @Override
    public <T> T execute(String ds, String sql, PreparedStatementCallback<T> action) throws DataAccessException {
        return null;
    }

    @Override
    public <T> T query(String ds, String sql, SqlParameterSource paramSource, ResultSetExtractor<T> rse) throws DataAccessException {
        return null;
    }

    @Override
    public <T> T query(String ds, String sql, Map<String, ?> paramMap, ResultSetExtractor<T> rse) throws DataAccessException {
        return null;
    }

    @Override
    public <T> T query(String ds, String sql, ResultSetExtractor<T> rse) throws DataAccessException {
        return null;
    }

    @Override
    public void query(String ds, String sql, SqlParameterSource paramSource, RowCallbackHandler rch) throws DataAccessException {

    }

    @Override
    public void query(String ds, String sql, Map<String, ?> paramMap, RowCallbackHandler rch) throws DataAccessException {

    }

    @Override
    public void query(String ds, String sql, RowCallbackHandler rch) throws DataAccessException {

    }

    @Override
    public <T> List<T> query(String ds, String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper) throws DataAccessException {
        return null;
    }

    @Override
    public <T> List<T> query(String ds, String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper) throws DataAccessException {
        return null;
    }

    @Override
    public <T> List<T> query(String ds, String sql, RowMapper<T> rowMapper) throws DataAccessException {
        return null;
    }

    @Override
    public <T> T queryForObject(String ds, String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper) throws DataAccessException {
        return null;
    }

    @Override
    public <T> T queryForObject(String ds, String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper) throws DataAccessException {
        return null;
    }

    @Override
    public <T> T queryForObject(String ds, String sql, SqlParameterSource paramSource, Class<T> requiredType) throws DataAccessException {
        return null;
    }

    @Override
    public <T> T queryForObject(String ds, String sql, Map<String, ?> paramMap, Class<T> requiredType) throws DataAccessException {
        return null;
    }

    @Override
    public Map<String, Object> queryForMap(String ds, String sql, SqlParameterSource paramSource) throws DataAccessException {
        return null;
    }

    @Override
    public Map<String, Object> queryForMap(String ds, String sql, Map<String, ?> paramMap) throws DataAccessException {
        return null;
    }

    @Override
    public <T> List<T> queryForList(String ds, String sql, SqlParameterSource paramSource, Class<T> elementType) throws DataAccessException {
        return null;
    }

    @Override
    public <T> List<T> queryForList(String ds, String sql, Map<String, ?> paramMap, Class<T> elementType) throws DataAccessException {
        DataSourceContextHolder.setDB(ds);
        List<T> list = jdbcTemplate.queryForList(sql, paramMap, elementType);
        DataSourceContextHolder.clearDB();
        return list;
    }

    @Override
    public List<Map<String, Object>> queryForList(String ds, String sql, SqlParameterSource paramSource) throws DataAccessException {
        return null;
    }

    @Override
    public List<Map<String, Object>> queryForList(String ds, String sql, Map<String, ?> paramMap) throws DataAccessException {
        DataSourceContextHolder.setDB(ds);
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, paramMap);
        DataSourceContextHolder.clearDB();
        return list;
    }

    @Override
    public SqlRowSet queryForRowSet(String ds, String sql, SqlParameterSource paramSource) throws DataAccessException {
        return null;
    }

    @Override
    public SqlRowSet queryForRowSet(String ds, String sql, Map<String, ?> paramMap) throws DataAccessException {
        return null;
    }

    @Override
    public int update(String ds, String sql, SqlParameterSource paramSource) throws DataAccessException {
        return 0;
    }

    @Override
    public int update(String ds, String sql, Map<String, ?> paramMap) throws DataAccessException {
        return 0;
    }

    @Override
    public int update(String ds, String sql, SqlParameterSource paramSource, KeyHolder generatedKeyHolder) throws DataAccessException {
        return 0;
    }

    @Override
    public int update(String ds, String sql, SqlParameterSource paramSource, KeyHolder generatedKeyHolder, String[] keyColumnNames) throws DataAccessException {
        return 0;
    }

    @Override
    public int[] batchUpdate(String ds, String sql, Map<String, ?>[] batchValues) {
        return new int[0];
    }

    @Override
    public int[] batchUpdate(String ds, String sql, SqlParameterSource[] batchArgs) {
        return new int[0];
    }
}
