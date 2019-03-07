package com.chezhibao.bigdata.hbase.config;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.util.StringUtils;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/25.
 */
@Configuration
public class HbaseConfig {


    @Bean
    public HbaseTemplate hbaseTemplate(HbaseProperties hbaseProperties) {
        HbaseTemplate hbaseTemplate = new HbaseTemplate();
        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", hbaseProperties.getQuorum());
        conf.set("hbase.zookeeper.port", hbaseProperties.getPort());
        String retries = StringUtils.isEmpty(hbaseProperties.getRetries())?"3":hbaseProperties.getRetries();
        String interval = StringUtils.isEmpty(hbaseProperties.getInterval())?"200":hbaseProperties.getInterval();
        conf.set("hbase.client.retries.number",retries);
        conf.set("hbase.client.pause", interval);
        conf.set("zookeeper.recovery.retry", retries);
        conf.set("zookeeper.recovery.retry.intervalmill", interval);
        hbaseTemplate.setConfiguration(conf);
        hbaseTemplate.setAutoFlush(true);
        return hbaseTemplate;
    }
}
