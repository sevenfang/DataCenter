package com.chezhibao.bigdata.msg.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.msg.KAfkaService;
import com.chezhibao.bigdata.msg.bo.KafkaMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/8.
 */
@Service(interfaceClass = KAfkaService.class)
@org.springframework.stereotype.Service
public class KafkaServiceImpl implements KAfkaService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public Boolean sendMsg(KafkaMsg kafkaMsg) {
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(kafkaMsg.getTopic(), kafkaMsg.getMsg());
        return send.isDone();
    }
}
