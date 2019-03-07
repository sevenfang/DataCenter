package com.chezhibao.bigdata.msg;

import com.chezhibao.bigdata.msg.bo.PushMessage;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/4/28
 * Created by WangCongJun on 2018/4/28.
 */
public interface AppMsgPushService {

    /**
     * 推送消息给指定商户
     * @param pushMessage 消息
     */
    Object pushMsg2Buyer(PushMessage pushMessage);

    /**
     * 推送消息给指定商户
     * @param channelId 渠道ID
     */
    Object pushMsg2Channel(Integer channelId, String msg);

    /**
     * 推送消息给指定商户
     * @param msgs 推送商户的信息
     */
    Boolean pushMsg2Buyer(List<PushMessage> msgs);

    /**
     * 发送多个SMS消息
     * @param msgs
     * @return
     */
    Boolean sendSMSmsg(List<PushMessage> msgs);

    /**
     * 发送单个SMS消息
     * @param pushMessage
     * @return
     */
    Object sendSMSmsg(PushMessage pushMessage);

    /**
     * 推送消息给指定商户
     * @param msgs 推送渠道的信息
     */
    Object pushMsg2Channel(List<Map<Integer, String>> msgs);


}
