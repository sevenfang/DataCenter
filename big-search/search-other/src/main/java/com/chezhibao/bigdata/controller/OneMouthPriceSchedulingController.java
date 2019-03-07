package com.chezhibao.bigdata.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chezhibao.api.intf.auction.AuctionClient;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.dao.NewWareHouseDao;
import com.chezhibao.bigdata.msg.AppMsgPushService;
import com.chezhibao.bigdata.recommend.onemouth.AbstractOlRecommendPushService;
import com.chezhibao.bigdata.recommend.onemouth.bo.RecommendPushMessage;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/25.
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class OneMouthPriceSchedulingController {

    @Reference(check = false)
    private NewWareHouseDao newWareHouseDao;

    @Autowired
    private SqlTemplateService sqlTemplateService;

    @Reference
    private AuctionClient auctionClient;
    @RequestMapping("one/mouth/car")

    public Object test1(String cars){
        List<Integer> integers = JSON.parseObject(cars, new TypeReference<List<Integer>>() {
        });
        log.error("========{}",integers);
        Map<Integer,Double> prices = auctionClient.getYikoujia(integers);
        log.error("========{}",prices);
        return prices;
    }

    @Autowired
    private AbstractOlRecommendPushService olRecommendPushService;
    @RequestMapping("one/mouth")
    public Object test(String buyerId){
        Map<String, Object> params = ParamsBean.newInstance().put("buyerId", buyerId).build();
        String sql = "SELECT * FROM dw_new.`ol_recommend_push` WHERE cutime > DATE_ADD(NOW(), INTERVAL - 2 HOUR) and nowToken != 'noToken' AND buyerId=#{params.buyerId}  ORDER BY cutime DESC;";
        List<LinkedHashMap<String, Object>> query = newWareHouseDao.query(sql, params);
        LinkedHashMap<String, Object> info = query.get(0);
        RecommendPushMessage recommendPushMessage = ObjectCommonUtils.map2Object(info,RecommendPushMessage.class);
        Integer auctionCarId = Integer.parseInt(recommendPushMessage.getAuctionCarId());
        List<Integer> integers = new ArrayList<>();
        integers.add(auctionCarId);
        Map<Integer, Double> yikoujia = auctionClient.getYikoujia(integers);
        Double aDouble = yikoujia.get(auctionCarId);
        recommendPushMessage.setPrice(aDouble.intValue()/10000+".x万元");
        olRecommendPushService.pushMessage(recommendPushMessage);
        return "0000";
    }
}
