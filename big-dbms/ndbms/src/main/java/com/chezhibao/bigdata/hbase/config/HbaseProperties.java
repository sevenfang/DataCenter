package com.chezhibao.bigdata.hbase.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/25.
 */
@Component
@Data
@ConfigurationProperties(prefix = "hbase.zookeeper")
public class HbaseProperties {
    private String quorum;
    private String port;
    /**
     * 客户端重试次数 和 zk 链接重试次数  （配置在一起了）
     */

    private String retries;
    /**
     * 客户端重试 和 zk 链接重试  的间隔时间  （配置在一起了）
     */
    private String interval;
}
