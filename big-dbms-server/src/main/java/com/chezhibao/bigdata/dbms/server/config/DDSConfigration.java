package com.chezhibao.bigdata.dbms.server.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.chezhibao.bigdata.dbms.server.datasource.DynamicDataSource;
import com.chezhibao.bigdata.dbms.server.filter.SqlTimeOutFilter;
import com.google.common.collect.Lists;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * 动态数据源的配置
 *
 * @author WangCongJun
 * @date 2018/4/25
 * Created by WangCongJun on 2018/4/25.
 */
@Configuration
@EnableConfigurationProperties(DatasourcesConfigration.class)
//@MapperScan(basePackages = {"com.chezhibao"}, sqlSessionFactoryRef = "dynamicSqlSessionFactory")
public class DDSConfigration {

    @Autowired
    private DatasourcesConfigration datasourcesConfigration;

//    @Bean(name = "AllDs")
    public HashMap<Object,Object> getAllDsFromDbcp() throws Exception {
        HashMap<Object, Object> datasources = new HashMap<>();
        Map<String,Properties> datasourceProps = datasourcesConfigration.getDatasourceProp();
        for(String dbName : datasourceProps.keySet()){
            DataSource dataSource = null;
//            BasicDataSource dataSource = BasicDataSourceFactory.createDataSource(datasourceProps.get(dbName));
            datasources.put(dbName,dataSource);
        }
        return datasources;
    }

    @Bean(name = "AllDs")
    public HashMap<String,Object> getAllDs() throws Exception {
        HashMap<String, Object> datasources = new HashMap<>();
        Map<String,Properties> datasourceProps = datasourcesConfigration.getDruidDatasourceProp();
        for(String dbName : datasourceProps.keySet()){
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.configFromPropety(datasourceProps.get(dbName));
            //设置dataSource的Filter
            List<Filter> filters = Lists.newArrayList();
            SqlTimeOutFilter timeOutFilter = new SqlTimeOutFilter();
            filters.add(timeOutFilter);
            dataSource.setProxyFilters(filters);
            dataSource.init();
            datasources.put(dbName,dataSource);
        }
        return datasources;
    }

    @Bean(name = "dynamicDataSource")
    @Resource(name = "AllDs")
    public DynamicDataSource getDataSource(HashMap AllDs) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(AllDs);
        return dynamicDataSource;
    }

    @Bean(name = "dynamicSqlSessionFactory")
    @Resource(name = "dynamicDataSource")
    public SqlSessionFactory dynamicSqlSessionFactory(DynamicDataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();

    }

}
