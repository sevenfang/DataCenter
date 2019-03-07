package com.chezhibao.bigdata.datasource;

import com.chezhibao.bigdata.dbms.common.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * @author WangCongJun
 * @date 2018/4/25
 * Created by WangCongJun on 2018/4/25.
 */
public class DataSourceContextHolder {

    private static Logger log = LoggerUtil.RDBMS_LOG;

    /**
     * 默认数据源
     */
    public static final String DEFAULT_DS = "default";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
    static {
        contextHolder.set(DEFAULT_DS);
    }

    // 设置数据源名
    public static void setDB(String dbType) {
        log.debug("【数据库管理系统】切换数据库为：{}", dbType);
        contextHolder.set(dbType);
    }

    // 获取数据源名
    public static String getDB() {
        return (contextHolder.get());
    }

    // 清除数据源名
    public static void clearDB() {
        contextHolder.remove();
    }
}
