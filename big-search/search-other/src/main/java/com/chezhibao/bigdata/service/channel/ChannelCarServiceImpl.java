package com.chezhibao.bigdata.service.channel;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.common.aspect.annotation.InvokeTime;
import com.chezhibao.bigdata.common.log.LoggerUtils;
import com.chezhibao.bigdata.dbms.hbase.HbaseService;
import com.chezhibao.bigdata.dbms.hbase.bo.HbaseParam;
import com.chezhibao.bigdata.dbms.redis.IRedisService;
import com.chezhibao.bigdata.dbms.redis.ListRedisService;
import com.chezhibao.bigdata.recommend.RecomConstants;
import com.chezhibao.bigdata.search.common.SearchLogEnum;
import com.chezhibao.bigdata.search.common.util.AuctionCarUtils;
import com.chezhibao.bigdata.search.common.util.RedisKeyUtils;
import com.chezhibao.bigdata.search.es.bo.SearchPage;
import com.chezhibao.bigdata.search.rdbms.dao.RdbmsDao;
import com.chezhibao.bigdata.search.recommend.channel.ChannelCarService;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/30.
 * @Slf4j
 */

@Service(interfaceClass = ChannelCarService.class)
@Component
public class ChannelCarServiceImpl implements ChannelCarService {
    private static final Logger log = LoggerUtils.Logger(SearchLogEnum.CHANNEL_CAR);
    /**
     *  渠道车辆查询 Hbase 表
     */
    private static final String CHANNEL_CAR_AUCTION = "channel_car_auction";
    /**
     * 渠道查询商户的rowkey 前缀
     */
    private static final String CHANNEL_STAFF_ROWKEY_PREFIX = "channelstaffid_";

    /**
     * 渠道查询商户的rowkey 前缀
     */
    private static final String CHANNEL_STAFF_ID_AND_FLAG_ROWKEY_PREFIX = "channelstaffidandflag_";

    /**
     * 渠道查询商户的rowkey 前缀
     */
    private static final String CHANNEL_BUYER_ROWKEY_PREFIX = "buyerid_";
    /**
     * 渠道查询商户的缓存 前缀
     */
    private static final String CHANNEL_REDIS_KEY_PREFIX = "channel:staff:";


    @Reference(check = false)
    private IRedisService redisService;
    private RdbmsDao rdbmsDao;
    @Reference(check = false)
    private ListRedisService listRedisService;
    @Reference(check = false)
    private HbaseService hbaseService;

    public ChannelCarServiceImpl(RdbmsDao rdbmsDao) {
        this.rdbmsDao = rdbmsDao;
    }

    /**
     *  渠道工单-根据渠道人员id获取商户list
     * @param channelId
     * @return
     */
    @Override
    public List<Integer> getAllBuyersByChannelId(Integer channelId) {
        //获取推荐商户map的keyset
        Set<Integer> integers = getAllBuyersByVisitedInfo(channelId).keySet();
        return new ArrayList<>(integers);
    }

