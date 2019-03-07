package com.chezhibao.bigdata.search.auction;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.common.log.LoggerUtils;
import com.chezhibao.bigdata.dao.AucsCommonDao;
import com.chezhibao.bigdata.dbms.redis.IRedisService;
import com.chezhibao.bigdata.dbms.redis.ListRedisService;
import com.chezhibao.bigdata.msg.AlarmService;
import com.chezhibao.bigdata.search.auction.service.AuctionCarInitService;
import com.chezhibao.bigdata.search.common.Constants;
import com.chezhibao.bigdata.search.common.SearchLogEnum;
import com.chezhibao.bigdata.search.common.util.AuctionCarUtils;
import com.chezhibao.bigdata.search.es.bo.RecommendInfo;
import com.chezhibao.bigdata.search.es.bo.SearchPage;
import com.chezhibao.bigdata.search.es.service.AucsESService;
import com.chezhibao.bigdata.template.ParamsBean;
import org.slf4j.Logger;

import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/8.
 */
@Service(interfaceClass = AucsESService.class)
@org.springframework.stereotype.Service
public class AucsESServiceV2Impl implements AucsESService ,AuctionCarInitService {

    private static final Logger log = LoggerUtils.Logger(SearchLogEnum.AUCTION_LOG);

    private static final String INDEXPREFIX = "personalized_recommend_";
    private static final String TYPEPREFIX = "remmend_info_";

    @Reference(check = false)
    private IRedisService redisService;
    @Reference(check = false)
    private ListRedisService listRedisService;
    private ReCommendCarInfoQueryService reCommendCarInfoQueryService;
    @Reference(check = false)
    private AlarmService alarmService;
    @Reference(check = false)
    private AucsCommonDao commonDao;

    public AucsESServiceV2Impl( ReCommendCarInfoQueryService reCommendCarInfoQueryService) {
        this.reCommendCarInfoQueryService = reCommendCarInfoQueryService;
    }



    @Override
    public void delExpiredCar(Integer integer) {
        List<Integer> list = new ArrayList<>();
        list.add(integer);
        delExpiredCar(list,"");
    }

    @Override
    public void addNewCar(Integer integer,String sessionName) {
        List<Integer> list = new ArrayList<>();
        list.add(integer);
        addNewCar(list,sessionName);
    }

    /**
     * 删除过期车辆
     * 1、获取
     * @param auctioncarid
     * @param sessionName
     */
    @Override
    public void delExpiredCar(List<Integer> auctioncarid,String sessionName) {
        log.info("【查询系统】删除下架车辆：{}", auctioncarid);
        List<String> strings = AuctionCarUtils.transToString(auctioncarid);
        try {
            listRedisService.addAll(Constants.AUCS_ALL_RECOMMEND_DEL, strings);
            listRedisService.removeAll(Constants.AUCS_ALL_RECOMMEND_DEFAULT+sessionName, strings);
        } catch (Exception e) {
            String msg = "【查询系统】删除过期车:" + strings + "出错了!" ;
            // TODO 添加告警通知
            log.error(msg, e);
            alarmService.sendAlarm("13365",msg);
        }
    }

    /**
     * 添加新车辆
     * 1、将新车添加到指定的场次缓存中
     * @param list
     * @param sessionName
     */
    @Override
    public void addNewCar(List<Integer> list,String sessionName) {
        log.info("【查询系统】新增车辆：{}", list);
        List<String> strings = AuctionCarUtils.transToString(list);
        try {
            listRedisService.addAll(Constants.AUCS_ALL_RECOMMEND_DEFAULT+sessionName, strings);
            listRedisService.addAll(Constants.AUCS_ALL_RECOMMEND_ADD, strings);
        } catch (Exception e) {
            String msg = "【查询系统】插入新增车:" + strings + "出错了";
            log.error(msg,e);
            alarmService.sendAlarm("13365",msg);
        }
    }

    /**
     * 豪华场、寄售场推荐
     *
     * @param list
     * @param integer
     * @return
     */
    @Override
    public List<RecommendInfo> getRecommendInfoByBuyerId(List<Integer> list, Integer integer) {
        return new ArrayList<>(0);
    }


