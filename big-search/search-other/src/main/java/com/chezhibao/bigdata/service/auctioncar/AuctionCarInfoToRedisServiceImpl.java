package com.chezhibao.bigdata.service.auctioncar;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.common.log.LoggerUtils;
import com.chezhibao.bigdata.dao.NewWareHouseDao;

import com.chezhibao.bigdata.dbms.redis.IRedisService;
import com.chezhibao.bigdata.dbms.redis.ListRedisService;
import com.chezhibao.bigdata.search.auction.AuctionCarInfoToRedisService;
import com.chezhibao.bigdata.search.common.SearchLogEnum;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/27.
 */
@Service(interfaceClass =AuctionCarInfoToRedisService.class )
@Component
public class AuctionCarInfoToRedisServiceImpl implements AuctionCarInfoToRedisService {
    private static final Logger log = LoggerUtils.Logger(SearchLogEnum.AUCTION_CAR);
    @Reference(check = false)
    private NewWareHouseDao newWareHouseDao;
    @Autowired
    private SqlTemplateService sqlTemplateService;
    @Reference(check = false)
    private IRedisService redisService;
    @Reference(check = false)
    private ListRedisService listRedisService;

    /***
     * 竞拍中车辆上架，竞拍车辆信息存储到redis中
     * @param auctionCarId
     * @param carModelId
     */
    @Override
    public void getPutOnSaleCar(Integer auctionCarId, Integer carModelId) {
          try{
              // 竞拍车辆id,车型id
              redisService.set("realtime:I2I:auctioncarid:"+auctionCarId+"",carModelId+"",72 * 60 * 60 * 1000L);
              // 拼接存入车型id的key值
              String carModelIdKey = "realtime:I2I:carmodelid:"+carModelId;
              // 获取当前竞拍车辆id
              List<String> auctionCarIdsList = listRedisService.get(carModelIdKey);
              // 新增竞拍车辆id
              auctionCarIdsList.add(auctionCarId+"");
              // 清空原有的车辆
              listRedisService.delete(carModelIdKey);
              // 将新车型id数据对应的auctioncarid 存入缓存中
              listRedisService.addAll(carModelIdKey,auctionCarIdsList);

          }catch (Exception e){
              log.error("【存储服务】添加redis当前竞拍车辆idkey为{},失败", auctionCarId, e);
          }
        log.info("【存储服务】添加redis当前竞拍车辆idkey为{},值为：{}", auctionCarId, carModelId);
    }
}
