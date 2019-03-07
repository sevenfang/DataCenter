package com.chezhibao.bigdata.msg.bo;

import java.io.Serializable;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/8.
 */
public class KafkaMsg implements Serializable {
    /**
     * kafka topic 名称
     */
    private String topic;
    /**
     * 发送的消息
     */
    private String msg;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "KafkaMsg{" +
                "topic='" + topic + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
