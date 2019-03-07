package com.chezhibao.bigdata.search.es.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.common.log.LoggerUtils;
import com.chezhibao.bigdata.common.utils.StringUtils;
import com.chezhibao.bigdata.dbms.redis.IRedisService;
import com.chezhibao.bigdata.dbms.redis.ListRedisService;
import com.chezhibao.bigdata.recommend.RecomConstants;
import com.chezhibao.bigdata.search.common.SearchLogEnum;
import com.chezhibao.bigdata.search.common.util.AuctionCarUtils;
import com.chezhibao.bigdata.search.es.bo.RecommendInfo;
import com.chezhibao.bigdata.search.es.service.AucsElasticsearchService;
import com.chezhibao.bigdata.search.rdbms.dao.RdbmsDao;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WangCongJun
 * @date 2018/5/14
 * Created by WangCongJun on 2018/5/14.
 */
//调整服务端executor数量，以及最大并发数actives
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = AucsElasticsearchService.class,timeout = 5000)
@Service("aucsDbService")
public class AucsDbServiceImpl implements AucsElasticsearchService {
    private static final Logger log = LoggerUtils.Logger(SearchLogEnum.SIMILAR_CAR);

    @Autowired
    private RdbmsDao rdbmsDao;
    @Reference(check = false)
    private ListRedisService listRedisService;
    @Reference(check = false)
    private IRedisService redisService;



    @Override
    public List<RecommendInfo> getRecommendInfoByBuyerId(Integer buyerId, Integer page, Integer size) {
        return new ArrayList<>();
    }


    @Override
    public List<RecommendInfo> getRecommendInfoByBuyerId(String key, List<Integer> auctioncarIds, Integer buyerId, Integer page, Integer size) {
        return new ArrayList<>();
    }

    /***
     * 带有竞拍时间竞拍大厅数据接口
     * @param key
     * @param flag
     * @param auctioncarIds
     * @param buyerId
     * @return
     */
    @Override
    public List<RecommendInfo> getRecommendInfoByBuyerIdSortByDate(String key, int flag, List<Integer> auctioncarIds, Integer buyerId) {
        return new ArrayList<>();
    }

    /**
     * 猜你喜欢 出价结束后，根据规则推荐车辆
     * 目前线上正在调用的接口
     * @param auctioncarid
     * @param count
     * @return
     */
    @Override
    public List<Integer> getGuessRecomnedInfo(int buyerId,String auctioncarid, double bidprice, String count) {
        String key = StringUtils.assembledRedisKey(RecomConstants.GOOD_CAR_BID_FINISH_SAMEBRAND_INFO, buyerId+"_"+auctioncarid);
        // 从redis中获取key
        List<String> redisValue = getGuessRecommendInfoFromRedis(key);
        // 把list里面的string转int
        List<Integer> returnValue = new ArrayList<>();
        for(String car : redisValue){
            returnValue.add(Integer.parseInt(car));
        }
        if (log.isDebugEnabled()) {
            log.debug("【AucsDbServiceImpl同车位推荐-出价完成】查询redis结果{} ", returnValue);
        }
        // 缓存返回结果大小大于0，直接返回
        if(returnValue.size()>0){
            return returnValue;
        }else{
            // 重新计算
            returnValue = getGuessRecommendInfoFromCal(buyerId,auctioncarid,bidprice,count,key);
            if(returnValue.isEmpty()){
                return new ArrayList<>();
            }
            return returnValue;
        }
    }

    public List<Integer> getGuessRecomnedInfoTest(int buyerId,String auctioncarid, double bidprice, String count) {
        return new ArrayList<>();
    }

    public List<String> getGuessRecommendInfoFromRedis(String key){
        List<String> strings;
        //获取指定数据
        try{
            strings = listRedisService.get(key);
        }catch (Exception e){
            return new ArrayList<>();
        }
        if (log.isDebugEnabled()) {
            log.debug("【AucsDbServiceImpl同车位推荐-出价完成】全部推荐的查询key{} ", key, strings);
        }
        return AuctionCarUtils.transToString(strings);
    }


    public List<Integer> getGuessRecommendInfoFromCal(int buyerId,String auctioncarid, double bidprice, String count,String key){
        //return new ArrayList<>();
        Assert.notNull(count,"count 不能为空");
        int i = Integer.parseInt(count);
        if (log.isDebugEnabled()) {
            log.debug("【AucsDbServiceImpl同车位推荐-出价完成】获取buyerId 参数: {},auctioncarid参数:{},bidprice参数:{},count参数:{}", buyerId, auctioncarid, bidprice, count);
        }
        //新定义一个list 集合对象
        List<String> lastList ;

        // 将string类型的auctioncarid,转int
        List<Integer> returnList = new ArrayList<>();
        // 查询同品牌、车型、车系车辆
        String buyerProvinceCityKey = StringUtils.assembledRedisKey(RecomConstants.GOOD_RECOMMEND_SAME_BRAND_CAR_INFO, auctioncarid);
        try{
            String redisResult = redisService.get(buyerProvinceCityKey);
            if(redisResult !=null){
                lastList = Arrays.asList(redisResult.split(","));
                for(String aucCarid:lastList){
                    returnList.add(Integer.parseInt(aucCarid));
                }
            }
        }catch (Exception e){
            // 如果redis中查询不到，直接返回空
            return new ArrayList<>();
        }
        if (log.isDebugEnabled()) {
            log.debug("【AucsDbServiceImpl同车位推荐-出价完成】查询相同品牌的redis {} ", returnList);
        }
        // 获取车商已经出价过的车辆
        List<Map<String, Object>> haveBiddingCars = rdbmsDao.getBuyerBiddingCarInfo(buyerId);

        // 定义一个集合，存入出价过车辆
        Set<Integer> set = new HashSet<>();

        for (Map<String, Object> haveBiddingCar : haveBiddingCars) {
            // 存入已经出过价的车辆
            set.add((Integer)haveBiddingCar.get("auctionCarId"));
        }
        if (log.isDebugEnabled()) {
            log.debug("【AucsDbServiceImpl】获取车商已经出价过的车辆 {}", set);
        }
        // 过滤已经出价过的车辆
        returnList.removeAll(set);
        if (log.isDebugEnabled()) {
            log.debug("【AucsDbServiceImpl】过滤已经出价过的车辆 {}", returnList);
        }
        // 返回指定个数
        if(returnList.size()>i){
            returnList = returnList.subList(0, i);
        }
        //判断 存入redis的值是否为空
        if(returnList.size()>0){
            //清空原先的数据
            listRedisService.delete(key);
            //最终将数据写入到redis
            listRedisService.addAll(key,
                    AuctionCarUtils.transToString(returnList));
            //添加过期时间
            listRedisService.expire(key, 10 * 60 * 1000L);
        }
        return returnList;
    }

