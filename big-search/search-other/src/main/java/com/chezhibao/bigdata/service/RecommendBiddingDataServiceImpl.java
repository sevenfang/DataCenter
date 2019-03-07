package com.chezhibao.bigdata.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.common.exception.BigException;
import com.chezhibao.bigdata.dbms.redis.IRedisService;
import com.chezhibao.bigdata.search.recommend.RecommendBiddingDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jerry on 2018/11/5.
 */
@Service(interfaceClass = RecommendBiddingDataService.class)
@Component
@Slf4j
public class RecommendBiddingDataServiceImpl implements RecommendBiddingDataService {

    @Reference(check = false)
    private IRedisService redisService;

    /**
     * 出价数据redis的key
     */
    public static final String REDISKEY_PRE_BIDDING_PRICE_DETAIL_AUCTIONCAR = "bidding_price_detail:";

    @Override
    public Map<Integer, String> getBiddingDetail(List<Integer> auctionCarIds) {
        Map<Integer, String> biddingDetails = new HashMap<>();
        try {
            for (Integer auctionCarId : auctionCarIds) {
                String s = "";
                String redisKey = REDISKEY_PRE_BIDDING_PRICE_DETAIL_AUCTIONCAR + auctionCarId;
                try{
                    s = redisService.get(redisKey);
                    log.info("【查询服务】查询的redis的key为{},结果：{}", redisKey, s);
                }catch (Exception e){
                    log.error("【查询服务】查询的redis的key{}失败！！",redisKey,e);
                }
                if (StringUtils.isEmpty(s)) {
                    s = "{}";
                }
                biddingDetails.put(auctionCarId, s);
            }
        } catch (BigException e) {
            log.error("RecommendBiddingDataServiceImpl.getBiddingDetail失败: {}",auctionCarIds, e);
        }
        return biddingDetails;
    }
}
