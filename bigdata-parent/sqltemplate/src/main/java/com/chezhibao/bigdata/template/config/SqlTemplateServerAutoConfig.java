package com.chezhibao.bigdata.template.config;

import com.chezhibao.bigdata.template.service.SqlTemplateService;
import com.chezhibao.bigdata.template.service.SqlTemplateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WangCongJun
 * @date 2018/4/26
 * Created by WangCongJun on 2018/4/26.
 */
@Configuration
@EnableConfigurationProperties(SqlTemplateConfigration.class)
@ConditionalOnClass(SqlTemplateServiceImpl.class)
@ConditionalOnProperty(prefix = "sql.template" , value ="enabled", matchIfMissing = true)
public class SqlTemplateServerAutoConfig {
    @Autowired
    SqlTemplateConfigration sqlTemplateConfigration;
    @Bean(name = "sqlTemplateService")
    public SqlTemplateService getMyConnectServer(){
        return new SqlTemplateServiceImpl(sqlTemplateConfigration);
    }


}
