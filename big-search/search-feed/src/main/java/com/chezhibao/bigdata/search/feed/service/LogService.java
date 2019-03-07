package com.chezhibao.bigdata.search.feed.service;

/**
 * 日志服务，发送和获取功能
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/19.
 */
public interface LogService {
    /**
     * 发送日志到消息队列
     * @param msg
     * @return
     */
    Long sendLog(String msg);

    /**
     * 从消息对下列获取消息
     * @return
     */
    String getLog();
}
