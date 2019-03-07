package com.chezhibao.bigdata.search.storm.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.common.log.LoggerUtils;
import com.chezhibao.bigdata.config.client.ParamsUtil;
import com.chezhibao.bigdata.dbms.redis.IRedisService;
import com.chezhibao.bigdata.search.common.SearchLogEnum;
import com.chezhibao.bigdata.search.similarcar.SimilarCarService;
import com.chezhibao.bigdata.search.storm.DetailCarService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DetailCarServiceImpl implements DetailCarService {
    private static final Logger log = LoggerUtils.Logger(SearchLogEnum.AUCTION_LOG);

    private static final String DETAIL_AUCTIONCAR_LAST_AMOUNT_KEY="recommend.detail_auctioncar_last_amount";

    private static final String REDISKEY_PRE_BIDDING_BUYERID = "bidding_car:";
    private static final String REDISKEY_PRE_DETAIL_BUYERID = "detail_car:";

    @Reference(check = false)
    private IRedisService redisService;
    private SimilarCarService similarCarService;

    public DetailCarServiceImpl(SimilarCarService similarCarService) {
        this.similarCarService = similarCarService;
    }

    @Override
    public List<Integer> getSimilarDetail(int buyerId) {
        try {
            long t1 = System.currentTimeMillis();
            //商户出价车辆
            String biddings = redisService.get(REDISKEY_PRE_BIDDING_BUYERID + buyerId);
                        List<Integer> biddingList;
            if (StringUtils.isEmpty(biddings)) {
                biddingList = new ArrayList();
            } else {
                biddingList = JSON.parseArray(biddings, Integer.class);
            }
            if(log.isDebugEnabled()){
                log.debug("【查询服务】商户{}出价车辆：{},{}",buyerId,biddings,biddingList);
            }
            //商户查看车辆
            String details = redisService.get(REDISKEY_PRE_DETAIL_BUYERID + buyerId);
            List<Integer> detailList;
            if (StringUtils.isEmpty(details)) {
                detailList = new ArrayList();
            } else {
                detailList = JSON.parseArray(details, Integer.class);
            }
            if(log.isDebugEnabled()){
                log.debug("【查询服务】商户{}查看车辆：{},{}",buyerId,details,detailList);
            }
            //拼接成完整的
            List<Integer> allDetailList = new ArrayList(biddingList);
            if(detailList != null){
                allDetailList.addAll(detailList);
            }
            if(log.isDebugEnabled()){
                log.debug("【查询服务】商户{}查看过的车辆：{}",buyerId,allDetailList);
            }
            //获取最新一次查看
            int lastAmount;
            try {
                lastAmount = Integer.parseInt(ParamsUtil.getDynamicValue(DETAIL_AUCTIONCAR_LAST_AMOUNT_KEY));
            } catch (Exception e) {
                lastAmount = 1;
                log.error("获取动态参数失败:{}", DETAIL_AUCTIONCAR_LAST_AMOUNT_KEY, e);
            }
            List<Integer> lastAuctionCarId = new ArrayList();
            for (int i = allDetailList.size(); i > 0 && i > allDetailList.size() - lastAmount; i--) {
                lastAuctionCarId.add(allDetailList.get(i - 1));
            }

            //查询所有相似车型
            Set<Integer> similarCarSet = new HashSet<>();
            long t2 = System.currentTimeMillis();

            for (Integer auctionCarId : lastAuctionCarId) {
                List<Integer> integers = null;
                try {
                    integers = similarCarService.getsimilarCarByauctionCarid(auctionCarId + "");
                } catch (Exception e) {
                    log.error("similarCarService is error,auctionCarId={}", auctionCarId, e);
                }
                if (integers != null) {
                    similarCarSet.addAll(integers);
                }
            }
            if(log.isDebugEnabled()){
                log.debug("【查询服务】商户ID{}根据{}推荐的相似车辆：{}",buyerId,lastAuctionCarId,similarCarSet);
            }

            long t3 = System.currentTimeMillis();

            similarCarSet.removeAll(allDetailList);
            List<Integer> result = new ArrayList<>(similarCarSet);
            long t4 = System.currentTimeMillis();

            log.info("getSimilarDetail执行时间:{},{},{},buyerId={}", t2 - t1, t3 - t2, t4 - t3, buyerId);
            //返回
            if(log.isDebugEnabled()){
                log.debug("【查询服务】商户{}相似推荐结果：{}",buyerId,result);
            }
            return result;
        } catch (Exception e) {
            log.error("getSimilarDetail is error,buyerId={}", buyerId, e);
            return new ArrayList<>();
        }
    }
}