    /**
     * ================================================================
     * 已筛选车辆推荐
     * 竞拍那边将客户筛选后的车辆全部传过来，
     * 1、第一页进行计算排序
     * 2、将排好序后的数据存入redis
     * 3、非第一页直接到redis中查询分页数据
     * ==================================================================
     * @param auctionCarIds
     * @param buyerId
     * @param searchPage
     * @return
     */
    @Override
    public List<RecommendInfo> getRecommendInfoByBuyerId(List<Integer> auctionCarIds, Integer buyerId, SearchPage searchPage) {
        List<Integer> integers;
        if(log.isDebugEnabled()){
            log.debug("【查询系统】推荐系统全部车辆查询参数：carId：{}，buyerId:{},searchPage：{}", auctionCarIds, buyerId, searchPage);
        }
        try {
            integers = reCommendCarInfoQueryService.queryRecommendCars(auctionCarIds, buyerId, searchPage);
        }catch (Exception e){
            String msg = "【查询系统】查询商户："+buyerId+"推荐车辆信息出错了:" + e.getMessage();
            log.error(msg,e);
            try {
                alarmService.sendAlarm("13365",msg);
            }catch (Exception e1){
                log.error("【查询系统】发送告警信息失败！");
            }
            integers = reCommendCarInfoQueryService.getDefaultResult(auctionCarIds, searchPage);
        }
        return AuctionCarUtils.tranIntetgerToRecommendInfos(integers);
    }

    /**
     * 场次全部车辆排序推荐
     * 1、根据场次名称获取全部车辆
     * 2、调用 已筛选车辆推荐 方法
     * @param buyerId
     * @param searchPage
     * @return
     */
    @Override
    public List<RecommendInfo> getRecommendInfoByBuyerId(Integer buyerId, SearchPage searchPage) {
        String sessionName = searchPage.getSessionName();
        log.info("【查询系统】推荐场次{}全部车辆查询参数：buyerId:{},searchPage：{}", sessionName, buyerId, searchPage);
        //查询场次下的所有车源信息
        List<String> carIds = listRedisService.get(Constants.AUCS_ALL_RECOMMEND_DEFAULT + sessionName);
        //调用现有的查询接口查询排序车辆
        return getRecommendInfoByBuyerId(AuctionCarUtils.transStringToInteger(carIds), buyerId,  searchPage);
    }

    /**
     * ================================================================
     * 初始化所有场次的车辆数据
     * 防止由于服务异常导致缓存中的车辆数据为空或不一致，
     * 需要初始化缓存场次的额车辆数据
     * ================================================================
     * @return
     */
    @Override
    public Boolean initAuctionCar() {
        //查询竞拍从库获取所有的场次及对应的车辆数据Map<String,List<Integer>>
        String sql = "SELECT s.`name` sessionName,c.id carId from ams_screen s " +
                     "LEFT JOIN ams_car c on s.id =c.screenId " +
                     "where s.`status`=502 and c.overTime>NOW() and c.flag=1 and c.operateMode=1";
        Map<String, Object> params = ParamsBean.newInstance().build();
        List<LinkedHashMap<String, Object>> query = commonDao.query(sql, params);
        Map<String,List<String>> allAuctionCarInfo = new HashMap<>();
        for(LinkedHashMap<String, Object> m : query){
            String sessionName = m.get("sessionName") + "";
            String carId = m.get("carId") + "";
            //判断场次名称是否已经存在结果中
            if(!allAuctionCarInfo.containsKey(sessionName)){
                //结果中添加该场次
                allAuctionCarInfo.put(sessionName,new ArrayList<>());
            }
            //将本场次的车辆ID添加到该场次中
            allAuctionCarInfo.get(sessionName).add(carId);
        }
        //将数据缓存到redis
        for(Map.Entry<String,List<String>> cars:allAuctionCarInfo.entrySet()){
            List<String> carIds = cars.getValue();
            String sessionName = cars.getKey();
            //缓存的key
            String key = Constants.AUCS_ALL_RECOMMEND_DEFAULT+sessionName;
            //清空原有的车辆
            listRedisService.delete(key);
            //将新数据存入缓存中
            listRedisService.addAll(key,carIds);
        }
        return true;
    }

    @Override
    public Object getCars(String sessionName) {
        //缓存的key
        String key = Constants.AUCS_ALL_RECOMMEND_DEFAULT+sessionName;
        return listRedisService.get(key);
    }
}
