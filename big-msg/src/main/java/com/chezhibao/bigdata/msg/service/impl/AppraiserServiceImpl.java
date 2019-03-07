package com.chezhibao.bigdata.msg.service.impl;

import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.msg.bo.CarValuationBO;
import com.chezhibao.bigdata.msg.dao.AppraiserDao;
import com.chezhibao.bigdata.msg.dao.AppraiserPriceDao;
import com.chezhibao.bigdata.msg.pojo.Appraiser;
import com.chezhibao.bigdata.msg.pojo.AppraiserPrice;
import com.chezhibao.bigdata.msg.service.AppraiserService;
import com.chezhibao.bigdata.msg.vo.AppraiserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/10.
 */
@Service("appraiserServiceImpl")
@Slf4j
public class AppraiserServiceImpl implements AppraiserService {

    @Autowired
    private AppraiserPriceDao appraiserPriceDao;

    @Autowired
    private AppraiserDao appraiserDao;



    @Override
    public Appraiser selectAppraiser(String city) {
        Appraiser appraiser = new Appraiser();
        List<Map<String, Object>> maps = appraiserDao.queryAppraiserByCity(city);
        //判断是否有对应的估价师 没有就发个王亮亮(默认)
        appraiser.setUserName("王亮亮");
        appraiser.setUserId(1443);
        if (ObjectUtils.isEmpty(maps)) {
            log.info("【消息系统】{}没有查询到估价师，直接发送给王亮亮", city);
            return appraiser;
        }
        Map<String, Object> map = maps.get(0);
        Object userId = map.get("userId");
        Object userName = map.get("userName");
        if (ObjectUtils.isEmpty(userId)) {
            log.info("【消息系统】{}没有对应的估价师userId", city);
            return appraiser;
        }
        appraiser.setUserName(userName + "");
        appraiser.setUserId(Integer.parseInt(userId + ""));
        log.info("【消息系统】查询到估价师id:{},name:{}",userId,userName);
        return appraiser;
    }

    @Override
    public Boolean saveAppraiserPrice(AppraiserVO appraiserVO) {
        AppraiserPrice appraiserPrice = new AppraiserPrice();
        appraiserPrice.setCreatedTime(new Date());
        appraiserPrice.setAppraiserId(appraiserVO.getAppraiserId());
        appraiserPrice.setCarId(appraiserVO.getCarId());
        appraiserPrice.setPrice(appraiserVO.getPrice());
        appraiserPrice.setOcseId(appraiserVO.getOcseId());
        appraiserPrice.setAccessUrl(appraiserVO.getAccessUrl());
        String detectId = appraiserVO.getDetectId();
        //给检测单Id一个默认值
        if(StringUtils.isEmpty(detectId)){
            detectId="0";
        }
        appraiserPrice.setDetectId(detectId);
        String comment = appraiserVO.getComment();
        if(StringUtils.isEmpty(comment)){
            comment="";
        }
        appraiserPrice.setComment(comment);
        log.info("【消息系统】估价后入库参数{}", appraiserPrice);
        return appraiserPriceDao.save(appraiserPrice);
    }

    @Override
    public Boolean saveAppraiserPrice(CarValuationBO valuation) {
        AppraiserPrice appraiserPrice = new AppraiserPrice();
        appraiserPrice.setCreatedTime(new Date());
        appraiserPrice.setAppraiserId(Integer.parseInt(valuation.getAppraiserId()));
        appraiserPrice.setCarId(Integer.parseInt(valuation.getCarId()));
        appraiserPrice.setOcseId(Integer.parseInt(valuation.getOcseId()));
        appraiserPrice.setAccessUrl(valuation.getAccessUrl());
        String detectId = valuation.getDetectId();
        //给检测单Id一个默认值
        if(StringUtils.isEmpty(detectId)){
            detectId="0";
        }
        appraiserPrice.setDetectId(detectId);
        appraiserPrice.setPrice(0d);
        appraiserPrice.setComment("");
        log.info("【消息系统】估价后入库参数{}", appraiserPrice);
        return appraiserPriceDao.save(appraiserPrice);
    }

    @Override
    public Boolean bidPrice(AppraiserVO appraiserVO) {
        //转换数据
        AppraiserPrice appraiserPrice = new AppraiserPrice();
        appraiserPrice.setBidTime(new Date());
        appraiserPrice.setAppraiserId(appraiserVO.getAppraiserId());
        appraiserPrice.setCarId(appraiserVO.getCarId());
        appraiserPrice.setOcseId(appraiserVO.getOcseId());
        appraiserPrice.setDetectId(appraiserVO.getDetectId());
        String comment = appraiserVO.getComment();
        if(StringUtils.isEmpty(comment)){
            comment="";
        }
        appraiserPrice.setComment(comment);
        appraiserPrice.setPrice(appraiserVO.getPrice());
        //更新出价及出价时间
        appraiserPriceDao.updateAppraiserPrice(appraiserPrice);
        return true;
    }

    @Override
    public AppraiserPrice getAppraiserPrice(AppraiserVO appraiserVO) {
        AppraiserPrice appraiserPrice = new AppraiserPrice();
        appraiserPrice.setAppraiserId(appraiserVO.getAppraiserId());
        appraiserPrice.setCarId(appraiserVO.getCarId());
        appraiserPrice.setOcseId(appraiserVO.getOcseId());
        appraiserPrice.setDetectId(appraiserVO.getDetectId());
        List<AppraiserPrice> list = getAppraiserPrice(appraiserPrice);
        if(ObjectUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    @Override
    public AppraiserPrice getAppraiserPriceByDetectId(String detectId) {
        AppraiserPrice appraiserPrice = new AppraiserPrice();
        appraiserPrice.setDetectId(detectId);
        List<AppraiserPrice> list = getAppraiserPrice(appraiserPrice);
        if(ObjectUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<AppraiserPrice> getAppraiserPrice(AppraiserPrice appraiserPrice) {
        List<Map<String, Object>> maps = appraiserPriceDao.get(appraiserPrice);
        if (ObjectUtils.isEmpty(maps)) {
            return null;
        }
        return ObjectCommonUtils.map2Object(maps,AppraiserPrice.class);
    }

    @Override
    public Boolean checkIsSend(AppraiserVO appraiserVO) {
        AppraiserPrice appraiserPrice = getAppraiserPrice(appraiserVO);
        if(appraiserPrice==null){
            return null;
        }
        Integer status = appraiserPrice.getStatus();
        return status==1;
    }

    @Override
    public Boolean updateStatusToSent(AppraiserVO appraiserVO) {
        AppraiserPrice appraiserPrice = new AppraiserPrice();
        appraiserPrice.setStatus(1);
        appraiserPrice.setAppraiserId(appraiserVO.getAppraiserId());
        appraiserPrice.setCarId(appraiserVO.getCarId());
        appraiserPrice.setOcseId(appraiserVO.getOcseId());
        appraiserPrice.setDetectId(appraiserVO.getDetectId());
        appraiserPriceDao.updateAppraiserStatus(appraiserPrice);
        return true;
    }

    @Override
    public String getAppraiseUrlByCarId(Integer carId) {
        AppraiserPrice appraiserPrice = new AppraiserPrice();
        appraiserPrice.setCarId(carId);
        List<Map<String, Object>> maps = appraiserPriceDao.get(appraiserPrice);
        log.info("【消息系统】查询的估价页面条数：{}",maps.size());
        if(ObjectUtils.isEmpty(maps)){
            return null;
        }
        Map<String, Object> map = maps.get(0);
        log.info("【消息系统】查询的估价页面记录：{}",map);
        return map.get("accessUrl") + "";
    }
}
