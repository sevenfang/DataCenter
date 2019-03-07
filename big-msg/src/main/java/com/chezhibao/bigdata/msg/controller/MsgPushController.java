package com.chezhibao.bigdata.msg.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.msg.AppMsgPushService;
import com.chezhibao.bigdata.msg.bo.PushMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WangCongJun
 * @date 2018/4/28
 * Created by WangCongJun on 2018/4/28.
 */
@RestController
@RequestMapping("/msg")
public class MsgPushController {

    @Autowired
    private AppMsgPushService appMsgPushService;

    /**
     * 群发App消息到到商户
     * @param data
     * @return
     */
    @PostMapping("push/buyer")
    public BigdataResult pushAppMsg2Buyer(
            String data) {

        List<PushMessage> maps = JSON.parseObject(data, new TypeReference<List<PushMessage>>() {
        });
        Boolean aBoolean = appMsgPushService.pushMsg2Buyer(maps);

        return BigdataResult.ok();

    }

    /**
     * 群发短信
     * @param data
     * @return
     */
    @PostMapping("sms/buyer")
    public BigdataResult sendSMSmsg2Buyer(
                    String data) {
        List<PushMessage> maps = JSON.parseObject(data, new TypeReference<List<PushMessage>>() {
        });
        appMsgPushService.sendSMSmsg(maps);
        return BigdataResult.ok();
    }

    /**
     * 发送消息到商户
     *
     * @param msg
     * @return
     */
    public Object pushBuyer(PushMessage msg) {
        return null;
    }


    /**
     * 发送短信到商户（单条，可以指定10个以内的号码）
     *
     * @param msg
     * @return
     */
    public Object sendSMS(PushMessage msg) {
        return null;
    }
}
