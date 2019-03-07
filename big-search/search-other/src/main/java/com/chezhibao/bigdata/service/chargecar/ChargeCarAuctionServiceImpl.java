package com.chezhibao.bigdata.service.chargecar;

import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.common.aspect.annotation.InvokeTime;
import com.chezhibao.bigdata.common.log.LoggerUtils;
import com.chezhibao.bigdata.search.common.SearchLogEnum;
import com.chezhibao.bigdata.search.common.util.AuctionCarUtils;
import com.chezhibao.bigdata.search.es.bo.SearchPage;
import com.chezhibao.bigdata.search.es.service.ChargeCarAuctionService;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/7.
 */
@Component
@Service(interfaceClass = ChargeCarAuctionService.class)
public class ChargeCarAuctionServiceImpl implements ChargeCarAuctionService {
    private static final Logger log = LoggerUtils.Logger(SearchLogEnum.CHARGE_CAR);

    /**
     * 收车推荐车辆
     */
    private static final Integer CHARGE_CAR_COUNT=500;
    private ObtainedCarService obtainedCarService;

    private ChargeCarService chargeCarService;

    public ChargeCarAuctionServiceImpl(ObtainedCarService obtainedCarService,
                                       ChargeCarService chargeCarService) {
        this.obtainedCarService = obtainedCarService;
        this.chargeCarService = chargeCarService;
    }

    @Override
    @InvokeTime
    public List<Integer> getRecommendCarInfo(Integer buyerId, Integer count) {
        if(log.isDebugEnabled()) {
            log.debug("【查询系统】获取所有推荐收车buyerid:{},count:{}", buyerId, count);
        }
        List<Integer> allChargeCar = chargeCarService.getAllChargeCar(buyerId, count);
        if(ObjectUtils.isEmpty(allChargeCar)){
            log.error("【查询系统】获取所有推荐收车为空！buyerId：{}",buyerId);
        }
        if(log.isDebugEnabled()) {
            log.debug("【查询系统】获取所有推荐收车buyerid:{},result:{}", buyerId, allChargeCar);
        }
        return allChargeCar;
    }

    @Override
    @InvokeTime
    public List<Integer> getRecommendCarInfo(Integer buyerId, SearchPage searchPage) {
        if(log.isDebugEnabled()){
            log.debug("【查询系统】获取推荐收车参数：buyerid:{},searchPage:{}",buyerId,searchPage);
        }
        searchPage.setSessionName("chargeCar");
        Integer page = searchPage.getPage();
        if(page==1){
            chargeCarService.firstPageHandler(buyerId,CHARGE_CAR_COUNT);
        }
        List<Integer> pageCar = chargeCarService.getPageCar(buyerId, searchPage);
        if(log.isDebugEnabled()) {
            log.debug("【查询系统】获取page：{}推荐收车 buyerid:{},result:{}", page, buyerId, pageCar);
        }
        return pageCar;
    }

    @Override
    public void vehicleOffTheShelf(List<Integer> list) {
        List<String> strings = AuctionCarUtils.transToString(list);
        obtainedCarService.addObtainedCars(strings);

    }
}
