package com.chezhibao.bigdata.dbms.server.datasource;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/4/25
 * Created by WangCongJun on 2018/4/25.
 */
@Data
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    private Map<Object, Object> targetDataSources;

    @Override
    protected Object determineCurrentLookupKey() {
        String db = DataSourceContextHolder.getDB();
        log.debug("【动态数据库切换】当前数据库为：{}", db);
        return db;
    }

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        this.targetDataSources = targetDataSources;
    }

    public void addDataSource(String name,DataSource dataSource){
        targetDataSources.put(name,dataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }
    public void addDataSource(Map<Object, Object> dataSources){
        targetDataSources.putAll(dataSources);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    public void delDataSource(String name){
        targetDataSources.remove(name);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }
}