    /**
     *  渠道工单-根据渠道人员id获取商户 map<buyerid,flag>
     * @param channelId
     * @return
     */
    @Override
    public Map<Integer, Boolean> getAllBuyersByVisitedInfo(Integer channelId) {
        if (log.isDebugEnabled()) {
            log.debug("【渠道|推荐商户】渠道人员id：{} ", channelId);
        }
        String redisKey = CHANNEL_REDIS_KEY_PREFIX + channelId;
        //查询Redis 判断有没有
        List<String> buyerIds = listRedisService.get(redisKey);
        //返回的值
        Map<Integer, Boolean> recommendList = new LinkedHashMap<>();
        if(ObjectUtils.isEmpty(buyerIds)){
            //redis没有查询Hbase
            HbaseParam<String> param = new HbaseParam<>();
            param.setTableName(CHANNEL_CAR_AUCTION);
            String rowKey = CHANNEL_STAFF_ID_AND_FLAG_ROWKEY_PREFIX + channelId;
            param.setRowKey(rowKey);
            String string = hbaseService.getString(param);
            if(StringUtils.isEmpty(string)){
                if (log.isDebugEnabled()) {
                    log.debug("【渠道|推荐商户】渠道人员 {} 查询{}中的rowKey：{} 值为空", channelId, CHANNEL_CAR_AUCTION, rowKey);
                }
                // 获取默认推荐商户
                List<Map<String, Object>> defaultBuyer = rdbmsDao.getBuyerByChannelId(channelId);
                List<Integer> defaultBuyerList = new ArrayList<>();
                for (Map<String, Object> buyerList : defaultBuyer) {
                    int buyerId = (int)buyerList.get("ID");
                    Boolean flag = buyerId%2==0;
                    recommendList.put(buyerId,flag);
                }
                if (log.isDebugEnabled()) {
                    log.debug("【渠道|推荐商户】渠道id：{}默认推荐商户数量 {}", channelId, defaultBuyerList.size());
                }
                return recommendList;
            }
            // 获取的推荐商户列表
            List<String> tmpBuyerIds =  JSON.parseArray(string, String.class);
            for(String buyerInfo : tmpBuyerIds){
                String[] buyerInfoList = buyerInfo.split("_");
                int buyerId = Integer.parseInt(buyerInfoList[0]);
                // 1:上门拜访,0:电话拜访
                Boolean flag = buyerInfoList[1].equals("1");
                recommendList.put(buyerId,flag);
            }
            if (log.isDebugEnabled()) {
                log.debug("【渠道|推荐商户】 返回的推荐列表大小:{}", recommendList.size());
            }
            listRedisService.addAll(redisKey,tmpBuyerIds);
            //设置过期时间未一小时
            listRedisService.expire(redisKey,60*60*1000L);
        }else{
            // 获取redis中值
            for(String buyerInfo : buyerIds){
                String[] buyerInfoList = buyerInfo.split("_");
                Boolean flag = buyerInfoList[1].equals("1");
                recommendList.put(Integer.parseInt(buyerInfoList[0]),flag);
            }
        }
        return recommendList;
    }

    /**
     * 为渠道推荐需要开拓的品牌、等级、价格区间
     * @param staffId
     * @return
     */
    @Override
    public List<Map<String, Object>> getExploreBrandInfo(Integer staffId) {
        //定义一个list map
        List<Map<String, Object>> exploreCarList = new ArrayList<Map<String, Object>>();
        log.info("【渠道|推荐开拓商户】 渠道id:{}",staffId);
        try{
            List<Map<String, Object>> carInfo = rdbmsDao.getexploreCarBystaffId(staffId);
            log.info("【渠道|推荐开拓商户】 查询到的信息{}",carInfo);
            // 为商户推荐需要拓展的商户
            for (Map<String, Object> car : carInfo) {
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("carBrand", car.get("carBrand"));
                map.put("carRating", car.get("carRating"));
                map.put("priceArea", car.get("priceArea"));
                exploreCarList.add(map);
            }
        }catch (Exception e){
            // 获取数据异常
            log.info("【渠道|推荐开拓商户】 获取数据异常");
            return  new ArrayList<>();
        }
        log.info("【渠道|推荐开拓商户】 需要开通的品牌信息:{}",exploreCarList.size());
        return exploreCarList;
    }

    @Override
    public Map<Integer, Integer> getAllCarIdAndDetectedIdByBuyerId(Integer buyerId, SearchPage searchPage) {
        log.info("【渠道|推荐车辆】推荐系统全部车辆查询参数：buyerId：{},searchPage：{}",buyerId, searchPage);
        //返回的值
        Map<Integer, Integer> recommendList = new LinkedHashMap<>();
        // 获取推荐数据
        List<String> resResult =  queryChannelRecommendCars(buyerId, searchPage);
        log.info("【渠道|推荐车辆】获取推荐数据：resResult{}",resResult);
        // 遍历数据，存储到map中
        for(String car : resResult){
            if(car.contains("_")){
                String[] split = car.split("_");
                if(StringUtils.isEmpty(split[0]) || StringUtils.isEmpty(split[1])){
                    continue;
                }
                int auctionCarId = Integer.parseInt(split[0]);
                int detectId = Integer.parseInt(split[1]);
                recommendList.put(auctionCarId,detectId);
            }

        }
        log.info("【渠道|推荐车辆】推荐系统全部车辆查询参数：recommendList{}",recommendList);
        return recommendList;
    }

