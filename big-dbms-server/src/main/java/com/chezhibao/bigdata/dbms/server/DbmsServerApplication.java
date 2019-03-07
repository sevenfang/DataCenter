package com.chezhibao.bigdata.dbms.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/13.
 */
@ComponentScan({"com.chezhibao.bigdata"})
@ServletComponentScan
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class DbmsServerApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DbmsServerApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
    }
}
