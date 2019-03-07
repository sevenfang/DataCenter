package com.chezhibao.bigdata.config.client.filter;

import com.chezhibao.clotho.ClothoFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/8.
 */

public class ClothoConfig {
    @Bean
    public ClothoFilter clothoFilter() {
        return new ClothoFilter();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean2(ClothoFilter clothoFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(clothoFilter);// 添加过滤器
        registration.addUrlPatterns("/*");// 设置过滤路径，/*所有路径
        registration.addInitParameter("name", "alue");// 添加默认参数
        registration.setName("MyClothoFilter");// 设置优先级
        registration.setOrder(2);// 设置优先级
        return registration;
    }
}
