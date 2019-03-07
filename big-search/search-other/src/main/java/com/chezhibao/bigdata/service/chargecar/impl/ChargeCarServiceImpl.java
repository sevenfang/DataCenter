package com.chezhibao.bigdata.service.chargecar.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.common.aspect.annotation.InvokeTime;
import com.chezhibao.bigdata.common.log.LoggerUtils;
import com.chezhibao.bigdata.config.client.ParamsUtil;
import com.chezhibao.bigdata.dao.AucsCommonDao;
import com.chezhibao.bigdata.dbms.hbase.HbaseService;
import com.chezhibao.bigdata.dbms.hbase.bo.HbaseParam;
import com.chezhibao.bigdata.dbms.redis.ListRedisService;
import com.chezhibao.bigdata.dbms.redis.recommend.RecommendListRedisService;
import com.chezhibao.bigdata.search.common.SearchLogEnum;
import com.chezhibao.bigdata.search.common.util.AuctionCarUtils;
import com.chezhibao.bigdata.search.common.util.RedisKeyUtils;
import com.chezhibao.bigdata.search.es.bo.SearchPage;
import com.chezhibao.bigdata.service.chargecar.ChargeCarService;
import com.chezhibao.bigdata.service.chargecar.ObtainedCarService;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/7.
 */
@Service
public class ChargeCarServiceImpl implements ChargeCarService {

    private static final Logger log = LoggerUtils.Logger(SearchLogEnum.CHARGE_CAR);

    private static final String AUCS_ALL_RECOMMEND_CHARGECAR_PREFIX = "aucs:all:recommend:chargecar:";
    /**
     * 收车对应hbase表
     */
    private static final String HBASETABLE = "charge_car_auction";
    /**
     * 收车对应sessionName
     */
    private static final String SESSION_NAME = "chargeCar";
    /**
     * 收车的rowkey前缀
     */
    private static final String CHARGE_CAR_ROWKEY_PREFIX = "LR_";
    /**
     * 默认排序的所有车辆  Hbase没有对应的收车排序车辆时用这个排序车辆
     */
    public static final String AUCS_RECOMMEND_CHARGECAR_DEFAULT ="aucs:recommend:chargecar:default";


    /**
     * 所有收车场推荐数据的来源参数名称 目前有hbase 和 redis
     */

    private static final String AUCS_CHARGECAR_RECOMMEND_DATA_SOURCE="recommend.aucs_chargecar_recommend_data_source";

    private ObtainedCarService obtainedCarService;

    @Reference(check = false)
    private HbaseService hbaseService;

    @Reference(check = false)
    private ListRedisService listRedisService;

    @Reference(check = false)
    private RecommendListRedisService recommendListRedisService;

    private SqlTemplateService sqlTemplateService;

    @Reference(check = false)
    private AucsCommonDao commonDao;

    public ChargeCarServiceImpl(ObtainedCarService obtainedCarService,
                                SqlTemplateService sqlTemplateService) {
        this.obtainedCarService = obtainedCarService;
        this.sqlTemplateService = sqlTemplateService;
    }

    @Override
    @InvokeTime
    public void firstPageHandler(Integer buyerId,Integer count) {
        String dynamicValue = ParamsUtil.getDynamicValue(AUCS_CHARGECAR_RECOMMEND_DATA_SOURCE);
        List<Integer> recommendCar;
        log.info("【查询系统】收车场推荐车辆获取来源{}",dynamicValue);
        switch (dynamicValue){
            case "hbase":
                recommendCar=getChargeCarFromHbase(buyerId);
                break;
            case "redis":
                recommendCar=getChargeCarFromRedis(buyerId);
                break;
            default:
                recommendCar=getChargeCarFromHbase(buyerId);

        }
        if(ObjectUtils.isEmpty(recommendCar)){
            //获取默认的车辆
            recommendCar= getDefaultCar();
        }
        //获取下架车
        List<Integer> obtainedCars = obtainedCarService.getObtainedCars();
        if(log.isDebugEnabled()) {
            log.debug("【查询系统】获取下架车结果：{}", obtainedCars);
        }
        recommendCar.removeAll(obtainedCars);
        if(recommendCar.size()>count){
            recommendCar = recommendCar.subList(0, count);
        }

        //将结果车辆存入Redis
        String key = RedisKeyUtils.getReCommendCarInfoQueryKey(buyerId, SESSION_NAME);
        listRedisService.delete(key);
        listRedisService.addAll(key,AuctionCarUtils.transToString(recommendCar));
    }

    /**
     * 从REDIS获取收车场推荐车辆
     * @param buyerId
     * @return
     */
    private List<Integer> getChargeCarFromRedis(Integer buyerId){
        List<String> recommendCar = recommendListRedisService.get(AUCS_ALL_RECOMMEND_CHARGECAR_PREFIX + buyerId);
        if(log.isDebugEnabled()) {
            log.debug("【查询系统】获取REDIS推荐收车结果：{}", recommendCar);
        }
        return AuctionCarUtils.transStringToInteger(recommendCar);
    }

    /**
     * 从Hbase获取收车场推荐车辆
     * @param buyerId
     * @return
     */
    private List<Integer> getChargeCarFromHbase(Integer buyerId){
        String rowKey = CHARGE_CAR_ROWKEY_PREFIX+ buyerId;
        HbaseParam<String> hbaseParam = new HbaseParam<>();
        hbaseParam.setRowKey(rowKey);
        hbaseParam.setTableName(HBASETABLE);
        //获取hbase中的推荐车辆
        String string = hbaseService.getString(hbaseParam);
        List<Integer> recommendCar = JSON.parseArray(string, Integer.class);
        if(log.isDebugEnabled()) {
            log.debug("【查询系统】获取HBASE推荐收车结果：{}", recommendCar);
        }
        return recommendCar;
    }

    private List<Integer> getDefaultCar(){
        HashMap<String, Object> params = new HashMap<>();
        String sql = sqlTemplateService.getSql("car.car_aucs.auctionCountdownCar",params);
        //收车场次中-进入竞拍倒计时的车辆
        List<Map<String, Object>> aucs = commonDao.select(sql, params);
        Set<String> auctionCountdownCar=new LinkedHashSet<>();
        for(Map<String, Object> map : aucs){
            Object id = map.get("id");
            if(id!=null){
                auctionCountdownCar.add(id+"");
            }
        }
        List<String> strings = listRedisService.get(AUCS_RECOMMEND_CHARGECAR_DEFAULT);
        auctionCountdownCar.addAll(strings);
        return AuctionCarUtils.transStringToInteger(auctionCountdownCar);
    }

    @Override
    public List<Integer> getPageCar(Integer buyerId, SearchPage searchPage) {
        String key = RedisKeyUtils.getReCommendCarInfoQueryKey(buyerId, searchPage);
        Integer page = searchPage.getPage();
        Integer size = searchPage.getSize();
        //获取对应的页码
        Integer start = (page - 1) * size;
        Integer end = (page) * size - 1;
        //获取指定数据
        List<String> strings = listRedisService.get(key, start, end);
        if(log.isDebugEnabled()) {
            log.debug("【查询系统】收车推荐的查询key{},page:{},size:{},结果{}", key, page, size, strings);
        }
        return AuctionCarUtils.transStringToInteger(strings);
    }

    @Override
    public List<Integer> getAllChargeCar(Integer buyerId, Integer count) {
        SearchPage searchPage = new SearchPage(1,count);
        searchPage.setSessionName("chargeCar");
        return getPageCar(buyerId,searchPage);
    }
}
