package com.chezhibao.bigdata;

import com.chezhibao.bigdata.common.log.LogService;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/21.
 */
public enum DbmsLogEnum implements LogService {
    //配置到logback-spring.xml中的logger name="kafkaLog"
    /**
     * kafka日志
     */
    KAFKA_LOG("kafkaLog"),
    /**
     * 推荐日志
     */
    RECOMMEND_LOG("recommendLog");

    private String logFileName;

    DbmsLogEnum(String fileName) {
        this.logFileName = fileName;
    }

    @Override
    public String getLoggerName() {
        return logFileName;
    }
}
