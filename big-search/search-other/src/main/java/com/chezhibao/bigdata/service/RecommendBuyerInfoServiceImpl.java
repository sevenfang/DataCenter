package com.chezhibao.bigdata.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chezhibao.bigdata.common.aspect.annotation.InvokeTime;
import com.chezhibao.bigdata.dbms.hbase.HbaseService;
import com.chezhibao.bigdata.dbms.hbase.bo.HbaseParam;
import com.chezhibao.bigdata.search.recommend.RecommendBuyerInfoService;
import com.chezhibao.bigdata.search.recommend.bo.RecommendBidCarIfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/27.
 */
@Service(interfaceClass = RecommendBuyerInfoService.class)
@Component
@Slf4j
public class RecommendBuyerInfoServiceImpl implements RecommendBuyerInfoService {

    /**
     * 渠道推荐商户
     */
    public static final String TABLE_NAME="channel_recommend_buyer";

    /**
     * 根据车编返回推荐商户  对接人：柯文 Hbase表名:CHANNEL_RECOMMEND_BUYER cf:car
     */
    public static final String RECOMMEND_BY_CARID_KEY_PREFIX="recommend_by_carid_";
    /**
     * 根据渠道人员ID获取车辆推荐给商户 对接人：柯文 Hbase表名:CHANNEL_RECOMMEND_BUYER cf:channel
     */
    public static final String RECOMMEND_BY_CHANNELID_KEY_PREFIX="recommend_by_channelid_";


    @Reference(check = false)
    private HbaseService hbaseService;

    @Override
    public List<String> getBuyerIdsByCarId(Integer carId,Integer channelId) {
    log.debug("【搜索系统】根据车编返回推荐商户,入参：carId:{};channelId:{}",carId,channelId);
        HbaseParam<String> param = new HbaseParam<>();
        param.setRowKey(RECOMMEND_BY_CARID_KEY_PREFIX + carId+"_"+channelId);
        param.setTableName(TABLE_NAME);
        param.setCf("car");
        String string = hbaseService.getString(param);
        if(StringUtils.isEmpty(string)){
            return new ArrayList<>(0);
        }
        List<String> result = JSON.parseObject(string,new TypeReference<ArrayList<String>>(){});
        log.debug("【搜索系统】根据车编返回推荐商户,返回值：{}",result);
        return result;
    }

    @Override
    public List<RecommendBidCarIfo> getRecommendBidCarIfo(Integer selUserId) {
        log.debug("【搜索系统】根据车辆推荐给商户,入参：{}",selUserId);
        HbaseParam<String> param = new HbaseParam<>();
        param.setRowKey(RECOMMEND_BY_CHANNELID_KEY_PREFIX + selUserId);
        param.setTableName(TABLE_NAME);
        param.setCf("channel");
        String string = hbaseService.getString(param);
        if(StringUtils.isEmpty(string)){
            return new ArrayList<>(0);
        }
        List<RecommendBidCarIfo> result = JSON.parseObject(string,new TypeReference<ArrayList<RecommendBidCarIfo>>(){});
        log.debug("【搜索系统】根据车辆推荐给商户,返回值：{}",result);
        return result;
    }

    @Override
    @InvokeTime
    public List<RecommendBidCarIfo> getCuocheRecommendBidCarIfo(Integer selUserId) {
        log.debug("【搜索系统】根据车辆(搓车)推荐给商户,入参：{}",selUserId);
        HbaseParam<String> param = new HbaseParam<>();
        param.setRowKey(RECOMMEND_BY_CHANNELID_KEY_PREFIX + selUserId);
        param.setTableName(TABLE_NAME);
        param.setCf("channel");
        param.setQualifier("cuoche");
        String string = hbaseService.getString(param);
        if(StringUtils.isEmpty(string)){
            return new ArrayList<>(0);
        }
        List<RecommendBidCarIfo> result = JSON.parseObject(string,new TypeReference<ArrayList<RecommendBidCarIfo>>(){});
        log.debug("【搜索系统】根据车辆(搓车)推荐给商户,返回值：{}",result);
        return result;
    }

    @Override
    @InvokeTime
    public List<Integer> getBuyIdsByCarId(Integer carId) {
        List<Integer> result = new ArrayList<>();
        log.debug("【搜索系统】根据车编返回推荐商户,入参：carId:{};",carId);
        HbaseParam<String> param = new HbaseParam<>();
        param.setRowKey(RECOMMEND_BY_CARID_KEY_PREFIX + carId);
        param.setTableName(TABLE_NAME);
        param.setCf("car");
        param.setQualifier("buyerIds");
        String string = hbaseService.getString(param);
        if(!StringUtils.isEmpty(string)){
            result = JSON.parseObject(string,new TypeReference<ArrayList<Integer>>(){});
        }
        log.debug("【搜索系统】根据车编返回推荐商户,返回值：{}",result);
        return result;
    }
}
