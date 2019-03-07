package com.chezhibao.bigdata.controller;

import com.chezhibao.bigdata.search.recommend.RecommendBuyerInfoService;
import com.chezhibao.bigdata.search.recommend.bo.RecommendBidCarIfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/19.
 */
@RestController
@RequestMapping("/RecommendBuyerInfoService")
public class RecommendBuyerInfoServiceController {
    @Autowired
    private RecommendBuyerInfoService recommendBuyerInfoService;
    @RequestMapping("getCuocheRecommendBidCarIfo")
    public List<RecommendBidCarIfo> getCuocheRecommendBidCarIfo(Integer selUserId) {
        return recommendBuyerInfoService.getCuocheRecommendBidCarIfo(selUserId);
    }
}
