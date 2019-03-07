package com.chezhibao.bigdata.service.chargecar.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.common.log.LoggerUtils;
import com.chezhibao.bigdata.dbms.redis.IRedisService;
import com.chezhibao.bigdata.dbms.redis.ListRedisService;
import com.chezhibao.bigdata.search.common.SearchLogEnum;
import com.chezhibao.bigdata.search.common.util.AuctionCarUtils;
import com.chezhibao.bigdata.service.chargecar.ObtainedCarService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/7.
 */
@Service
public class ObtainedCarServiceImpl implements ObtainedCarService {
    private static final Logger log = LoggerUtils.Logger(SearchLogEnum.CHARGE_CAR);
    @Reference(check = false)
    private ListRedisService listRedisService;
    @Reference(check = false)
    private IRedisService redisService;


    @Override
    public List<Integer> getObtainedCars() {
        List<Integer> result = new ArrayList<>();
        List<String> strings = listRedisService.get(VEHICLEOFFTHESHELF);
        if(ObjectUtils.isEmpty(strings)){
            return result;
        }
        result = AuctionCarUtils.transStringToInteger(strings);
        if(log.isDebugEnabled()) {
            log.debug("【查询系统】获取下架车信息：{}", result);
        }
        return result;
    }

    @Override
    public void addObtainedCars(List<String> carIds) {
        log.info("参数carids：{}",carIds);
        if(ObjectUtils.isEmpty(carIds)){
            return;
        }
        try {
            Long aLong = listRedisService.addAll(VEHICLEOFFTHESHELF, carIds);
        }catch (Exception e){
            log.error("【查询系统】添加下架车出错了!{}",carIds,e);
        }
    }
}



