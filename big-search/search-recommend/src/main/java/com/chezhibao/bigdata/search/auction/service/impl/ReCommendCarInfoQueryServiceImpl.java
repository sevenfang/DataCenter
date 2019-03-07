package com.chezhibao.bigdata.search.auction.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.common.aspect.annotation.InvokeTime;
import com.chezhibao.bigdata.common.log.LoggerUtils;
import com.chezhibao.bigdata.dbms.redis.ListRedisService;
import com.chezhibao.bigdata.search.auction.ReCommendCarInfoQueryService;
import com.chezhibao.bigdata.search.auction.service.AucsAllRecommendSjbService;
import com.chezhibao.bigdata.search.auction.service.ExposureWithoutBiddingService;
import com.chezhibao.bigdata.search.common.Constants;
import com.chezhibao.bigdata.search.common.SearchLogEnum;
import com.chezhibao.bigdata.search.common.util.AuctionCarUtils;
import com.chezhibao.bigdata.search.common.util.RedisKeyUtils;
import com.chezhibao.bigdata.search.es.bo.SearchPage;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/15.
 */
@Service
public class ReCommendCarInfoQueryServiceImpl extends ReCommendCarInfoQueryService {

    private static final Logger log = LoggerUtils.Logger(SearchLogEnum.AUCTION_LOG);
    @Reference(check = false)
    private ListRedisService listRedisService;
    private AucsAllRecommendSjbService aucsAllRecommendSjbService;
    //曝光车服务（看了未出价）
    private ExposureWithoutBiddingService exposureWithoutBiddingService;


    public ReCommendCarInfoQueryServiceImpl(ExposureWithoutBiddingService exposureWithoutBiddingService,
                                            AucsAllRecommendSjbService aucsAllRecommendSjbService) {
        this.exposureWithoutBiddingService = exposureWithoutBiddingService;
        this.aucsAllRecommendSjbService = aucsAllRecommendSjbService;
    }

    @Override
    public List<Integer> defaultSortRecommendCarInfo() {
        List<Integer> result = new ArrayList<>();
        //因为默认数据只有一份，所以暂时没有用到
        List<String> strings = listRedisService.get(Constants.AUCS_ALL_RECOMMEND_DEFAULT);
        if (!ObjectUtils.isEmpty(strings)) {
            result = AuctionCarUtils.transStringToInteger(strings);
        }
        return result;
    }

    @Override
    public List<Integer> computedSortRecommendCarInfo(List<Integer> recommendCar, List<Integer> defaultCar) {
        if (ObjectUtils.isEmpty(defaultCar)) {
            defaultCar = defaultSortRecommendCarInfo();
        }
        if (ObjectUtils.isEmpty(recommendCar)) {
            recommendCar = defaultSortRecommendCarInfo();
        }
        //取两者交集
        recommendCar.retainAll(defaultCar);
        //获取差集
        defaultCar.removeAll(recommendCar);
        //将差集追加到排序中
        recommendCar.addAll(defaultCar);
        return recommendCar;
    }


    @Override
    public List<Integer> queryRecommendCars(Integer buyerId, SearchPage searchPage) {
        String key = RedisKeyUtils.getReCommendCarInfoQueryKey(buyerId, searchPage);
        Integer page = searchPage.getPage();
        Integer size = searchPage.getSize();
        //获取对应的页码
        Integer start = (page - 1) * size;
        Integer end = (page) * size - 1;
        //获取指定数据
        List<String> strings = listRedisService.get(key, start, end);
        log.debug("【查询系统】全部推荐的查询key{},page:{},size:{},结果{}", key, page, size, strings);
        return AuctionCarUtils.transStringToInteger(strings);
    }



    /**
     * 处理第一页请求的数据
     * @param auctionCarIds
     * @param buyerId
     * @param searchPage
     */
    @InvokeTime
    private void handlerFirstPage(List<Integer> auctionCarIds, Integer buyerId, SearchPage searchPage){
        String key = RedisKeyUtils.getReCommendCarInfoQueryKey(buyerId, searchPage);
        List<Integer> recommendCars;
        //查询数据部计算得到的推荐排序
        List<Integer> recommendCarInfoByBuyerId = aucsAllRecommendSjbService.getAllRecommendData(buyerId);
        //综合推荐排序 和 默认给的车 计算出推荐结果
        recommendCars = computedSortRecommendCarInfo(recommendCarInfoByBuyerId, auctionCarIds);
        if (ObjectUtils.isEmpty(recommendCars)) {
            recommendCars = auctionCarIds;
        }
        //增加曝光车
        exposureWithoutBiddingService.addExposureWithoutBidding(buyerId,recommendCars);

        //清空原先的数据
        listRedisService.delete(key);
        //最终将数据写入到redis
        listRedisService.addAll(key,
                AuctionCarUtils.transToString(recommendCars));
        //添加过期时间
        listRedisService.expire(key, 3 * 60 * 60 * 1000L);
    }

    @Override
    public List<Integer> queryRecommendCars(List<Integer> auctionCarIds, Integer buyerId, SearchPage searchPage) {
        Integer page = searchPage.getPage();
        if (page == 1) {
            handlerFirstPage(auctionCarIds,buyerId,searchPage);
        }
        //查询redis获取推荐数据
        List<Integer> integers = queryRecommendCars(buyerId, searchPage);
        log.info("【查询系统】推荐系统全部车辆查询结果：carId：{}，buyerId:{},searchPage：{}", integers, buyerId, searchPage);
        return integers;
    }
}
