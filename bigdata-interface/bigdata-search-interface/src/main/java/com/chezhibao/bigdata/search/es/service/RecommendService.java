package com.chezhibao.bigdata.search.es.service;

import java.util.List;
import java.util.Map;

/**
 * @author YuanJianWeri
 * @date 2018/7/19
 * Created by YuanJianWeri on 2018/7/19.
 */
public interface RecommendService {

    /**
     * 竞拍首页，好车推荐
     * @param buyerId 商户id
     * @return
     */
    List<Map<String, Object>> getBetterCarInfo(int buyerId);

    /**
     * 同类(级别)车推荐
     * @param mapValue
     * @param count
     * @return
     */
    Map<Integer, List<Integer>> recommendSameGradeCarInfo(int buyerId, Map<Integer, Double> mapValue, int count);


    /**
     * 出价结束，好车推荐
     * @param buyerId 商户id
     * @param auctioncarid 竞拍车辆id
     * @param bidprice 价格
     * @return
     */
    List<Map<String, Object>> getBidFinishRecommemdCarInfo(int buyerId,String auctioncarid, double bidprice);

    /**
     * 订阅推荐
     * @param buyerId 商户id
     * @param count   返回车辆数
     * @return
     */
    List<Map<String, Object>> getSubscriptRecommemdCarInfo(int buyerId,int count);
}
