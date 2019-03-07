package com.chezhibao.bigdata.msg;

/**
 * @author Huangjie2
 * Created by Huangjie2 on 2018/10/15.
 */
public interface KAfkaLogService {
    /**
     * 发送推荐埋点日志到数据部kafka -- 供竞拍端用
     * @param kafkaMsg
     * @return
     */
    Boolean sendTjmdAucsMsg(String kafkaMsg);
}
