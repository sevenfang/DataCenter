package com.chezhibao.bigdata;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.chezhibao.bigdata.config.client.listener.BigdataConfigImportListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author WangCongJun
 * @date 2018/5/4
 * Created by WangCongJun on 2018/5/4.
 */

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@NacosPropertySource(dataId = "test.yml",groupId = "bigdata:to1:bigMsg")
@EnableDubboConfiguration
public class BigMsgApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BigMsgApplication.class);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }
}
