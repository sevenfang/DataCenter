package com.chezhibao.bigdata;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.chezhibao.bigdata.config.client.listener.BigdataConfigImportListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/10.
 */
@SpringBootApplication
@EnableDubboConfiguration
public class DbmsApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DbmsApplication.class);
        application.run(args);
    }
}
