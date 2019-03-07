package com.chezhibao.bigdata.search.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/19.
 */
@Configuration
@EnableScheduling
@EnableAsync
public class RecommendSchedulingConfig implements SchedulingConfigurer {

    /**
     * 自定义线程池
     * @return
     */
    @Bean(destroyMethod="shutdown")
    public Executor taskExecutor() {
        return  Executors.newScheduledThreadPool(20);
    }

    /**
     * 自定义异步线程池
     * @return
     */
    @Bean
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("Recommend-SubExecutor");
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(100);
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        return executor;
    }


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

}
