package com.chezhibao.bigdata.msg.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.msg.KAfkaLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author Huangjie2
 * Created by Huangjie2 on 2018/10/15.
 */
@Service(interfaceClass = KAfkaLogService.class)
@org.springframework.stereotype.Service
public class KafkaLogServiceImpl implements KAfkaLogService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public Boolean sendTjmdAucsMsg(String kafkaMsg) {
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send("tj_aucs_md", kafkaMsg);
        return send.isDone();
    }
}
