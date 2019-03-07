package com.chezhibao.bigdata.datasource;

import com.chezhibao.bigdata.dbms.common.LoggerUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author WangCongJun
 * @date 2018/4/25
 * Created by WangCongJun on 2018/4/25.
 */
@Data
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static Logger log = LoggerUtil.RDBMS_LOG;

    private String dsName;

    @Override
    protected Object determineCurrentLookupKey() {
        String db = DataSourceContextHolder.getDB();
        log.debug("【动态数据库切换】当前数据库为：{}", db);
        return db;
    }
}
