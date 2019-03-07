package com.chezhibao.bigdata.msg.service;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/11.
 */
public interface CrawlMessageService {

    /**
     * 获取所有信息
     * @return
     */
    List<Object> getAllMsgs();

    /**
     * 清理过期数据
     * @param msgs
     */
    void checkExpiredMsg(List<Object> msgs);

    /**
     * 添加新数据
     * @param msg
     */
    void addNewMsg(Object msg);


    void stop();
    void start();

    /**
     * 发送新数据
     */
    void sendMsg();
}
