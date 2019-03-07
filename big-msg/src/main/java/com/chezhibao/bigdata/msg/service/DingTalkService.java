package com.chezhibao.bigdata.msg.service;

import com.chezhibao.bigdata.msg.pojo.DingMsg;
import com.lebo.chezhibao.msg.entity.MsgResponse;

/**
 * @author WangCongJun
 * @date 2018/5/9
 * Created by WangCongJun on 2018/5/9.
 */
public interface DingTalkService {
    /**
     * 推送钉钉消息到群组
     * @param dingMsg
     * @return
     */
    MsgResponse sendDingTalkMsg(DingMsg dingMsg);

    /**
     * 推送钉钉消息到指定的人
     * @param dingMsg
     * @return
     */
    MsgResponse sendDingTalkMsgToSpecifiedPerson(DingMsg dingMsg);

    /**
     * 发送消息给指定的用户
     * @param userId
     * @param msg
     * @return
     */
    MsgResponse sendDingTalkMsgToSpecifiedPerson(Integer userId ,String msg);
}