    /**
     *  渠道-工单 为商户推荐车辆
     * @param buyerId
     * @param searchPage
     * @return
     */
    public List<String> queryChannelRecommendCars(Integer buyerId, SearchPage searchPage) {
        //获取开始时间
        long startTime=System.currentTimeMillis();
        //定义一个list map
        //Map<Integer, Integer> recommendList = new HashMap<Integer, Integer>();
        List<String> recommendList ;

        //获取商户所在城市
        String cityId = "2072";
        try {
            cityId = redisService.get(RecomConstants.GOOD_BUYER_CITY_INFO);
            if (log.isDebugEnabled()) {
                log.debug("【渠道|推荐车辆】获取商户{}所在城市：{}", buyerId, cityId);
            }
        }catch (Exception e){
            log.error("【渠道|推荐车辆】获取商户所在城市异常");
        }

        //获取开始时间
        long startTime_zero_first=System.currentTimeMillis();

        List<Map<String, Object>> haveBiddingCars;
        // 获取车商已经出价过的车辆
        haveBiddingCars = rdbmsDao.getBuyerBiddingCarInfo(buyerId);
        // 定义一个集合，存入出价过车辆
        Set<Integer> set = new HashSet<>();
        for (Map<String, Object> haveBiddingCar : haveBiddingCars) {
            // 存入已经出过价的车辆
            set.add((int)haveBiddingCar.get("auctionCarId"));
        }

        //获取商户所在省份的所有城市
        List<String> cityList = new ArrayList<>();
        String buyerProvinceCityKey = com.chezhibao.bigdata.common.utils.StringUtils.assembledRedisKey(RecomConstants.GOOD_BUYER_PROVINCE_CITY_INFO, buyerId);
        try{
            String s = redisService.get(buyerProvinceCityKey);
            if(!StringUtils.isEmpty(s)){
                //cityList = JSON.parseArray(s,String.class);
                cityList = Arrays.asList(s.split(";"));
            }
        }catch (Exception e){
            log.error("【查询系统】渠道工单查询出错了buyerId={}！！",buyerId,e);
            cityList.add(cityId);
        }
        if (log.isDebugEnabled()) {
            log.debug("【同车位推荐-出价完成】与车商 {}同省份的城市有:{}", buyerId, cityList);
        }
        if (log.isDebugEnabled()) {
            log.debug("【渠道|推荐车辆】取出商户出价过车编：{}", set);
        }

        // 当前正在竞拍车辆
        List<String> setIsBidding = queryIsBiddingCarFromRedis();
        if (log.isDebugEnabled()) {
            log.debug("【渠道|推荐车辆】获取正在竞拍的车辆个数：{}", setIsBidding.size());
        }

        // 拼接key值
        String buyerAbilityFlagKey = com.chezhibao.bigdata.common.utils.StringUtils.assembledRedisKey(RecomConstants.BUYER_ABILITY_FLAG,buyerId);

        // 获取商户是否是新老用户
        String buyerAbilityFlagResult = "";
        try{
            buyerAbilityFlagResult = redisService.get(buyerAbilityFlagKey);
        }catch (Exception e){
            log.error("【查询服务】查询的redis的key{}失败！！",buyerAbilityFlagKey,e);
        }
        // 如果redis的值不为空
        if(buyerAbilityFlagResult !=null){
            if(buyerAbilityFlagResult.trim().equals("1")){
                //flag为1，是老用户
                if (log.isDebugEnabled()) {
                     log.debug("【猜你喜欢|好车推荐】为老用户进行推荐");
                }
                recommendList = forOldBuyerRecommend(buyerId,set,setIsBidding,cityId,cityList);
            }else{
                if (log.isDebugEnabled()) {
                    log.debug("【猜你喜欢|好车推荐】为新用户进行推荐");
                }
                recommendList = forNewBuyerRecommend(buyerId,set,cityId,setIsBidding,cityList);
            }
        }else{
            if (log.isDebugEnabled()) {
                log.debug("【猜你喜欢|好车推荐】为新用户进行推荐");
            }
            recommendList = forNewBuyerRecommend(buyerId,set,cityId,setIsBidding,cityList);
        }
        Integer page = searchPage.getPage();
        if (page == 1) {
            handlerChannelFirstPage(recommendList,buyerId,searchPage);
        }
        //查询redis获取推荐数据
        List<String> integers = queryChannelRedisRecommendCars(buyerId, searchPage);
        if (log.isDebugEnabled()) {
            log.debug("【渠道|推荐车辆】推荐系统全部车辆查询结果：carId：{}，buyerId:{},searchPage：{}", integers, buyerId, searchPage);
        }
        //获取结束时间
        long endTime=System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("【渠道|推荐车辆】 调用总运行时长：{} ms", (endTime - startTime));
        }
        if(integers == null){
            return new ArrayList<>();
        }
        return integers;
    }

    @InvokeTime
    private void handlerChannelFirstPage(List<String> auctionCarIds,Integer buyerId, SearchPage searchPage){
        String key = RedisKeyUtils.getChannelCommendCarInfoQueryKey(buyerId, searchPage);
        //判断 存入redis的值是否为空
        if(auctionCarIds.size()>0){
            //清空原先的数据
            listRedisService.delete(key);
            //最终将数据写入到redis
            listRedisService.addAll(key,
                    AuctionCarUtils.transToString(auctionCarIds));
            //添加过期时间
            listRedisService.expire(key, 3 * 60 * 60 * 1000L);
        }
    }

    public List<String> queryChannelRedisRecommendCars(Integer buyerId, SearchPage searchPage) {
        String channelKey = RedisKeyUtils.getChannelCommendCarInfoQueryKey(buyerId, searchPage);
        Integer channelPage = searchPage.getPage();
        Integer channelPize = searchPage.getSize();
        //获取对应的页码
        Integer start = (channelPage - 1) * channelPize;
        Integer end = (channelPage) * channelPize - 1;
        //获取指定数据
        List<String> strings = listRedisService.get(channelKey, start, end);
        if (log.isDebugEnabled()) {
            log.debug("【渠道|推荐车辆】全部推荐的查询key{},page:{},size:{},结果{}", channelKey, channelPage, channelPize, strings);
        }
        if(strings==null){
            return new ArrayList<>();
        }
        return AuctionCarUtils.transToString(strings);
    }


    /***
     * 对新用户进行推荐  Map<Integer, Integer>
     */
    public List<String> forNewBuyerRecommend(int buyerId,Set<Integer> set,String cityId,List<String> setIsBidding,List<String> cityList){

        //获取开始时间
        long startTime=System.currentTimeMillis();
        //定义一个list map
        //Map<Integer, Integer> recommendList = new HashMap<Integer, Integer>();
        List<String> recommendList = new ArrayList<>();

        //组装hbase查询参数
        HbaseParam<String> hbaseParam = new HbaseParam<>();
        //确定表名 这里是固定的
        hbaseParam.setTableName(CHANNEL_CAR_AUCTION);
        //确定rowkey这里可以是动态的
        hbaseParam.setRowKey(CHANNEL_BUYER_ROWKEY_PREFIX+buyerId);
        String s = hbaseService.getString(hbaseParam);
        //判断是否为空
        if(!StringUtils.isEmpty(s)){
            //不为空
            List<String>  recommendListTmp = JSON.parseArray(s, String.class);
            if (log.isDebugEnabled()) {
                log.debug("【渠道|推荐车辆】查询hbase返回的结果值大小：{}", recommendListTmp.size());
            }
            //判断查询的结果是否有6辆车
            if(recommendListTmp.size()>10){
                return recommendListTmp;
            }else{
                List<String> defaultList = queryDefaultCar(set);
                //去重
                defaultList.removeAll(recommendList);
                //组装
                recommendList.addAll(defaultList);
                return recommendList;
            }
        }else{
            // hbase中没有值时
            if (log.isDebugEnabled()) {
                log.debug("【渠道|推荐车辆】合并商户所属地区以及附近地区：{}", cityList);
            }
            // 获取竞拍车辆中附近城市 一、二级 车况的车辆
            List<Map<String, Object>> betterCarConditionList = rdbmsDao.getbetterCarConditionWithDetectId(cityList);
            if (log.isDebugEnabled()) {
                log.debug("【渠道|推荐车辆】推荐一、二级 车况的车辆为：{}", betterCarConditionList);
            }
            // 获取竞拍车辆中出价次数小于5次的车辆
            List<Map<String, Object>> bidNumLessFiveList = rdbmsDao.getbidNumLessFiveWithDetectId(cityList);
            if (log.isDebugEnabled()) {
                log.debug("【渠道|推荐车辆】推荐出价次数小于5次的车辆为：{}", bidNumLessFiveList);
            }
            betterCarConditionList.addAll(bidNumLessFiveList);
            if (log.isDebugEnabled()) {
                log.debug("【渠道|推荐车辆】根据条件推荐的车辆为：{}", betterCarConditionList);
            }

            //已经推荐的车辆集合
            Set<Integer> set_havingRecommend = new HashSet<>();
            // 推荐容易中标的车辆
            for (Map<String, Object> car : betterCarConditionList) {
                // 过滤已经出过价的车辆 并且 是正在竞拍的车辆
                if(!set.contains(car.get("id"))&&setIsBidding.contains(car.get("id"))&&!set_havingRecommend.contains(car.get("id"))){
                    recommendList.add((Integer)car.get("id")+"_"+(Integer)car.get("detectionId"));
                    set_havingRecommend.add((Integer) car.get("id"));
                }
            }
            // 做校验，如果推荐的车辆数小10辆，选择默认推荐
            if(recommendList.size()<10){
                // 获取默认推荐
                if (log.isDebugEnabled()) {
                    log.debug("【渠道推荐】根据规则推荐的车辆数小于10");
                }
                List<String> defaultList = queryDefaultCar(set);
                //去重
                defaultList.removeAll(recommendList);
                //组装
                recommendList.addAll(defaultList);
                if (log.isDebugEnabled()) {
                    log.debug("【渠道|推荐车辆】为新用户推荐的车辆列表：{}", recommendList);
                }
                return recommendList;
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("【渠道|推荐车辆】为新用户推荐的车辆列表：{}", recommendList);
        }
        //获取结束时间
        long endTime=System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("【渠道|推荐车辆】 接口forNewBuyerRecommend调用总运行时长：{} ms", (endTime - startTime));
        }
        return recommendList;
    }

    /***
     * 对老用户进行推荐
     * @param buyerId  商户id
     * @param set 已出价车辆
     */
    public List<String> forOldBuyerRecommend(int buyerId,Set<Integer> set,List<String> setIsBidding,String cityId,List<String> cityList){
        //获取开始时间
        long startTime=System.currentTimeMillis();
        // 返回值
        List<String> recommendList = new ArrayList<>();
        // 根据key查询value值
        //组装hbase查询参数
        HbaseParam<String> hbaseParam = new HbaseParam<>();
        //确定表名 这里是固定的
        hbaseParam.setTableName(CHANNEL_CAR_AUCTION);
        //确定rowkey这里可以是动态的
        hbaseParam.setRowKey(CHANNEL_BUYER_ROWKEY_PREFIX+buyerId);
        String s = hbaseService.getString(hbaseParam);

        //已经推荐的车辆集合
        Set<String> set_havingRecommend = new HashSet<>();
        //判断是否为空
        if(!StringUtils.isEmpty(s)){
            //不为空
            List<String>  recommendListTmp = JSON.parseArray(s, String.class);
            if (log.isDebugEnabled()) {
                log.debug("【渠道|推荐车辆】查询hbase返回的结果值大小：{}", recommendListTmp.size());
            }
            // 判断推荐车辆是否是正在竞拍的车辆
            for(String tmpValue : recommendListTmp){
                String tmpValueReplace = tmpValue.replaceAll("\"","");
                if(tmpValueReplace.contains("_")){
                    if(!set_havingRecommend.contains(tmpValueReplace)&&setIsBidding.contains(Integer.parseInt(tmpValue.replaceAll("\"","").split("_")[0]))){
                        recommendList.add(tmpValueReplace);
                        //存入已经推荐的
                        set_havingRecommend.add(tmpValueReplace);
                    }
                }
            }
        }else{
            if (log.isDebugEnabled()) {
                log.debug("【渠道|推荐车辆】查询hbase返回的结果为空");
            }
            // 为空，返回新商户的值
            return forNewBuyerRecommend(buyerId,set,cityId,setIsBidding,cityList);
        }
        if (log.isDebugEnabled()) {
            log.debug("【渠道|推荐车辆】 商户出价过车辆：{} ", set);
        }
        //log.info("【猜你喜欢|好车推荐】 被推荐的车辆为：{} ",redis_value);
        // 遍历HBase中推荐车辆
        // 如果HBase中存的值已经过期
        if(recommendList.size()==0){
            // 获取默认推荐
            if (log.isDebugEnabled()) {
                log.debug("【渠道|推荐车辆】 auctionCarId值已经过期");
            }
            return queryDefaultCar(set);
        }else{
            // 如果推荐车辆的个数小于10辆
            if(recommendList.size()<10){
                List<String> defaultList = queryDefaultCar(set);
                //去重
                defaultList.removeAll(recommendList);
                //组装
                recommendList.addAll(defaultList);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("【渠道|推荐车辆】 为老用户推荐车辆的个数为：{}", recommendList.size());
        }
        //获取结束时间
        long endTime_three=System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("【渠道|推荐车辆】 接口forOldBuyerRecommend调用总运行时长：{}", (endTime_three - startTime));
        }
        return recommendList;
    }

    public List<String> queryIsBiddingCarFromRedis(){
        // 获取正在竞拍的车辆
        // 拼接key值
        String biddingCar = "";
        try{
            biddingCar = redisService.get(RecomConstants.BUYER_IS_SETTING_CARA).replaceAll(" ","");
        }catch (Exception e){
            log.error("【查询服务】查询的redis的key{}失败！！",biddingCar,e);
        }
        // 定义一个集合，存入正在竞拍的车辆
        List<String> setIsBidding = new ArrayList<>();
        if (biddingCar.contains(",")) {
            setIsBidding = Arrays.asList(biddingCar.split(","));
        } else {
            setIsBidding = Arrays.asList(biddingCar);
        }
        if(setIsBidding==null){
            return new ArrayList<>();
        }
        return setIsBidding;
    }

    /***
     * 获取默认推荐车辆，取一级、二级车
     * @param set 已经出过价车辆的集合
     * @return
     */
    public  List<String>  queryDefaultCar(Set<Integer> set){
        // 当前正在竞拍车辆
        String biddingCar = "";
        try{
            biddingCar = redisService.get(RecomConstants.BUYER_AUCTIONCARID_DETECTID);
        }catch (Exception e){
            log.error("【查询服务】查询的redis的key{}失败！！",biddingCar,e);
        }
        // 定义一个集合，存入正在竞拍的车辆
        List<String> setIsBidding = new ArrayList<>();
        if (biddingCar.contains(",")) {
            setIsBidding = Arrays.asList(biddingCar.split(","));
        } else {
            setIsBidding = Arrays.asList(biddingCar);
        }

        if(setIsBidding.size()>50){
            setIsBidding = setIsBidding.subList(0,45);
        }
        return setIsBidding;
    }
}
