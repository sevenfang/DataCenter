package com.chezhibao.bigdata.msg.controller;

import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.msg.enums.DingTalkEnums;
import com.chezhibao.bigdata.msg.exception.DingTalkException;
import com.chezhibao.bigdata.msg.pojo.DingMsg;
import com.chezhibao.bigdata.msg.service.DingTalkService;
import com.lebo.chezhibao.msg.entity.MsgResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangCongJun
 * @date 2018/5/9
 * Created by WangCongJun on 2018/5/9.
 */
@RestController
@RequestMapping("/msg")
@Slf4j
public class DingTalkController {

    @Autowired
    private DingTalkService dingTalkService;

    /**
     * 推送到组
     * 参数 msg：消息内容
     *      group： 群组
     *      title：主题（没有用）
     * @param dingMsg
     * @return
     */
    @RequestMapping(value="/ding/push",method = RequestMethod.POST)
    public BigdataResult sendDingtalkMsg(DingMsg dingMsg){
        log.debug("【推送APP消息】钉钉消息推送，推送的消息：{}",dingMsg);
        MsgResponse msgResponse = dingTalkService.sendDingTalkMsg(dingMsg);
        if(ObjectUtils.isEmpty(msgResponse)){
            throw DingTalkException.newInstance(DingTalkEnums.PUSH_MSG_FAILED);
        }
        return BigdataResult.ok();
    }


    /**
     * 参数 msg：消息内容
     *      userID： 发送的人
     *      title：主题（没有用）
     * @param dingMsg
     * @return
     */
    @RequestMapping(value="/ding/push/user",method = RequestMethod.POST)
    public BigdataResult sendDingtalkMsgToSpecifiedPerson(DingMsg dingMsg){
        log.debug("【推送APP消息】钉钉消息推送，推送的消息：{}",dingMsg);
        MsgResponse msgResponse = dingTalkService.sendDingTalkMsgToSpecifiedPerson(dingMsg);
        if(ObjectUtils.isEmpty(msgResponse)){
            throw DingTalkException.newInstance(DingTalkEnums.PUSH_MSG_FAILED);
        }
        return BigdataResult.ok();
    }
}
