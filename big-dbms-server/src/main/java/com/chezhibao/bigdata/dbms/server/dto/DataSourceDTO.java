package com.chezhibao.bigdata.dbms.server.dto;

import com.alibaba.druid.pool.DruidDataSource;
import com.chezhibao.bigdata.dbms.server.bo.DBInstanceSchemaBO;
import com.chezhibao.bigdata.dbms.server.utils.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/24.
 */
@Slf4j
public class DataSourceDTO {
    public static DataSource transDBInstanceSchemaBOToDS(DBInstanceSchemaBO dbInstanceSchemaBO){
        Properties properties = DBUtils.addDruidDefaultProperties();
        String url = DBUtils.getUrl(dbInstanceSchemaBO);
        properties.put("druid.url",url);
        properties.put("druid.username",dbInstanceSchemaBO.getUsername());
        String password = dbInstanceSchemaBO.getPassword();
        password=StringUtils.isEmpty(password)?"":password;
        properties.put("druid.password", password);
        String driverClassName = dbInstanceSchemaBO.getDriverClassName();
        if(!StringUtils.isEmpty(driverClassName)){
            log.debug("【数据库管理系统】获取指定的driverClassName：{}",driverClassName);
            properties.put("druid.driverClassName", driverClassName);
        }
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.configFromPropety(properties);
        try {
            druidDataSource.init();
        }catch (SQLException e){
            log.error("【数据库管理系统】初始化dataSource失败，name:{}",dbInstanceSchemaBO.getName(),e);
        }
        return druidDataSource;
    }
}
