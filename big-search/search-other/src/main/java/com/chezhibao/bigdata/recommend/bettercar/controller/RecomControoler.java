package com.chezhibao.bigdata.recommend.bettercar.controller;

import com.chezhibao.bigdata.search.es.bo.SearchPage;
import com.chezhibao.bigdata.search.es.service.AucsElasticsearchService;
import com.chezhibao.bigdata.search.es.service.RecommendService;
import com.chezhibao.bigdata.search.es.service.impl.AucsDbServiceImpl;
import com.chezhibao.bigdata.search.rdbms.dao.RdbmsDao;
import com.chezhibao.bigdata.service.auctioncar.AuctionCarInfoToRedisServiceImpl;
import com.chezhibao.bigdata.service.channel.ChannelCarServiceImpl;
import com.chezhibao.bigdata.service.chargecar.impl.ChargeCarServiceImpl;
import com.chezhibao.bigdata.service.chargecar.impl.ObtainedCarServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/5/14
 * Created by WangCongJun on 2018/5/14.
 */
@RestController
@RequestMapping("/recommend")
public class RecomControoler {


    @Autowired
    private RecommendService recommendService;

    @Autowired
    private AucsElasticsearchService aucsElasticsearchService;


    @Autowired
    private RdbmsDao rdbmsDao;

    @Autowired
    private ChannelCarServiceImpl channelCarService;

    @Autowired
    private ChargeCarServiceImpl chargeCarService;

    @Autowired
    private AuctionCarInfoToRedisServiceImpl auctionCarInfoToRedisService;

    @Autowired
    private ObtainedCarServiceImpl obtainedCarService;

    // 调用其他非interface里面的东西，需要用autowrited注入
    @Autowired
    private AucsDbServiceImpl aucsDbService;



    /**
     * 更新redis中存储的index和type
     *
     * @param buyerid
     * @return
     */
    @RequestMapping("/bettercar/{buyerid}")
    public List<Map<String, Object>> getBetterCarInfo(@PathVariable("buyerid") int buyerid){
        List<Map<String, Object>> list = recommendService.getBetterCarInfo(buyerid);
        //通过排量等获取竞拍车辆
        return list;
    }

    /**
     *
     *
     * @param
     * @return
     */
    @RequestMapping("/guess/{count}")
    public Map<Integer, List<Integer>> getGuessInfo(@PathVariable("count") int count){
        Map<Integer, Double> mapValue =  new HashMap<Integer, Double>();
        mapValue.put(1282,10000.0);
        mapValue.put(1281,10000.0);
        Map<Integer, List<Integer>> returnList = recommendService.recommendSameGradeCarInfo(301,mapValue,2);
        //List<Map<String, Object>> list = recommendService.getBetterCarInfo(buyerid);
        //通过排量等获取竞拍车辆
        return returnList;
    }

    /**
     *
     *
     * @param
     * @return
     */
    @RequestMapping("/guess/single/{buyerid}/{auctioncarid}/{bidprice}")
    public List<Integer> getSingleGuessInfo(@PathVariable("buyerid") int buyerid,@PathVariable("auctioncarid") String auctioncarid,@PathVariable("bidprice") double bidprice){
        List<Integer> list = aucsElasticsearchService.getGuessRecomnedInfo(buyerid,auctioncarid,bidprice,"20");
       //通过排量等获取竞拍车辆
        return list;
    }

    @RequestMapping("/guess/single/test/{buyerid}/{auctioncarid}/{bidprice}")
    public List<Integer> getSingleGuessInfoTest(@PathVariable("buyerid") int buyerid,@PathVariable("auctioncarid") String auctioncarid,@PathVariable("bidprice") double bidprice){
        List<Integer> list = aucsDbService.getGuessRecomnedInfoTest(buyerid,auctioncarid,bidprice,"20");
        //通过排量等获取竞拍车辆
        return list;
    }

    @RequestMapping("/bidfinish/recommend/{buyerid}/{auctioncarid}/{bidprice}")
    public List<Map<String, Object>> getBetterCarInfo(@PathVariable("buyerid") int buyerid,@PathVariable("auctioncarid") String auctioncarid,@PathVariable("bidprice") double bidprice){
         List<Map<String, Object>> list = recommendService.getBidFinishRecommemdCarInfo(buyerid,auctioncarid,bidprice);
        //通过排量等获取竞拍车辆
        return list;
    }

    @RequestMapping("/subscribe/recommend/{buyerid}/{count}")
    public List<Map<String, Object>> getSubscriptRecommemdCarInfo(@PathVariable("buyerid") int buyerid,@PathVariable("count") int count){
        List<Map<String, Object>> list = recommendService.getSubscriptRecommemdCarInfo(buyerid,count);
        return list;
    }

    @RequestMapping("/channel/staff/{channelId}")
    public List<Integer> getChannelRecBuyer(@PathVariable("channelId") int channelId){
        List<Integer> list = channelCarService.getAllBuyersByChannelId(channelId);
        return list;
    }

    @RequestMapping("/channel/buyer/{buyerId}/{page}/{size}")
    public Map<Integer, Integer> getChannelRecCarByBuyer(@PathVariable("buyerId") int buyerId,@PathVariable("page") int page,@PathVariable("size") int size){
        SearchPage searchPage = new SearchPage(page,size);
        Map<Integer, Integer> list = channelCarService.getAllCarIdAndDetectedIdByBuyerId(buyerId,searchPage);
        return list;
    }

    @RequestMapping("/receivecar/{buyerId}/{page}/{size}")
    public List<Integer> getRecieveCar(@PathVariable("buyerId") int buyerId,@PathVariable("page") int page,@PathVariable("size") int size){
        SearchPage searchPage = new SearchPage(page,size);
        List<Integer> list = chargeCarService.getPageCar(buyerId,searchPage);
        return list;
    }

    /**
     *  车辆上架
     * @param auctioncarid
     * @param carmodelid
     */
    @RequestMapping("/putonsalecar/{auctioncarid}/{carmodelid}")
    public void getPutOnSaleCar(@PathVariable("auctioncarid") int auctioncarid,@PathVariable("carmodelid") int carmodelid){
        auctionCarInfoToRedisService.getPutOnSaleCar(auctioncarid,carmodelid);
    }

    /**
     * 获取下架车
     */
    @RequestMapping("/addobtainedcars")
    public void addObtainedCars(){
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("456");
        list.add("789");
        obtainedCarService.addObtainedCars(list);
    }
}