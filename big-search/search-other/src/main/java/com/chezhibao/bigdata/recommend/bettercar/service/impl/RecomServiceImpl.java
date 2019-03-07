package com.chezhibao.bigdata.recommend.bettercar.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.common.log.LoggerUtils;
import com.chezhibao.bigdata.dbms.hbase.HbaseService;
import com.chezhibao.bigdata.dbms.redis.ListRedisService;
import com.chezhibao.bigdata.search.es.util.ListUtil;
import com.chezhibao.bigdata.dbms.hbase.bo.HbaseParam;
import com.chezhibao.bigdata.dbms.redis.IRedisService;
import com.chezhibao.bigdata.recommend.RecomConstants;
import com.chezhibao.bigdata.search.common.SearchLogEnum;
import com.chezhibao.bigdata.search.es.service.impl.AucsDbServiceImpl;
import com.chezhibao.bigdata.service.chargecar.ObtainedCarService;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;
import com.chezhibao.bigdata.common.utils.StringUtils;
import com.chezhibao.bigdata.search.es.service.RecommendService;
import com.chezhibao.bigdata.search.rdbms.dao.RdbmsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chezhibao.bigdata.search.es.pojo.RecommedValue;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import javax.annotation.Resource;

import java.util.*;

/**
 * @author YuanJianWei
 * @date 2018/7/19
 * Created by YuanJianWei on 2018/7/19.
 */
// ctrl+i
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = RecommendService.class, timeout = 5000)
@Service("recomService")
public class RecomServiceImpl implements RecommendService {
    private static final Logger log = LoggerUtils.Logger(SearchLogEnum.BETTER_CAR);
    /**
     * 渠道车辆查询 Hbase 表
     */
    private static final String CHANNEL_CAR_AUCTION = "channel_car_auction";
    /**
     * 渠道查询商户的rowkey 前缀
     */
    private static final String CHANNEL_STAFF_ROWKEY_PREFIX = "channelstaffid_";

    /**
     * 渠道查询商户的rowkey 前缀
     */
    private static final String CHANNEL_BUYER_ROWKEY_PREFIX = "buyerid_";


    @Reference(check = false)
    private IRedisService redisService;

    @Autowired
    private RdbmsDao rdbmsDao;

    @Autowired
    private AucsDbServiceImpl aucsDbService;

    @Reference(check = false)
    private HbaseService hbaseService;

    @Autowired
    private ObtainedCarService obtainedCarService;

