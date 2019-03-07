package com.chezhibao.bigdata;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@EnableDubboConfiguration
public class WHTCApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WHTCApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
    }
}
