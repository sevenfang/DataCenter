package com.chezhibao.bigdata.search.es.controller;

import com.chezhibao.bigdata.search.es.service.AucsElasticsearchService;
import com.chezhibao.bigdata.search.rdbms.dao.RdbmsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WangCongJun
 * @date 2018/5/14
 * Created by WangCongJun on 2018/5/14.
 */
@RestController
@RequestMapping("/search/es")
public class AucsESControoler {




    @Autowired
    private RdbmsDao rdbmsDao;


    @Autowired
    private AucsElasticsearchService aucsElasticsearchService;

    @RequestMapping("/guess/single/{buyerid}")
    public List<Integer> getSingleGuessInfo(@PathVariable("buyerid") int buyerid){
        List<Integer> list = aucsElasticsearchService.getGuessRecomnedInfo(buyerid,"297515",1000.0,"2");
        //通过排量等获取竞拍车辆
        return list;
    }

}