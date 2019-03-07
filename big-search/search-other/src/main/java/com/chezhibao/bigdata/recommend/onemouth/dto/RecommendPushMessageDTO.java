package com.chezhibao.bigdata.recommend.onemouth.dto;

import com.chezhibao.bigdata.common.utils.IDUtils;
import com.chezhibao.bigdata.msg.bo.PushMessage;
import com.chezhibao.bigdata.recommend.onemouth.bo.RecommendPushMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/23.
 */
@Slf4j
public class RecommendPushMessageDTO {

    private static String BASE_URL = "http://dfs.public.mychebao.com";
    private static String SUFFIX = ".jpg";

    public static List<PushMessage> transPushMessages(List<RecommendPushMessage> recommendPushMessages){
        return null;
    }

    /**
     * 消息格式：
     * {
     *   "path" : "31",
     *   "detectId" : "2926979",
     *   "auctionCarId" : "2004924",
     *   "DUID" : "8348787",
     *   "aps" : {
     *     "sound" : "default",
     *     "mutable-content" : 1,
     *     "alert" : {
     *       "subtitle" : "副标题",
     *       "title" : "标题",
     *       "body" : "消息内容主题哈哈哈哈"
     *     },
     *     "img" : "http://dfs.mychebao.com/download/image/910fdaaac922eef78b1646cbb4f4a0ad_min.jpg",
     *     "badge" : 1
     *   },
     *   "action" : "carDetail"
     * }
     *
     *
     * 消息内容：您感兴趣的奥迪A6L2018领先版正在一口价拍卖，全国只有一辆，价格只要20.x万元，看谁手速快。点我立刻前往查看，直接一键成交。
     *
     *  @param recommendPushMessage
     * @return
     * @throws Exception
     */
    public static PushMessage transPushMessage(RecommendPushMessage recommendPushMessage) {
        String nowToken = recommendPushMessage.getNowToken();
        Assert.notNull(nowToken,"nowToken can not be null!!");
        String nowClient = recommendPushMessage.getNowClient();
        Assert.notNull(nowClient,"nowClient can not be null!!");
        PushMessage pushMessage = new PushMessage();

        //拼装消息内容
        StringBuilder builder = new StringBuilder("您感兴趣的");
        builder.append(recommendPushMessage.getCarBrand())
                .append(recommendPushMessage.getCarModel())
                .append(recommendPushMessage.getCarType())
                .append("正在一口价拍卖，全国只有一辆");
        String price = recommendPushMessage.getPrice();
        if(price!=null){
            builder.append("，价格只要").append(price);
        }
        builder.append("，看谁手速快。点我立刻前往查看，直接一键成交。");
        String msg = builder.toString();
        pushMessage.setMsg(msg);
        //添加附加消息
        Map<String,String> attachments=new HashMap<>();
        attachments.put("path","31");
        attachments.put("detectId",recommendPushMessage.getDetectionId());
        attachments.put("detectionId",recommendPushMessage.getDetectionId());
        attachments.put("DAUID", UUID.randomUUID().toString());
        attachments.put("action","carDetail");
        attachments.put("auctionCarId",recommendPushMessage.getAuctionCarId());
        //添加图片信息
        String imgUrl = BASE_URL + "/download/image/" + recommendPushMessage.getDefaultImg() + SUFFIX;
        attachments.put("img",imgUrl);
        pushMessage.setAttachments(attachments);
        //添加基本信息
        pushMessage.setBuyerId(Integer.parseInt(recommendPushMessage.getBuyerId()));
        pushMessage.setClient(recommendPushMessage.getNowClient());
        pushMessage.setToken(recommendPushMessage.getNowToken());
        log.info("【查询系统|一口价推荐】最后推送的消息内容{}", pushMessage);
        return pushMessage;
    }
}
