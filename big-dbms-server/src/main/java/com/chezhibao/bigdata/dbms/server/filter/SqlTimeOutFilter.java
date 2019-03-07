package com.chezhibao.bigdata.dbms.server.filter;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.StatementProxy;

import java.sql.SQLException;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/16.
 */
public class SqlTimeOutFilter extends FilterEventAdapter {
    /**
     * 默认超时时间,单位秒
     * 注意不要设置太小
     */
    private static final int QUERY_TIMEOUT_THRESHOLD_SECOND = 400;

    /**
     * 超时时间,默认为QUERY_TIMEOUT_THRESHOLD_SECOND
     */
    private int timeoutThreshold = QUERY_TIMEOUT_THRESHOLD_SECOND;

    @Override
    protected void statementExecuteBefore(StatementProxy statement, String sql) {
        setQueryTimeout(statement);
        super.statementExecuteBefore(statement, sql);
    }

    @Override
    protected void statementExecuteBatchBefore(StatementProxy statement) {
        setQueryTimeout(statement);
        super.statementExecuteBatchBefore(statement);
    }

    @Override
    protected void statementExecuteUpdateBefore(StatementProxy statement, String sql) {
        setQueryTimeout(statement);
        super.statementExecuteUpdateBefore(statement, sql);
    }

    @Override
    protected void statementExecuteQueryBefore(StatementProxy statement, String sql) {
        setQueryTimeout(statement);
        super.statementExecuteQueryBefore(statement, sql);
    }

    /**
     * 设置Statement超时时间,
     * statement.setQueryTimeout单位是秒,0表示没有限制.这个函数可能会抛出SQLException异常,场景:
     *      1.数据库访问错误
     *      2.在一个已经关闭的Statement上调用这个方法
     *      3.超时时间不满足seconds >= 0的条件
     *
     * @param statement
     */
    private void setQueryTimeout(StatementProxy statement) {
        try {
            statement.setQueryTimeout(timeoutThreshold);
        }catch (SQLException se) {
            //TODO: do something
        }
    }

    public int getTimeoutThreshold() {
        return timeoutThreshold;
    }

    public void setTimeoutThreshold(int timeoutThreshold) {
        this.timeoutThreshold = timeoutThreshold;
    }
}