    @Reference(check = false)
    private ListRedisService listRedisService;
    /***
     * 竞拍首页 好车推荐
     * 新/老用户不同的推荐策略
     * @param buyerId
     * @return
     */
    @Override
    public List<Map<String, Object>> getBetterCarInfo(int buyerId) {
        log.debug("【猜你喜欢|好车推荐】获取buyerId 参数: {}",buyerId);
        //获取开始时间
        long startTime = System.currentTimeMillis();
        //定义一个list map
        List<Map<String, Object>> recommendList;
        //获取商户所在城市
        String cityId = "2072";
        String buyerCityKey = StringUtils.assembledRedisKey(RecomConstants.GOOD_BUYER_CITY_INFO, buyerId);
        try {
            cityId = redisService.get(buyerCityKey);
        }catch (Exception e){
            log.error("【猜你喜欢|好车推荐】获取商户所在城市异常");
        }
        if (log.isDebugEnabled()) {
            log.debug("【猜你喜欢|好车推荐】商户所在城市:{}", cityId);
        }

        List<Map<String, Object>> haveBiddingCars;
        // 获取车商已经出价过的车辆
        haveBiddingCars = rdbmsDao.getBuyerBiddingCarInfo(buyerId);
        // 定义一个集合，存入出价过车辆
        Set<String> set = new HashSet<>();

        for (Map<String, Object> haveBiddingCar : haveBiddingCars) {
            // 存入已经出过价的车辆
            if(haveBiddingCar.get("auctionCarId") !=null){
                set.add(String.valueOf((int)haveBiddingCar.get("auctionCarId")));
            }
        }
        //获取结束始时间
        if (log.isDebugEnabled()) {
            log.debug("【猜你喜欢|好车推荐】取出商户出价过车编：{}", set);
        }

        // 当前正在竞拍和车主降价车辆
        List<String> setIsBidding =  queryIsBiddingAndReductionCarFromRedis();
        if (log.isDebugEnabled()) {
            log.debug("【猜你喜欢|好车推荐】获取正在竞拍的车辆：{}", setIsBidding);
        }

        // 拼接key值
        String buyerAbilityFlagKey = StringUtils.assembledRedisKey(RecomConstants.BUYER_ABILITY_FLAG, buyerId);
        // 获取是否为新老车商
        String buyerAbilityFlagResult = getIsNewBuyer(buyerAbilityFlagKey);
        if (log.isDebugEnabled()) {
            log.debug("【猜你喜欢|好车推荐】是否新老用户值 {}", buyerAbilityFlagResult);
        }
        // 如果redis的值不为空
        if (buyerAbilityFlagResult !=null) {
            if (log.isDebugEnabled()) {
                log.debug("【猜你喜欢|好车推荐】新老用户值redis中不为空");
            }
            if (log.isDebugEnabled()) {
                log.debug("【猜你喜欢|好车推荐】是否为老用户 {}",buyerAbilityFlagResult.trim().equals("1"));
            }
            if (buyerAbilityFlagResult.trim().equals("1")) {
                //flag为1，是老用户
                if (log.isDebugEnabled()) {
                    log.debug("【猜你喜欢|好车推荐】为老用户进行推荐 {}", buyerId);
                }
                recommendList = forOldBuyerRecommend(buyerId, set,  setIsBidding);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("【猜你喜欢|好车推荐】为新用户进行推荐 {}", buyerId);
                }
                recommendList = forNewBuyerRecommend(set, buyerId , cityId, setIsBidding);
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("【猜你喜欢|好车推荐】新老用户值redis中为空");
                log.debug("【猜你喜欢|好车推荐】为新用户进行推荐 {}", buyerId);
            }
            recommendList = forNewBuyerRecommend(set, buyerId , cityId, setIsBidding);
        }
        // 空列表判断
        if(recommendList==null){
            return new ArrayList<>();
        }
        //获取结束时间
        long endTime = System.currentTimeMillis();
        log.debug("【猜你喜欢|好车推荐】 接口getBetterCarInfo调用总运行时长：{} ms", (endTime - startTime));
        return recommendList;
    }

    /**
     * 获取是否为新老车商
     * @param buyerAbilityFlagKey
     * @return
     */
    public String getIsNewBuyer(String buyerAbilityFlagKey){
        // 获取商户是否是新老用户
        String buyerAbilityFlag = "";
        try {
            buyerAbilityFlag = redisService.get(buyerAbilityFlagKey);
        } catch (Exception e) {
            log.error("【猜你喜欢|好车推荐】查询redis商户是否是新老用户{}失败！！", buyerAbilityFlagKey, e);
        }
        return buyerAbilityFlag;
    }

    /***
     * 对新用户进行推荐
     */
    public List<Map<String, Object>> forNewBuyerRecommend(Set<String> set, int buyerId,String cityId, List<String> setIsBidding) {
        //获取开始时间
        long startTime = System.currentTimeMillis();
        //定义一个list map
        List<Map<String, Object>> recommendList = new ArrayList<Map<String, Object>>();

        //已经推荐的车辆集合
        Set<Integer> set_havingRecommend = new HashSet<>();

        //组装hbase查询参数
        HbaseParam<String> hbaseParam = new HbaseParam<>();
        //确定表名 这里是固定的
        hbaseParam.setTableName(CHANNEL_CAR_AUCTION);
        //确定rowkey这里可以是动态的
        hbaseParam.setRowKey(CHANNEL_BUYER_ROWKEY_PREFIX + buyerId);
        String s = hbaseService.getString(hbaseParam);

        // 渠道推荐个数
        int channelRecommendCount = 0;

        if (!org.springframework.util.StringUtils.isEmpty(s)) {
            //不为空
            List<String> recommendListTmp = JSON.parseArray(s, String.class);
            if (log.isDebugEnabled()) {
                log.debug("【渠道|推荐车辆】查询hbase返回的结果值大小：{}", recommendListTmp.size());
            }
            for (String recomInfo : recommendListTmp) {
               if(channelRecommendCount<10){
                   Map<String, Object> map = new HashMap<String, Object>();
                   map.put("auctionCarId", recomInfo.split("_")[0]);
                   map.put("rank", "1.21");
                   set_havingRecommend.add(Integer.parseInt(recomInfo.split("_")[0]));
                   recommendList.add(map);
                   channelRecommendCount++;
               }
            }
        }

        //获取商户所在省份的所有城市
        List<String> buyerRegionList = new ArrayList<>();
        String buyerProvinceCityKey = StringUtils.assembledRedisKey(RecomConstants.GOOD_BUYER_PROVINCE_CITY_INFO, buyerId);
        try{
            String buyerProList = redisService.get(buyerProvinceCityKey).trim();
            if (buyerProList.contains(";")) {
                buyerRegionList = Arrays.asList(buyerProList.split(";"));
            } else {
                buyerRegionList = Arrays.asList(buyerProList);
            }
            if (log.isDebugEnabled()) {
                log.debug("【猜你喜欢|好车推荐】获取商户所在省份的所有城市 {}", buyerRegionList);
            }
        }catch (Exception e){
            buyerRegionList.add(cityId);
        }

        // 获取竞拍车辆中附近城市 一、二级 车况的车辆
        List<Map<String, Object>> betterCarConditionList = rdbmsDao.getbetterCarCondition(buyerRegionList);

        if (log.isDebugEnabled()) {
            log.debug("【猜你喜欢|好车推荐】推荐一、二级 车况的车辆为：{}", betterCarConditionList);
        }
        // 获取竞拍车辆中出价次数小于5次的车辆
        List<Map<String, Object>> bidNumLessFiveList = rdbmsDao.getbidNumLessFive(buyerRegionList);
        if (log.isDebugEnabled()) {
            log.debug("【猜你喜欢|好车推荐】推荐出价次数小于5次的车辆为：{}", bidNumLessFiveList);
        }

        betterCarConditionList.addAll(bidNumLessFiveList);
        if (log.isDebugEnabled()) {
            log.debug("【猜你喜欢|好车推荐】容易中标车辆：{}", betterCarConditionList);
        }

        // 推荐容易中标的车辆
        for (Map<String, Object> car : betterCarConditionList) {
            // 过滤已经出过价的车辆 并且 过滤已经推荐过的车辆
            if (!set.contains(car.get("id")) && ! set_havingRecommend.contains(car.get("id"))) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("auctionCarId", car.get("id"));
                map.put("rank", "1.0");
                set_havingRecommend.add((Integer) car.get("id"));
                recommendList.add(map);
            }
        }

        if(!CollectionUtils.isEmpty(recommendList) && recommendList.size()>60){
            recommendList = recommendList.subList(0,59);
        }

        if(log.isDebugEnabled()) {
            log.debug("【猜你喜欢|好车推荐】为新用户推荐的车辆列表：{}", recommendList);
        }

        //获取结束时间
        long endTime = System.currentTimeMillis();
        log.debug("【猜你喜欢|好车推荐】 接口forNewBuyerRecommend调用总运行时长：{} ms", (endTime - startTime));
        return recommendList;
    }

    /***
     * 对老用户进行推荐
     * @param buyerId  商户id
     * @param set 已出价车辆
     */
    public List<Map<String, Object>> forOldBuyerRecommend(int buyerId, Set<String> set,  List<String> setIsBidding) {
        //获取开始时间
        long startTime = System.currentTimeMillis();
        //定义一个list map
        List<Map<String, Object>> recommendList = new ArrayList<Map<String, Object>>();

        //已经推荐的车辆集合
        Set<Integer> set_havingRecommend = new HashSet<>();

        //组装hbase查询参数
        HbaseParam<String> hbaseParam = new HbaseParam<>();
        //确定表名 这里是固定的
        hbaseParam.setTableName(CHANNEL_CAR_AUCTION);
        //确定rowkey这里可以是动态的
        hbaseParam.setRowKey(CHANNEL_BUYER_ROWKEY_PREFIX + buyerId);
        String s = hbaseService.getString(hbaseParam);

        // 渠道推荐个数
        int channelRecommendCount = 0;

        if (!org.springframework.util.StringUtils.isEmpty(s)) {
            //不为空
            List<String> recommendListTmp = JSON.parseArray(s, String.class);
            if (log.isDebugEnabled()) {
                log.debug("【猜你喜欢|好车推荐】查询hbase返回的结果值大小：{}", recommendListTmp.size());
            }
            for (String recomInfo : recommendListTmp) {
                if(channelRecommendCount<8){
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("auctionCarId", recomInfo.split("_")[0]);
                    map.put("rank", "1.21");
                    recommendList.add(map);
                    set_havingRecommend.add(Integer.parseInt(recomInfo.split("_")[0]));
                    channelRecommendCount++;
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("【猜你喜欢|好车推荐】已经推荐的车辆集合大小 ：{}", set_havingRecommend);
        }

        // 拼接key值
        String buyerkey = StringUtils.assembledRedisKey(RecomConstants.GOOD_CAR_RECOMMENDATION_INFO, buyerId);
        //获取结束时间
        if (log.isDebugEnabled()) {
            log.debug("【猜你喜欢|好车推荐】拼接key值为：{} ", buyerkey);
        }

        long startTime_zero = System.currentTimeMillis();
        // 根据key查询value值
        String redisResult = "";
        try {
            redisResult = redisService.get(buyerkey);
        } catch (Exception e) {
            log.error("【猜你喜欢|好车推荐】查询的redis的key{}失败！！", buyerkey, e);
        }
        if (log.isDebugEnabled()) {
            log.debug("【猜你喜欢|好车推荐】查询redis返回的结果：{}", redisResult);
        }
        // 如果返回的值为空，返回空列表
        if (StringUtil.isEmpty(redisResult)) {
            if (log.isDebugEnabled()) {
                log.debug("【猜你喜欢|好车推荐】键值为{}的商户在redis中值为空", buyerId);
            }
            //默认车辆数据
            if (ObjectUtils.isEmpty(recommendList)) {
                // 默认推荐
                recommendList = queryDefaultCar(set);
            }
            if (log.isDebugEnabled()) {
                log.debug("【猜你喜欢|好车推荐】默认推荐的车辆为", recommendList);
            }
            return recommendList;
        }
        List<String> redis_value;
        // 如果根据 better:buyerid 从redis中查询出来的值只有一个，那没有逗号分割符
        // 如果值有多个，那么直接用逗号分割
        if (redisResult.contains(",")) {
            redis_value = Arrays.asList(redisResult.split(","));
        } else {
            redis_value = Arrays.asList(redisResult);
        }
        // 验证拿到的value中的key是否为空
        if (CollectionUtils.isEmpty(redis_value)) {
            if (log.isDebugEnabled()) {
                log.debug("【猜你喜欢|好车推荐】 redis中该商户的key为空，buyerId={}", buyerId);
            }
            return new ArrayList<>();
        }
        long endTime_zero = System.currentTimeMillis();
        log.info("【猜你喜欢|好车推荐】 根据key查询value值 运行时长：{} ms", (endTime_zero - startTime_zero));
        if (log.isDebugEnabled()) {
            log.debug("【猜你喜欢|好车推荐】 商户出价过车辆：{} ", set);
        }
        //log.info("【猜你喜欢|好车推荐】 被推荐的车辆为：{} ",redis_value);

        // 需要曝光的车辆
        //recommendList.addAll(needExposureCar(buyerNobidList,set,setIsBidding));
        //获取下架车
        List<Integer> obtainedCars = obtainedCarService.getObtainedCars();
        if (log.isDebugEnabled()) {
            log.debug("【猜你喜欢|好车推荐】获取下架车结果：{}", obtainedCars);
        }
        // redis中推荐车辆个数计数器
        int redisRecommendListSize = 0;
        if (log.isDebugEnabled()) {
            log.debug("【猜你喜欢|好车推荐】redis中推荐的车辆转数组大小为：{}", redis_value.size());
        }
        // 遍历redis中推荐车辆
        for (String accurateKey : redis_value) {
            // 定义map对象
            Map<String, Object> map = new HashMap<String, Object>();
            // 取出键值对
            String[] cacheValueList = accurateKey.split("_");
            // 竞拍车辆id
            String auctionCarId = cacheValueList[1].trim();
            // 推荐车辆不包括已经出价车辆 以及 推荐车辆属于是当前正在竞拍车辆
            if (!set.contains(auctionCarId) && setIsBidding.contains(auctionCarId)) {
                redisRecommendListSize++;
                if (log.isDebugEnabled()) {
                    log.debug("【猜你喜欢|好车推荐】不是商户出价过车辆 以及 正在竞拍的车辆");
                }
                // 排查 下架车辆、已经推荐的车辆
                if (!obtainedCars.contains(Integer.parseInt(cacheValueList[1].trim())) && !set_havingRecommend.contains(Integer.parseInt(cacheValueList[1].trim()))) {
                    if (log.isDebugEnabled()) {
                        log.debug("【猜你喜欢|好车推荐】排查了下架车辆、已经推荐的车辆");
                    }
                    map.put("auctionCarId", Integer.parseInt(cacheValueList[1].trim()));
                    map.put("rank", cacheValueList[2]);
                    set_havingRecommend.add(Integer.parseInt(cacheValueList[1].trim()));
                    recommendList.add(map);
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("【猜你喜欢|好车推荐】 redis中推荐的车辆为 {}", recommendList);
        }

        // 如果redis中存的值已经过期，即redis中auctioncarid不在当前正在竞拍车辆中
        if (redisRecommendListSize == 0) {
            // 获取默认推荐
            if (log.isDebugEnabled()) {
                log.debug("【猜你喜欢|好车推荐】 redis中存的值已经过期");
            }
            recommendList.addAll(queryDefaultCar( set));
        }
        if (log.isDebugEnabled()) {
            log.debug("【猜你喜欢|好车推荐】 为老用户推荐的车辆为：{} ms", recommendList);
        }
        //获取结束时间
        long endTime_three = System.currentTimeMillis();
        log.info("【猜你喜欢|好车推荐】 接口forOldBuyerRecommend调用总运行时长：{} ms", (endTime_three - startTime));
        return recommendList;
    }

    /***
     * 获取默认推荐车辆，取一级、二级车
     * @param set 已经出过价车辆的集合
     * @return
     */
    public List<Map<String, Object>> queryDefaultCar( Set<String> set) {
        List<Map<String, Object>> default_list = new ArrayList<Map<String, Object>>();
        List<String> defaultCar = queryIsBiddingAndReductionCarFromRedis();
        // 车况较好的车辆
        for (String car : defaultCar) {
            // 过滤已经出过价的车辆 和 需要曝光的车辆
            if (!set.contains(car)) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("auctionCarId", car);
                map.put("rank", "1.0");
                default_list.add(map);
            }
        }
        if(log.isDebugEnabled()) {
            log.debug("【猜你喜欢|好车推荐】默认推荐车辆列表为{}", default_list);
        }
        if(default_list.size()>50){
            default_list = default_list.subList(0,49);
        }
        return default_list;
    }

    /***
     * 需要曝光的车辆
     * @param buyerNobidList
     * @param set
     * @return
     */
    public List<Map<String, Object>> needExposureCar(List<Object> buyerNobidList, Set<Integer> set, Set<Object> setIsBidding) {
        List<Map<String, Object>> exposure_list = new ArrayList<Map<String, Object>>();
        // 需要曝光的车辆
        if (log.isDebugEnabled()) {
            log.debug("【猜你喜欢-好车推荐】曝光车辆列表{}", buyerNobidList);
        }
        if (!buyerNobidList.isEmpty()) {
            for (Object car : buyerNobidList) {
                // 剔除出价过的车辆 并且 正在竞拍的车辆
                if (!set.contains(car) && setIsBidding.contains(car)) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("auctionCarId", car);
                    map.put("rank", "1.1");
                    exposure_list.add(map);
                }
            }
        }
        if(log.isDebugEnabled()) {
            log.debug("【猜你喜欢-好车推荐】需要曝光的车辆{}", exposure_list);
        }
        return exposure_list;
    }

    /**
     * 同车位推荐
     *
     * @param mapValue
     * @param count
     * @return
     */
    @Override
    public Map<Integer, List<Integer>> recommendSameGradeCarInfo(int buyerId, Map<Integer, Double> mapValue, int count) {
        log.info("【同车位推荐-出价结果分析】mapValue:{},count参数:{}", mapValue, count);
        Map<Integer, List<Integer>> returnMap = new HashMap<>();

        //获取开始时间
        long startTime = System.currentTimeMillis();
        List<Map<String, Object>> haveBiddingCars;
        haveBiddingCars = rdbmsDao.getBuyerBiddingCarInfo(buyerId);

        Set<Object> set = new HashSet<>();
        long endTime = System.currentTimeMillis();

        for (Map<String, Object> haveBiddingCar : haveBiddingCars) {
            set.add(haveBiddingCar.get("auctionCarId"));
        }
        log.info("【同车位推荐-出价结果分析】取出商户出价 运行时长：{} ms", (endTime - startTime));
        // 遍历map
        long startTime_map = System.currentTimeMillis();
        for (Map.Entry<Integer, Double> entry : mapValue.entrySet()) {
            int countsum = 0;
            // 取出key 和 value
            if(log.isDebugEnabled()){
                log.debug("【同车位推荐-出价结果分析】 key={},value={}", entry.getKey(), entry.getValue());
            }
            List<Map<String, Object>> cars;

            // 数据库查询
            cars = rdbmsDao.getGuessRecommdByBrandModelList(entry.getKey() + "", entry.getValue(), count + "");

            List<Integer> carIds = new ArrayList<>();

            for (Map<String, Object> car : cars) {
                //过滤掉商户已经出价过的车辆
                if (!set.contains(car.get("id"))) {
                    countsum++;
                    if (countsum <= count) {
                        carIds.add((Integer) car.get("id"));
                        set.add(car.get("id"));
                    }
                }
            }

            //获取商户已经出价的车辆
            returnMap.put(entry.getKey(), carIds);
        }
        long endTime_map = System.currentTimeMillis();
        log.info("【同车位推荐-出价结果分析】调用 map ：{} ms", (endTime_map - startTime_map));

        return returnMap;
    }

    /**
     * 出价结束，好车推荐
     *
     * @param buyerId      商户id
     * @param auctioncarid 竞拍车辆id
     * @param bidprice     价格
     * @return
     */
    @Override
    public List<Map<String, Object>> getBidFinishRecommemdCarInfo(int buyerId, String auctioncarid, double bidprice) {
        //return  new ArrayList<>();
        if (log.isDebugEnabled()) {
            log.debug("【出价结束|出价完成 好车推荐】获取buyerId 参数: {},auctioncarid参数:{},bidprice参数:{},count参数:{}", buyerId, auctioncarid, bidprice);
        }
        //获取接口开始调用时间
        long startTime = System.currentTimeMillis();

        List<Map<String, Object>> haveBiddingCars;
        //获取开始时间
        long startTime_haveBiddingCars = System.currentTimeMillis();
        // 获取车商已经出价过的车辆
        haveBiddingCars = rdbmsDao.getBuyerBiddingCarInfo(buyerId);
        // 定义一个集合，存入出价过车辆
        Set<Object> set = new HashSet<>();
        for (Map<String, Object> haveBiddingCar : haveBiddingCars) {
            // 存入已经出过价的车辆
            set.add(haveBiddingCar.get("auctionCarId"));
        }

        if(log.isDebugEnabled()) {
            log.debug("【出价结束|出价完成】 商户出价过车辆：{} ", set);
        }

        // 根据key查询 达到车均次数或者出价大于系统评估价
        String redisDevelopedAuction = "";
        try {
            redisDevelopedAuction = redisService.get(RecomConstants.DEVELOPED_AUCTIONCAR);
            if (log.isDebugEnabled()) {
                log.debug("【查询服务】查询的redis的key为{},结果：{}",  redisDevelopedAuction);
            }
        } catch (Exception e) {
            log.error("【查询服务】查询的redis的key{}失败！！", RecomConstants.DEVELOPED_AUCTIONCAR, e);
        }
        // json 格式转数组
        List<Object> developedAuctionList = new ArrayList<>();
        if (redisDevelopedAuction != null) {
            developedAuctionList = ListUtil.jsonToList(redisDevelopedAuction);
        }
        //定义一个list map
        List<Map<String, Object>> recommendList = new ArrayList<Map<String, Object>>();

        //获取开始时间
        long startTime_concatrediskey = System.currentTimeMillis();
        // 拼接key值
        String buyerkey = StringUtils.assembledRedisKey(RecomConstants.GOOD_CAR_RECOMMENDATION_INFO, buyerId);
        //获取结束时间
        log.info("【出价结束|出价完成】拼接key值为：{} ", buyerkey);
        long endTime_concatrediskey = System.currentTimeMillis();
        log.info("【出价结束|出价完成】拼接key值 运行时长：{} ms", (endTime_concatrediskey - startTime_concatrediskey));

        long startTime_redisResult = System.currentTimeMillis();
        // 根据key查询value值
        String redisResult = "";
        try {
            redisResult = redisService.get(buyerkey);
        } catch (Exception e) {
            log.error("【查询服务】查询的redis的key{}失败！！", buyerkey, e);
        }
        if (log.isDebugEnabled()) {
            log.debug("【出价结束|出价完成】查询redis返回的结果：{}", redisResult);
        }

        // 定义一个集合，存入正在竞拍的车辆
        List<String> setIsBidding = queryIsBiddingAndReductionCarFromRedis();

        // 正在竞拍的车辆剔除 达到车均或者出价大于评估价的车辆
        setIsBidding.removeAll(developedAuctionList);

        // 如果返回的值为空，返回空列表
        if (redisResult == null) {
            log.info("【猜你喜欢|出价完成】键值为{}的商户在redis中值为空", buyerId);
            //默认车辆数据
            if (recommendList.isEmpty()) {
                recommendList = getDefaultRecommendCar( set, setIsBidding);
            }
            if (log.isDebugEnabled()) {
                log.debug("【出价结束|出价完成】默认推荐车辆过滤后的列表为{}", recommendList);
            }

            return recommendList;
        }

        List<String> redis_value ;
        // 如果根据 better:buyerid 从redis中查询出来的值只有一个，那没有逗号分割符
        // 如果值有多个，那么直接用逗号分割
        if (redisResult.contains(",")) {
            redis_value = Arrays.asList(redisResult.split(","));
        } else {
            redis_value = Arrays.asList(redisResult);
        }
        // 验证拿到的value中的key是否为空
        if (CollectionUtils.isEmpty(redis_value)) {
            if (log.isDebugEnabled()) {
                log.debug("【出价结束|出价完成】 recommend_key is null，buyerId={}", buyerId);
            }
            recommendList = getDefaultRecommendCar(set, setIsBidding);
            if (log.isDebugEnabled()) {
                log.debug("【出价结束|出价完成】拿到的value中的key为空时推荐车辆列表为{}", recommendList);
            }
            return recommendList;
        }
        if (log.isDebugEnabled()) {
            log.debug("【出价结束|出价完成】 被推荐的车辆为：{} ", redis_value.size());
        }

        // 排序匹配车辆
        //List<RecommedValue> returnRecInfo = recommemdComparablePojoBysort(redis_value,bidprice);

        // 设置推荐的个数
        int count = 0;
        // 遍历redis中推荐车辆
        for (String accurateKey : redis_value) {
            if (count < 50) {
                // 定义map对象
                Map<String, Object> map = new HashMap<String, Object>();
                // 取出键值对
                String[] cacheValueList = accurateKey.split("_");
                if (!set.contains(Integer.parseInt(cacheValueList[1].trim())) && setIsBidding.contains(Integer.parseInt(cacheValueList[1].trim()))) {
                    count++;
                    map.put("auctionCarId", Integer.parseInt(cacheValueList[1].trim()));
                    map.put("rank", cacheValueList[2]);
                    recommendList.add(map);
                }
            } else {
                //大于50就退出
                break;
            }
        }
        // redis中存的值已经过期，即count值为0
        if (count == 0) {
            if (log.isDebugEnabled()) {
                log.debug("【猜你喜欢|好车推荐】 redis中存的值已经过期");
            }
            recommendList = getDefaultRecommendCar( set, setIsBidding);
        }
        //获取结束时间
        long endTime_three = System.currentTimeMillis();
        log.info("【出价结束|出价完成】 接口getBidFinishRecommemdCarInfo调用总运行时长：{} ms", (endTime_three - startTime));
        if (log.isDebugEnabled()) {
            log.debug("【出价结束|出价完成】 接口recommendList ：{}" , recommendList);
        }
        return recommendList;
    }

    /***
     * 订阅推荐
     * @param buyerId
     * @param count
     * @return
     */
    @Override
    public List<Map<String, Object>> getSubscriptRecommemdCarInfo(int buyerId, int count) {
        List<Map<String, Object>> subscriptSeriesId;
        //获取开始时间
        long startTime = System.currentTimeMillis();
        // 获取车商订阅的车型id
        subscriptSeriesId = rdbmsDao.getBuyerSubscriptCarSeries(buyerId);

        List<Map<String, Object>> subscriptBrandId;
        // 获取车商订阅的品牌id
        subscriptBrandId = rdbmsDao.getBuyerSubscriptCarBrand(buyerId);

        // 定义一个集合，订阅的车系id
        Set<Object> setSeries = new HashSet<>();
        for (Map<String, Object> seriesCarId : subscriptSeriesId) {
            List<String> seriesCarIdList = Arrays.asList(seriesCarId.get("modelids").toString().split(","));
            // 存入订阅的车系
            setSeries.addAll(seriesCarIdList);
            //setSeries.add(seriesCarIdList);
        }
        log.info("【订阅推荐】获取商户订阅的车系id：{} ", setSeries);
        // 定义一个集合，订阅的品牌id
        Set<Object> setBrand = new HashSet<>();
        for (Map<String, Object> brandCarId : subscriptBrandId) {
            // 存入订阅的品牌
            setBrand.add(brandCarId.get("brandids"));
        }
        log.info("【订阅推荐】获取商户订阅的品牌id：{} ", setBrand);

        List<Map<String, Object>> haveBiddingCars;
        // 获取车商已经出价过的车辆
        haveBiddingCars = rdbmsDao.getBuyerBiddingCarInfo(buyerId);
        // 定义一个集合，存入出价过车辆
        Set<Object> set = new HashSet<>();
        for (Map<String, Object> haveBiddingCar : haveBiddingCars) {
            // 存入已经出过价的车辆
            set.add(haveBiddingCar.get("auctionCarId"));
        }
        //定义一个list map
        List<Map<String, Object>> recommendList = new ArrayList<Map<String, Object>>();
        // 拼接key值
        String buyerkey = StringUtils.assembledRedisKey(RecomConstants.GOOD_CAR_RECOMMENDATION_INFO, buyerId);
        //获取结束时间
        log.info("【订阅推荐】拼接key值为：{} ", buyerkey);
        // 根据key查询value值
        String redisResult = "";
        try {
            redisResult = redisService.get(buyerkey);
            if (log.isDebugEnabled()) {
                log.debug("【查询服务】查询的redis的key为{},结果：{}", buyerkey, redisResult);
            }
        } catch (Exception e) {
            log.error("【查询服务】查询的redis的key{}失败！！", buyerkey, e);
        }
        if (log.isDebugEnabled()) {
            log.debug("【订阅推荐】查询redis返回的结果：{}", redisResult);
        }
        // 定义一个集合，存入正在竞拍的车辆
        Set<Object> setIsBidding = new HashSet<>();
        // 获取正在竞拍的车辆
        List<Map<String, Object>> biddingCars = rdbmsDao.getDefaultCarInfo(5000);
        for (Map<String, Object> isBiddingCar : biddingCars) {
            setIsBidding.add(isBiddingCar.get("id"));
        }
        if (log.isDebugEnabled()) {
            log.debug("【订阅推荐】获取正在竞拍的车辆：{} ", setIsBidding.size());
        }
        if (redisResult == null) {
            //如果redis值为空
            return new ArrayList<>();
        }
        List<String> redis_value = null;
        // 如果根据 better:buyerid 从redis中查询出来的值只有一个，那没有逗号分割符
        // 如果值有多个，那么直接用逗号分割
        if (redisResult.contains(",")) {
            redis_value = Arrays.asList(redisResult.split(","));
        } else {
            redis_value = Arrays.asList(redisResult);
        }
        int total = 0;
        for (String accurateKey : redis_value) {
            if (total <= count) {
                // 定义map对象
                Map<String, Object> map = new HashMap<String, Object>();
                // 取出键值对
                String[] cacheValueList = accurateKey.split("_");
                if (!set.contains(Integer.parseInt(cacheValueList[1].trim())) && setIsBidding.contains(Integer.parseInt(cacheValueList[1].trim()))) {
                    if (setBrand.contains(cacheValueList[5])) {
                        // 品牌一致
                        total++;
                        map.put("auctionCarId", Integer.parseInt(cacheValueList[1].trim()));
                        map.put("rank", cacheValueList[2]);
                        recommendList.add(map);
                    } else if (setSeries.contains(cacheValueList[6])) {
                        // 车系一致
                        total++;
                        map.put("auctionCarId", Integer.parseInt(cacheValueList[1].trim()));
                        map.put("rank", cacheValueList[2]);
                        recommendList.add(map);
                    }
                }
            } else {
                //大于count就退出
                break;
            }
        }
        //获取结束时间
        long endTime_three = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("【订阅推荐】 接口getSubscriptRecommemdCarInfo 推荐列表 {}", (recommendList));
        }
        log.info("【订阅推荐】 接口getSubscriptRecommemdCarInfo调用总运行时长：{} ms", (endTime_three - startTime));

        return recommendList;
    }


    /**
     * 默认推荐
     *
     * @param set
     * @param setIsBidding
     * @return
     */
    public List<Map<String, Object>> getDefaultRecommendCar( Set<Object> set, List<String> setIsBidding) {
        List<Map<String, Object>> recommendList = new ArrayList<Map<String, Object>>();
        List<String> defaultCar = queryIsBiddingAndReductionCarFromRedis();
        if(defaultCar.size()>50){
            defaultCar = defaultCar.subList(0,49);
        }
        defaultCar.removeAll(set);
        if (log.isDebugEnabled()) {
            log.debug("【出价结束|出价完成】默认推荐车辆列表为{}", defaultCar);
        }
        for (String car : defaultCar) {
            // 推荐的车辆满足是正在竞拍的车辆
            if (setIsBidding.contains(car)) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("auctionCarId", car);
                map.put("rank", "1.0");
                recommendList.add(map);
            }
        }
        return recommendList;
    }

    /**
     * 默认推荐
     *
     * @param buyerId
     * @param auctioncarid
     * @param bidprice
     * @param set
     * @param setSeries
     * @return
     */
    public List<Map<String, Object>> getDefaultSubscriptRecommendCar(int buyerId, String auctioncarid,
                                                                     double bidprice, Set<Object> set, Set<Object> setSeries) {
        List<Map<String, Object>> recommendList = new ArrayList<Map<String, Object>>();
        List<Integer> defaultCar = aucsDbService.getGuessRecomnedInfo(buyerId, auctioncarid, bidprice, 40 + "");
        defaultCar.removeAll(set);

        // 正在竞拍的车辆id
        List<Map<String, Object>> biddingCarIdList = rdbmsDao.getCaridofAuctioncar(5000);
        // 获取满足车系条件的车辆

        return recommendList;
    }

    /**
     * 排序 返回推荐列表
     *
     * @param redisValue
     * @param bidprice
     * @return
     */
    public List<RecommedValue> recommemdComparablePojoBysort(List<String> redisValue, double bidprice) {
        List<RecommedValue> listSortRecommendValue
                = new ArrayList<>();
        //返回值
        List<RecommedValue> returnRecommendValue = null;

        for (String accurateKey : redisValue) {
            RecommedValue recommedValue = new RecommedValue();
            String[] cacheValueList = accurateKey.split("_");
            recommedValue.setBuyerId(cacheValueList[0]);
            recommedValue.setAuctionCarId(cacheValueList[1]);
            recommedValue.setRecommendScore(cacheValueList[2]);
            recommedValue.setElaPrice(Double.parseDouble(cacheValueList[3]));
            recommedValue.setRegion(cacheValueList[4]);

            listSortRecommendValue.add(recommedValue);
        }
        // 从小到大进行排序
        Collections.sort(listSortRecommendValue);
        //log.info("【出价结束|好车推荐】 sort排序结果 ："+ listSortRecommendValue);

        // redis列表中评估价最小的一个
        double minPrice = listSortRecommendValue.get(0).getElaPrice();
        log.info("【出价结束|好车推荐】 minPrice：" + minPrice);
        // redis列表中评估价最大的一个
        double maxPrice = listSortRecommendValue.get(listSortRecommendValue.size() - 1).getElaPrice();
        log.info("【出价结束|好车推荐】 maxPrice ：" + maxPrice);

        //如果最小值大于出价值，返回前*项
        log.info("【出价结束|好车推荐】 minPrice>bidprice : " + (minPrice > bidprice));
        log.info("【出价结束|好车推荐】 minPrice<bidprice : " + (minPrice < bidprice));
        if (minPrice > bidprice) {
            returnRecommendValue = listSortRecommendValue.subList(0, listSortRecommendValue.size() / 2);
            log.info("【出价结束|好车推荐】 如果最小值大于出价值，返回前*项 ：" + returnRecommendValue);
        }
        //如果最大值大于出价值，返回尾*项
        else if (bidprice > maxPrice) {
            returnRecommendValue = listSortRecommendValue.subList(listSortRecommendValue.size() / 2, listSortRecommendValue.size() - 1);
            log.info("【出价结束|好车推荐】 如果最大值大于出价值，返回尾*项：" + returnRecommendValue);
        } else {
            //如果出价值介于最大值和最小值区间
            for (int i = 0; i < listSortRecommendValue.size(); i++) {
                //如果出价在0到10万之间
                if (bidprice >= 0.0 && bidprice < 100000.0) {
                    if (listSortRecommendValue.get(i).getElaPrice() > bidprice * 0.9) {
                        returnRecommendValue = listSortRecommendValue.subList(i, listSortRecommendValue.size() - 1);
                        log.info("【出价结束|好车推荐】 0到10万 如果出价值介于最大值和最小值区间：" + returnRecommendValue.size());
                        break;
                    }
                } else if (bidprice >= 100000.0 && bidprice < 200000.0) {
                    if (listSortRecommendValue.get(i).getElaPrice() > bidprice * 0.8) {
                        returnRecommendValue = listSortRecommendValue.subList(i, listSortRecommendValue.size() - 1);
                        log.info("【出价结束|好车推荐】 10到20万 如果出价值介于最大值和最小值区间：" + returnRecommendValue.size());
                        break;
                    }
                } else if (bidprice >= 200000.0 && bidprice < 300000.0) {
                    if (listSortRecommendValue.get(i).getElaPrice() > bidprice * 0.7) {
                        returnRecommendValue = listSortRecommendValue.subList(i, listSortRecommendValue.size() - 1);
                        log.info("【出价结束|好车推荐】 20到30万如果出价值介于最大值和最小值区间：" + returnRecommendValue.size());
                        break;
                    }
                } else {
                    if (listSortRecommendValue.get(i).getElaPrice() > bidprice * 0.6) {
                        returnRecommendValue = listSortRecommendValue.subList(i, listSortRecommendValue.size() - 1);
                        log.info("【出价结束|好车推荐】30万以上 如果出价值介于最大值和最小值区间：" + returnRecommendValue.size());
                        break;
                    }
                }
            }
        }
        return returnRecommendValue;

    }

    /**
     * 当前竞拍车辆 车主降价车辆
     * @return
     */
    public List<String> queryIsBiddingAndReductionCarFromRedis(){
        // 获取正在竞拍的车辆
        // 拼接key值
        String biddingCar = "";
        String reduction = "";
        try{
            biddingCar = redisService.get(RecomConstants.BUYER_IS_SETTING_CARA).replaceAll(" ","");

            reduction =  redisService.get(RecomConstants.BUYER_IS_REDUCTION_CARA).replaceAll(" ","");

        }catch (Exception e){
            log.error("【查询服务】查询的redis的key{}失败！！",e);
        }
        // 定义一个集合，存入正在竞拍的车辆
        List<String> setIsBidding ;
        if (biddingCar.contains(",")) {
            setIsBidding = Arrays.asList(biddingCar.split(","));
        } else {
            setIsBidding = Arrays.asList(biddingCar);
        }
        if (log.isDebugEnabled()) {
            log.debug("【查询服务】查询的正在竞拍车辆 {}", setIsBidding.size());
        }

        // 定义一个集合，存入车主降价的车辆
        List<String> setIsReduction ;
        if (reduction.contains(",")) {
            setIsReduction = Arrays.asList(reduction.split(","));
        } else {
            setIsReduction = Arrays.asList(reduction);
        }
        if (log.isDebugEnabled()) {
            log.debug("【查询服务】查询车主降价车辆 {}", setIsReduction.size());
        }
        // 当前竞拍车辆和车主降价车辆合并
        List<String> setIsBiddingList = new ArrayList<>(setIsBidding);
        List<String> setIsReductionList = new ArrayList<>(setIsReduction);
        setIsBiddingList.addAll(setIsReductionList);

        if (log.isDebugEnabled()) {
            log.debug("【查询服务】查询的正在竞拍和车主降价车辆", setIsBiddingList.size());
        }

        if(setIsBiddingList==null){
            return new ArrayList<>();
        }
        return setIsBiddingList;
    }


}

