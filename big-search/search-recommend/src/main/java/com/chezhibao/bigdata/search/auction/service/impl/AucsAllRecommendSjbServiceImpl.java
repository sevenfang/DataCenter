package com.chezhibao.bigdata.search.auction.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.common.aspect.annotation.InvokeTime;
import com.chezhibao.bigdata.common.log.LoggerUtils;
import com.chezhibao.bigdata.config.client.ParamsUtil;
import com.chezhibao.bigdata.dbms.hbase.HbaseService;
import com.chezhibao.bigdata.dbms.hbase.bo.HbaseParam;
import com.chezhibao.bigdata.dbms.redis.IRedisService;
import com.chezhibao.bigdata.dbms.redis.ListRedisService;
import com.chezhibao.bigdata.dbms.redis.recommend.RecommendListRedisService;
import com.chezhibao.bigdata.search.auction.service.AucsAllRecommendSjbService;
import com.chezhibao.bigdata.search.common.Constants;
import com.chezhibao.bigdata.search.common.SearchLogEnum;
import com.chezhibao.bigdata.search.common.util.AuctionCarUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/20.
 */
@Service
public class AucsAllRecommendSjbServiceImpl implements AucsAllRecommendSjbService {


    private static final Logger log = LoggerUtils.Logger(SearchLogEnum.AUCTION_LOG);

    private static final String TABLE_NAME="aucs_all_recommend_sjb";
    private static final String EXPOSURE_WITHOUT_BIDDING="filter_detail_car:";

    /**
     * 所有推荐数据的来源参数名称 目前有hbase 和 redis
     */

    private static final String AUCS_ALL_RECOMMEND_DATA_SOURCE="recommend.aucs_all_recommend_data_source";

    @Reference(check = false)
    private RecommendListRedisService listRedisService;
    @Reference(check = false)
    private IRedisService redisService;
    @Reference(check = false)
    private HbaseService hbaseService;

    @Override
    public List<Integer> getAllRecommendData(Integer buyerId) {
        String dynamicValue = ParamsUtil.getDynamicValue(AUCS_ALL_RECOMMEND_DATA_SOURCE);
        log.info("【查询系统】推荐车辆获取来源{}",dynamicValue);
        switch (dynamicValue){
            case "hbase":
                return getSJBRecommendCarInfoByBuyerIdFromHbase(buyerId);
            case "redis":
                return getSJBRecommendCarInfoByBuyerId(buyerId);
                default:
                    return getSJBRecommendCarInfoByBuyerId(buyerId);

        }
    }

    @Override
    @InvokeTime
    public List<Integer> getSJBRecommendCarInfoByBuyerIdFromHbase(Integer buyerId) {
        //定义返回数据
        List<Integer> result;
        if(log.isDebugEnabled()){
            log.debug("【查询系统】从HBASE查询商户{}数据部排序推荐车辆",buyerId);
        }
        //组装hbase查询参数
        HbaseParam<String> hbaseParam = new HbaseParam<>();
        //确定表名 这里是固定的
        hbaseParam.setTableName(TABLE_NAME);
        //确定roekey这里可以是动态的
        hbaseParam.setRowKey("LR_"+buyerId);
        String s = hbaseService.getString(hbaseParam);
        //初始化返回数据
        result = new ArrayList<>();
        //判断是否为空
        if(!StringUtils.isEmpty(s)){
            //不为空
            result = JSON.parseArray(s, Integer.class);
        }
        if(log.isDebugEnabled()){
            log.debug("【查询系统】从HBASE查询商户{}数据部排序推荐车辆结果:{}",buyerId,result);
        }
        return result;
    }

    @Override
    @InvokeTime
    public List<Integer> getSJBRecommendCarInfoByBuyerId(Integer buyerId) {
        //定义返回数据
        List<Integer> result;
        if(log.isDebugEnabled()){
            log.debug("【查询系统】从REDIS查询商户{}数据部排序推荐车辆",buyerId);
        }
        String key = Constants.AUCS_ALL_RECOMMEND_SJB_PREFIX + buyerId;
        List<String> strings = listRedisService.get(key);
        //初始化返回数据
        result = new ArrayList<>();
        if (!ObjectUtils.isEmpty(strings)) {
            result = AuctionCarUtils.transStringToInteger(strings);
        }
        if(log.isDebugEnabled()){
            log.debug("【查询系统】从REDIS查询商户{}数据部排序推荐车辆结果：{}",buyerId,result);
        }
        return result;
    }

    @Override
    @InvokeTime
    public List<Integer> getExposureWithoutBidding(Integer buyerId) {
        //组装key
        String key = EXPOSURE_WITHOUT_BIDDING + buyerId;
        //查询redis
        String s = "";
        try{
            s = redisService.get(key);
            log.info("【查询服务】查询的redis的key为{},结果：{}", key, s);
        }catch (Exception e){
            log.error("【查询服务】查询的redis的key{}失败！！",key,e);
        }
        //定义返回结果
        List<Integer> result = new ArrayList<>();
        if(StringUtils.isEmpty(s) || "[]".equals(s)){
            return result;
        }
        try {
            result = JSON.parseArray(s, Integer.class);
        }catch (Exception e){
            log.error("【查询服务】获取数据部曝光车辆出错！key{}",key,e);
        }
        //返回结果
        return result;
    }
}
