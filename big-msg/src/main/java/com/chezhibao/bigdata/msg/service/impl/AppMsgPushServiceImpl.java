package com.chezhibao.bigdata.msg.service.impl;

import com.chezhibao.bigdata.msg.AppMsgPushService;
import com.chezhibao.bigdata.msg.bo.PushMessage;
import com.chezhibao.bigdata.msg.utils.AppIdAndPushTypeUtils;
import com.lebo.chezhibao.msg.app.AppClientSDK;
import com.lebo.chezhibao.msg.entity.*;
import com.lebo.chezhibao.msg.entity.youmeng.UmengPushType;
import com.lebo.chezhibao.msg.util.UrlAssemblyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/4/28
 * Created by WangCongJun on 2018/4/28.
 * APPID:	21
 * appKey:	aa6fa0059b3b4e6cbfd35793f8edd32f
 * appSecret:	d74310a553a30404e12e291766d1dbb6
 */
@Service
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = AppMsgPushService.class)
@Slf4j
public class AppMsgPushServiceImpl implements AppMsgPushService {


    @Resource(name = "appClientSDK")
    private AppClientSDK appClientSDK;

    @Override
    public MsgResponse pushMsg2Buyer(PushMessage pushMessage) {
        Integer buyerId = pushMessage.getBuyerId();
        String msg = pushMessage.getMsg();
        String title = pushMessage.getTitle();

        log.debug("【推送APP消息】推送消息给ID为{}的商户！", buyerId);

        //封装消息对象
        YouMengAppRequest youMengAppRequest =new YouMengAppRequest();

        //确定目标系统的id
        youMengAppRequest.setAppType(AppIdAndPushTypeUtils.transBuyerAppType(pushMessage.getClient()));

        youMengAppRequest.setUmengPush(UmengPushType.unicast.getValue());
        /*
         * 添加token,这里的token是直接传来的
         * 正常是从chezhibao库中
         * 商户token 表t_buyer_client 字段nowToken
         * 渠道token 表t_buyer_channel_appdevice 字段deviceId
         */

        youMengAppRequest.setDeviceTokens(pushMessage.getToken());
        log.debug("【推送APP消息】友盟消息DeviceTokens：{}",youMengAppRequest.getDeviceTokens());
        youMengAppRequest.setMsgTitle(title);
        youMengAppRequest.setMsgDesc(msg);
        youMengAppRequest.setSendType(SendType.TIMELY.getValue());
        youMengAppRequest.setCreateTime(new Date());
        youMengAppRequest.setDeviceType(DeviceType.YouMeng.getValue());

        Map<String, Object> map =new HashMap<>();
        map.put("action","carDetail");
        Map<String, String> attachments = pushMessage.getAttachments();
        if(!ObjectUtils.isEmpty(attachments)){
            map.putAll(attachments);
        }
        youMengAppRequest.setExtraStr(UrlAssemblyUtil.mapToStr(map));
        log.debug("【推送APP消息】友盟消息附加内容：{}",youMengAppRequest.getExtraStr());
        try {
            return appClientSDK.pushYouMengMsg(youMengAppRequest);

        } catch (Exception e) {
            log.error("【消息系统】",e);
        }

        return null;
    }


    @Override
    public Boolean pushMsg2Buyer(List<PushMessage> msgs) {
        for (PushMessage msg: msgs){
            MsgResponse msgResponse = pushMsg2Buyer(msg);
            if(!msgResponse.isSucess()){
                log.error("【PUSH消息】给{}推送消息：{}失败！",msg.getBuyerId(),msg.getMsg());
            }
        }
        return true;
    }

    @Override
    public Boolean sendSMSmsg(List<PushMessage> msgs) {
        for (PushMessage msg: msgs){
            MsgResponse msgResponse = sendSMSmsg(msg);
            log.debug("【短信消息】发送给{}的消息结果{}",msg.getMobile(),msgResponse);
            if(!msgResponse.isSucess()){
                log.error("【短信消息】给{}推送短信：{}失败！",msg.getBuyerId(),msg.getMsg());
            }
        }
        return true;
    }

    @Override
    public MsgResponse sendSMSmsg(PushMessage pushMessage) {
        StringBuffer account = new StringBuffer();
        account.append(pushMessage.getMobile());

        SmsMsgRequest request = new SmsMsgRequest();
        request.setDeviceType(DeviceType.SMS.getValue());
        request.setMsgResource(1);
        request.setMsgTitle("test");
        request.setSendType(SendType.TIMELY.getValue());
        request.setPhoneNum(account.toString());
        request.setCreateTime(new Date());

        Map<String, Object>	contentParam =new HashMap<>();
        contentParam.put("content", pushMessage.getMsg());
        request.setContentParam(contentParam);
        request.setTemplate("sms_buyer_refund_push");

        try {
            MsgResponse msgResponse = appClientSDK.pushMsgToSms(request);
            return msgResponse;

        } catch (Exception e) {
            log.error("【消息系统】",e);
        }
        return null;
    }

    @Override
    public Object pushMsg2Channel(List<Map<Integer, String>> msgs) {
        //TODO 暂时不管
        return null;
    }

    @Override
    public Object pushMsg2Channel(Integer channelId,String msg) {
        //TODO 暂时不管
        return null;
    }


}
