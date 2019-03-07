package com.chezhibao.bigdata.zipkin.dubbo.dubbo.config;

import com.chezhibao.bigdata.zipkin.dubbo.dubbo.BraveConsumerFilter;
import com.chezhibao.bigdata.zipkin.dubbo.dubbo.BraveDubboManagementBean;
import com.chezhibao.bigdata.zipkin.dubbo.dubbo.BraveFactoryBean;
import com.chezhibao.bigdata.zipkin.dubbo.dubbo.BraveProviderFilter;
import com.github.kristofa.brave.Brave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/16.
 */
@Configuration
@Import(ZipkinProperties.class)
@ConditionalOnProperty(prefix = "spring.zipkin",name = "server",havingValue = "true")
public class ZipkinAutoConfiguration {

    @Autowired
    private ZipkinProperties zipkinProperties;

    @Bean
    public BraveFactoryBean getBraveFactoryBean() throws Exception{
        BraveFactoryBean braveFactoryBean = new BraveFactoryBean();
        String zipkinHost = zipkinProperties.getZipkinHost();
        String serverName = zipkinProperties.getServerName();
        String rate = zipkinProperties.getRate();
        braveFactoryBean.setServiceName(serverName);
        braveFactoryBean.setZipkinHost(zipkinHost);
        braveFactoryBean.setRate(rate);
        Brave brave = braveFactoryBean.getObject();
        BraveConsumerFilter.setBrave(brave);
        BraveProviderFilter.setBrave(brave);
        return braveFactoryBean;
    }
}
