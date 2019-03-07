package com.chezhibao.bigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;

/**
 * @author czb123
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConfigApplication.class);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }
}
