package com.chezhibao.bigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/18.
 */
@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ApiGatewayApplication.class);
        application.run(args);
    }
}
