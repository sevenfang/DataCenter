package com.chezhibao.bigdata.recommend.onemouth.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.api.intf.auction.AuctionClient;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.dao.NewWareHouseDao;
import com.chezhibao.bigdata.msg.AppMsgPushService;
import com.chezhibao.bigdata.recommend.onemouth.AbstractOlRecommendPushService;
import com.chezhibao.bigdata.recommend.onemouth.bo.RecommendPushMessage;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/23.
 */
@Service
@Slf4j
public class OlRecommendPushServiceImpl extends AbstractOlRecommendPushService {

    @Reference(check = false)
    private NewWareHouseDao newWareHouseDao;

    @Reference(check = false)
    private AppMsgPushService appMsgPushService;

    @Autowired
    private SqlTemplateService sqlTemplateService;

    @Reference(check = false)
    private AuctionClient auctionClient;

    @Override
    public AppMsgPushService getAppMsgPushService() {
        return appMsgPushService;
    }

    /**
     * 获取最近2小时的有效推荐消息
     * @return
     */
    @Override
    public List<RecommendPushMessage> getEffectivePushMessage() {
        Map<Integer, RecommendPushMessage> messageMap = new HashMap<>();
        HashMap<String, Object> params = new HashMap<>();
        String sql = sqlTemplateService.getSql("onemouth.getEffectivePushMessage", params);
        List<LinkedHashMap<String, Object>> query = newWareHouseDao.query(sql, params);
        for (LinkedHashMap<String, Object> m : query) {
            Object buyerId = m.get("buyerId");
            if(StringUtils.isEmpty(buyerId)){
                continue;
            }
            Integer bId = Integer.parseInt(buyerId.toString());
            //去除重复的消息，防止对同一个人发送多条消息
            if (messageMap.containsKey(bId)) {
                continue;
            }
            RecommendPushMessage recommendPushMessage = ObjectCommonUtils.map2Object(m, RecommendPushMessage.class);
            try {
                //设置车辆价格
                setRecommendPushMessagePrice(recommendPushMessage);
            } catch (Exception e) {
                log.error("【查询系统|一口价推荐】查询{}价格失败！",recommendPushMessage.getAuctionCarId(),e);
                continue;
            }
            messageMap.put(bId, recommendPushMessage);
        }
        ArrayList<RecommendPushMessage> recommendPushMessages = new ArrayList<>(messageMap.values());
        if(log.isDebugEnabled()){
            log.info("【查询系统|一口价推荐】查询获取的推送消息内容()", recommendPushMessages);
        }
        return recommendPushMessages;
    }

    private void setRecommendPushMessagePrice(RecommendPushMessage recommendPushMessage) throws Exception{
        List<Integer> list = new ArrayList<>(1);
        //查询车辆价格
        String auctionCarId = recommendPushMessage.getAuctionCarId();
        if(StringUtils.isEmpty(auctionCarId)){
            throw new Exception("auctionCarId is null");
        }
        int i = Integer.parseInt(auctionCarId);
        list.add(i);
        Map<Integer, Double> yikoujia = auctionClient.getYikoujia(list);
        Double aDouble = yikoujia.get(i);
        if(aDouble==null){
          return;
        }
        String price = aDouble.intValue()/10000+".x万元";
        recommendPushMessage.setPrice(price);
    }
}
