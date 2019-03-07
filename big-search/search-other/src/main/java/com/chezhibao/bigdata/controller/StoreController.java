package com.chezhibao.bigdata.controller;

import com.chezhibao.bigdata.search.store.StoreService;
import com.chezhibao.bigdata.search.store.bo.StoreStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/3.
 */
@RestController
@RequestMapping("/search")
public class StoreController {
    @Autowired
    StoreService storeService;
    @RequestMapping("/store")
    public Object getCarIdsOfAbnormalStore(@RequestBody List<StoreStatusInfo> list){
       return storeService.getCarIdsOfAbnormalStore(list);
    }
}
