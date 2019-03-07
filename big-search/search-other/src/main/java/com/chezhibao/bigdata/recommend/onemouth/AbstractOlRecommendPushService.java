package com.chezhibao.bigdata.recommend.onemouth;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.config.client.ParamsUtil;
import com.chezhibao.bigdata.msg.AppMsgPushService;
import com.chezhibao.bigdata.msg.bo.PushMessage;
import com.chezhibao.bigdata.recommend.onemouth.bo.RecommendPushMessage;
import com.chezhibao.bigdata.recommend.onemouth.dto.RecommendPushMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/23.
 */
@Slf4j
public abstract class AbstractOlRecommendPushService {

    private static final String APP_PUSH_ONE_MOUTH_RECOMMEND_LIST = "app.push.onemouth.recommend:list";


    public abstract AppMsgPushService getAppMsgPushService();

    /**
     * 获取有效推荐消息
     *
     * @return
     */
    public abstract List<RecommendPushMessage> getEffectivePushMessage();

    /**
     * 推送消息
     */
    public void pushMessage() {
        List<RecommendPushMessage> effectivePushMessage = getEffectivePushMessage();
        for (RecommendPushMessage recommendPushMessage : effectivePushMessage) {
            pushMessage(recommendPushMessage);
        }
    }

    private List<String> getPushBuyerList() {
        List<String> list = new ArrayList<>();
        List<String> redisList = ParamsUtil.getDynamicValueList(APP_PUSH_ONE_MOUTH_RECOMMEND_LIST, String.class);
        if (ObjectUtils.isEmpty(redisList)) {
            log.info("【查询系统|一口价推荐】推荐所有");
            return null;
        }
        list.addAll(redisList);
        log.info("【查询系统|一口价推荐】推荐商户列表{}", list);
        return list;
    }

    /**
     * 推送单条消息
     *
     * @param recommendPushMessage
     */
    public void pushMessage(RecommendPushMessage recommendPushMessage) {
        try {
            List<String> list = getPushBuyerList();
            if (ObjectUtils.isEmpty(list) || list.contains(recommendPushMessage.getBuyerId())) {
                log.info("【查询系统|一口价推荐】正在推送app消息到商户{}", recommendPushMessage.getBuyerId());
                PushMessage pushMessage = RecommendPushMessageDTO.transPushMessage(recommendPushMessage);
                Object o = getAppMsgPushService().pushMsg2Buyer(pushMessage);
                log.info("【查询系统|一口价推荐】推荐消息发送结果{}", o);
            }
        } catch (Exception e) {
            log.error("【查询系统|一口价推荐】app消息推送出错了！！消息{}", recommendPushMessage, e);
        }
    }
}
