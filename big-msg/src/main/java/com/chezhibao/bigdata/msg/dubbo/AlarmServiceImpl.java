package com.chezhibao.bigdata.msg.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.msg.AlarmService;
import com.chezhibao.bigdata.msg.pojo.DingMsg;
import com.chezhibao.bigdata.msg.service.DingTalkService;
import org.springframework.stereotype.Component;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/16.
 */
@Component
@Service(interfaceClass = AlarmService.class ,timeout = 1000)
public class AlarmServiceImpl implements AlarmService {

    private DingTalkService dingTalkService;

    public AlarmServiceImpl(DingTalkService dingTalkService) {
        this.dingTalkService = dingTalkService;
    }

    @Override
    public void sendGroupAlarm(String groupName, String msg, String atSomeone) {
        DingMsg dingMsg = new DingMsg();
        dingMsg.setGroup(groupName);
        dingMsg.setMobiles(atSomeone);
        dingMsg.setMsg(msg);
        dingTalkService.sendDingTalkMsg(dingMsg);
    }

    @Override
    public void sendAlarm(String userId, String msg) {
        dingTalkService.sendDingTalkMsgToSpecifiedPerson(Integer.parseInt(userId),msg);
    }
}
