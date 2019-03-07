package com.chezhibao.bigdata.search.controller;

import com.chezhibao.bigdata.search.es.service.AucsElasticsearchService;
import com.chezhibao.bigdata.search.es.service.RecommendService;
import com.chezhibao.bigdata.search.similarcar.impl.SimilarCarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


/**
 * @author WangCongJun
 * @date 2018/5/14
 * Created by WangCongJun on 2018/5/14.
 */
@RestController
@RequestMapping("/simi")
public class SimiControoler {

    @Autowired
    private SimilarCarServiceImpl similarCarService;


    /**
     *  相似车辆
     * @param auctioncarid
     */
    @RequestMapping("/getsimilar/car/{auctioncarid}")
    public List<Integer>  getSimiRecommendCar(@PathVariable("auctioncarid") String auctioncarid){
        List<Integer> returnList =  similarCarService.getsimilarCarByauctionCarid(auctioncarid);
        return returnList;
    }

}