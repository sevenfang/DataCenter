package com.chezhibao.bigdata.dbms.server.config;

import com.chezhibao.bigdata.dbms.server.websocket.WsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/dbms/download").setAllowedOrigins("*");
    }

    @Bean
    public WsHandler myHandler()
    {
        return new WsHandler();
    }
}