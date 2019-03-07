package com.chezhibao.bigdata.msg.service.impl;

import com.chezhibao.bigdata.msg.config.DingMsgConfiguration;
import com.chezhibao.bigdata.msg.enums.DingTalkEnums;
import com.chezhibao.bigdata.msg.exception.DingTalkException;
import com.chezhibao.bigdata.msg.pojo.DingMsg;
import com.chezhibao.bigdata.msg.service.DingTalkService;
import com.lebo.chezhibao.msg.api.dingtalk.DingtalkSendType;
import com.lebo.chezhibao.msg.app.AppClientSDK;
import com.lebo.chezhibao.msg.entity.DeviceType;
import com.lebo.chezhibao.msg.entity.DingtalkRequest;
import com.lebo.chezhibao.msg.entity.MsgResponse;
import com.lebo.chezhibao.msg.entity.SendType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/5/9
 * Created by WangCongJun on 2018/5/9.
 */
@Service
@Slf4j
public class DingTalkServiceImpl implements DingTalkService {

    @Resource(name = "appClientSDK")
    private AppClientSDK appClientSDK;

    @Autowired
    private DingMsgConfiguration dingMsgConfiguration;


    @Override
    public MsgResponse sendDingTalkMsg(DingMsg dingMsg) {
        String title = dingMsg.getTitle();
        String mobiles=dingMsg.getMobiles();
        String msg = dingMsg.getMsg();
        String group = dingMsg.getGroup();
        if(StringUtils.isEmpty(group)){
            throw DingTalkException.newInstance(DingTalkEnums.DING_GROUPNAME_IS_NULL);
        }
        Map<String, String> tokens = dingMsgConfiguration.getToken();
        log.debug("【推送APP消息】钉钉消息推送，tokens:{}",tokens);
        String token = tokens.get(group);
        if(StringUtils.isEmpty(token)){
            throw DingTalkException.newInstance(DingTalkEnums.DING_GROUP_TOKEN_NOT_EXIST);
        }
        log.debug("【推送APP消息】钉钉消息推送，此次推送token",token);
        MsgResponse resp = null;
        try
        {
            DingtalkRequest request=new DingtalkRequest();

            request.setDeviceType(DeviceType.Dingtalk.getValue());
            //此处必须设置类型
            request.setDingtalkSendType(DingtalkSendType.R2G.getValue());
            request.setMsgDesc(msg);
            request.setMsgTitle(title);
            if(!StringUtils.isEmpty(mobiles)) {
                request.setAtMobiles(mobiles);
            }
            request.setR2gAccessToken(token);
            request.setAppId(13);
            request.setSendType(SendType.TIMELY.getValue());
            request.setCreateTime(new Date());

            resp=appClientSDK.pushMsgToDingtalk(request);

            return resp;
        }
        catch (Exception e)
        {
            log.error("【消息系统】",e);
        }
        return null;
    }

    @Override
    public MsgResponse sendDingTalkMsgToSpecifiedPerson(DingMsg dingMsg) {
        String userId=dingMsg.getUserId();
        if(StringUtils.isEmpty(userId)){
            throw DingTalkException.newInstance(DingTalkEnums.DING_UNSPECIFIED_USER);
        }
        String title = dingMsg.getTitle();
        String msg = dingMsg.getMsg();
        MsgResponse resp;
        try
        {
            DingtalkRequest request=new DingtalkRequest();

            request.setDeviceType(DeviceType.Dingtalk.getValue());
            //此处必须设置类型
            request.setDingtalkSendType(DingtalkSendType.B2P.getValue());
            request.setMsgDesc(msg);
            request.setMsgTitle(title);
            request.setTouser(userId);
            request.setAppId(13);
            request.setSendType(SendType.TIMELY.getValue());
            request.setCreateTime(new Date());

            resp=appClientSDK.pushMsgToDingtalk(request);

            return resp;
        }
        catch (Exception e)
        {
            log.error("【消息系统】",e);
        }
        return null;
    }

    @Override
    public MsgResponse sendDingTalkMsgToSpecifiedPerson(Integer userId, String msg) {
        DingMsg dingMsg = new DingMsg();
        log.info("【消息系统】发送消息：{}",msg);
        dingMsg.setMsg(msg);
        dingMsg.setUserId(userId+"");
        return  sendDingTalkMsgToSpecifiedPerson(dingMsg);
    }
}
