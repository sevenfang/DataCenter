package com.chezhibao.bigdata.config.client.listener;

import com.chezhibao.bigdata.config.client.sentinel.SentinelRulerServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.*;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/14.
 */
@Slf4j
public class SentinelRulerListener implements ApplicationListener<SpringApplicationEvent>, PriorityOrdered {

    private int order = Ordered.LOWEST_PRECEDENCE + 1;


    @Override
    public void onApplicationEvent(SpringApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            onApplicationEnvironmentPreparedEvent(
                    (ApplicationEnvironmentPreparedEvent) event);
        }
        if (event instanceof ApplicationPreparedEvent) {
            onApplicationPreparedEvent((ApplicationPreparedEvent)event);
        }
        if (event instanceof ApplicationStartedEvent){
            onApplicationStartedEvent((ApplicationStartedEvent)event);
        }
        if (event instanceof ApplicationStartingEvent){
            onApplicationStartingEvent((ApplicationStartingEvent)event);
        }
    }

    private void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event){

    }

    private void  onApplicationStartedEvent(ApplicationStartedEvent event){
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();
        String namespace = environment.getProperty("spring.cloud.sentinel.nacos.namespace");
        SentinelRulerServer sentinelRulerServer = new SentinelRulerServer(namespace);
        sentinelRulerServer.loadRulers();
    }

    private void onApplicationPreparedEvent(ApplicationPreparedEvent event){
    }

    private void onApplicationStartingEvent(ApplicationStartingEvent event){

    }

    @Override
    public int getOrder() {
        return this.order;
    }
}
