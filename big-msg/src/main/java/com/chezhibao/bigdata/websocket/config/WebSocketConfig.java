package com.chezhibao.bigdata.websocket.config;

import com.chezhibao.bigdata.websocket.CrawlMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(myHandler(), "/noauth/craw/msg").setAllowedOrigins("*");
    }

    @Bean
    public CrawlMessageHandler myHandler()
    {
        return new CrawlMessageHandler();
    }
}