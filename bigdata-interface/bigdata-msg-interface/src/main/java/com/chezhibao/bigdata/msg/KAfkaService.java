package com.chezhibao.bigdata.msg;

import com.chezhibao.bigdata.msg.bo.KafkaMsg;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/8.
 */
public interface KAfkaService {
    /**
     * 发送数据到kafka
     * @param kafkaMsg
     * @return
     */
    Boolean sendMsg(KafkaMsg kafkaMsg);
}
