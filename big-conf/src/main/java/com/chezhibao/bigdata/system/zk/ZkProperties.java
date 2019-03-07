package com.chezhibao.bigdata.system.zk;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author WangCongJun
 * @date 2018/5/17
 * Created by WangCongJun on 2018/5/17.
 */
@Component
@ConfigurationProperties(prefix = "zk")
@Data
public class ZkProperties {
    private String servers;
    private Integer maxTotal;
    private Integer maxIdle;
    private Integer minIdle;
    private Integer sessionTimeout;
    private Integer connectionTimeout;
}
