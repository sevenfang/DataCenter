package com.chezhibao.bigdata.gateway.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/20.
 */
@Data
@Component
@ConfigurationProperties(prefix = "gateway")
public class GatewayConfig {
    private Integer port;
    private String registryAddr;
}
