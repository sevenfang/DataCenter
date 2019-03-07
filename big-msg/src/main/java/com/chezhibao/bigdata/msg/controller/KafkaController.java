package com.chezhibao.bigdata.msg.controller;

import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.msg.KAfkaLogService;
import com.chezhibao.bigdata.msg.KAfkaService;
import com.chezhibao.bigdata.msg.bo.KafkaMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/8.
 */
@RestController
@RequestMapping("/msg/kafka")
public class KafkaController {

    @Autowired
    private KAfkaService kAfkaService;

    @RequestMapping("/send")
    public BigdataResult sendMsg(KafkaMsg kafkaMsg){
        kAfkaService.sendMsg(kafkaMsg);
        return BigdataResult.ok();
    }

    /**
     * sendtest仅供测试，可直接删除
     */
    @Autowired
    private KAfkaLogService kAfkaLogService;
    @RequestMapping("/sendtest")
    public BigdataResult sendMsg(String kafkaMsg){
        kAfkaLogService.sendTjmdAucsMsg(kafkaMsg);
        return BigdataResult.ok();
    }
}
