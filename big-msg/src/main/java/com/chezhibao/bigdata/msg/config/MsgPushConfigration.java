package com.chezhibao.bigdata.msg.config;

import com.lebo.chezhibao.msg.app.AppClientSDK;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author WangCongJun
 * @date 2018/4/29
 * Created by WangCongJun on 2018/4/29.
 */
@Data
@Configuration
public class MsgPushConfigration {

    @Value("${msg.server.appid}")
    private Integer APPID;

    @Value("${msg.server.appkey}")
    private String appKey;

    @Value("${msg.server.secret}")
    private String appSecret;

    @Value("${msg.server.url}")
    private String msgUrl;

    @Bean("appClientSDK")
    public AppClientSDK appClientSDK(){
        return  AppClientSDK.getClientInstance(appKey, appSecret, msgUrl);
    }
}