    /**
     * 猜你喜欢 出价结束后，根据规则推荐车辆 已经废弃
     *
     * @param auctioncarid
     * @param count
     * @return
     */
    @Override
    public List<Integer> getGuessRecomnedInfo(String auctioncarid, double bidprice, String count) {
        log.info("【同车位推荐-出价完成】获取,auctioncarid参数:{},bidprice参数:{},count参数:{}", auctioncarid,bidprice,count);

        //获取商户所在城市
        int cityId = (int)rdbmsDao.getBuyerCity(301).get(0).get("region");

        //获取商户所在省份的所有城市
        List<Map<String, Object>> buyerCityList;
        buyerCityList = rdbmsDao.getBuyerCityList(301);
        List<Integer> cityList = new ArrayList<>();
        for (Map<String, Object> buyerCity : buyerCityList) {
            cityList.add((int)buyerCity.get("id"));
        }

        List<Map<String, Object>> cars;
        int countsum=0;
        cars = rdbmsDao.getGuessRecommdByBrandModel(1,auctioncarid, bidprice,cityList, "10");
        log.info("【同车位推荐-出价完成】规则一返回值大小:{}", cars.size());
        if(cars.isEmpty()){
            log.info("【同车位推荐-出价完成】规则一返回值为0:{}");
            cars = rdbmsDao.getGuessRecommdByBidprice(auctioncarid, bidprice,cityList, "10");
            log.info("【同车位推荐-出价完成】规则二返回值大小:{}", cars.size());
        }
        List<Integer> carIds = new ArrayList<>();
        for (Map<String, Object> car : cars) {
             carIds.add((Integer) car.get("id"));
             log.info("【同车位推荐-出价完成】返回参数:{}", car.get("id"));
        }
        //返回推荐的车辆
        return carIds;
    }

    /**
     * 获取带有竞拍时间的 index和type数据
     *
     * @return
     */
    private Map<String, Object> getBidtimeIndexTypePrams(Integer buyerId, Integer page) {
        return new HashMap<>();
    }

    /**
     * 确认带有竞拍时间的版本号
     * 确认TYPE
     * 判断逻辑：page为1 则取redis中的最新版本号
     * 否则取商户各自存在redis中的版本号
     *
     * @param buyerId
     * @param page
     * @return
     */
    private String confirmbidTimeVersion(Integer buyerId, Integer page) {
       return "";
    }


    @Override
    public List<RecommendInfo> getRecommendInfoByBuyerId(String key, List<Integer> auctioncarIds, Integer buyerId) {
       return new ArrayList<>();
    }

    /**
     * 获取index和type数据
     *
     * @return
     */
    private Map<String, Object> getIndexTypePrams(Integer buyerId, Integer page) {
        Map<String, Object> params = new HashMap<>();
        return params;
    }


    /**
     * 确认版本号
     * 确认TYPE
     * 判断逻辑：page为1 则取redis中的最新版本号
     * 否则取商户各自存在redis中的版本号
     *
     * @param buyerId
     * @param page
     * @return
     */
    private String confirmVersion(Integer buyerId, Integer page) {
       return "";
    }

    /**
     * 查询es获取带竞拍时间排序车辆信息
     *
     * @param page
     * @param size
     * @param params        查询的参数
     * @param buyerRedisKey 商户在redis中缓存的key
     * @return
     */
    private List<RecommendInfo> getRecommendInfoByBuyerIdSort(Map<String, Object> params, String buyerRedisKey, Integer page, Integer size, int flag) {
       return new ArrayList<>();
    }

    /**
     * 查询es获取排序车辆信息
     *
     * @param page
     * @param size
     * @param params        查询的参数
     * @param buyerRedisKey 商户在redis中缓存的key
     * @return
     */
    private List<RecommendInfo> getRecommendInfoByBuyerId(Map<String, Object> params, String buyerRedisKey, Integer page, Integer size) {
        return new ArrayList<>();
    }

    /**
     * 好车推荐/渠道app  查询es获取排序车辆信息
     *
     * @param buyerId 商户在redis中缓存的key
     * @return
     */
    @Override
    public List<RecommendInfo> getRecommendInfoByBuyerId(Integer buyerId) {
        return new ArrayList<>();
    }

    /**
     * 删除下架车辆
     *
     * @param auctioncarid
     */
    @Override
    public void delExpiredCar(Integer auctioncarid) {
    }

    /**
     * 新增临时场车辆
     *
     * @param auctionId
     * @param carId
     */
    @Override
    public void addNewCarRecommendInfo(Integer auctionId, Integer carId) {
    }


}
