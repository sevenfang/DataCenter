package com.chezhibao.bigdata;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/14.
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@ServletComponentScan
@EnableDubboConfiguration
@ImportResource({"classpath:config/spring_dubbo.xml"})
public class CBRApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(CBRApplication.class);
        application.run(args);
    }
}
