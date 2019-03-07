package com.chezhibao.bigdata.msg;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/16.
 */
public interface AlarmService {
    /**
     * 发送告警到讨论组，并@谁
     * @param groupName
     * @param msg
     * @param atSomeones 注册钉钉的手机号码；多个用,隔开
     */
    void sendGroupAlarm(String groupName,String msg,String atSomeones );

    void sendAlarm(String userId,String msg);
}
