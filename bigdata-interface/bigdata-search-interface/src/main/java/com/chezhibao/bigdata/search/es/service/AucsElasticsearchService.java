package com.chezhibao.bigdata.search.es.service;

import com.chezhibao.bigdata.search.es.bo.RecommendInfo;

import java.util.List;

/**
 * @author WangCongJun
 * @date 2018/5/14
 * Created by WangCongJun on 2018/5/14.
 */
public interface AucsElasticsearchService {
    /**
     * 根据商户ID获取推荐车辆
     * @param buyerId 商户ID
     * @param page 页码（从1开始）
     * @param size 每页数量
     * @return
     */
    @Deprecated
    List<RecommendInfo> getRecommendInfoByBuyerId(Integer buyerId, Integer page, Integer size);

    /**
     * 根据商户ID获取推荐车辆
     * @param key 查询key 用查询条件拼装出来的额唯一key（同样的条件对应同一个key）
     * @param auctioncarIds 此次查询中用到的竞拍ID集合
     * @param buyerId 商户ID
     * @param page 页码（从1开始）
     * @param size 每页数量
     * @return
     */
    List<RecommendInfo> getRecommendInfoByBuyerId(String key, List<Integer> auctioncarIds,
                                                  Integer buyerId, Integer page, Integer size);

    /**
     * 根据商户ID获取推荐车辆
     * @param key 查询key 用查询条件拼装出来的额唯一key（同样的条件对应同一个key）
     * @param auctioncarIds 此次查询中用到的竞拍ID集合
     * @param buyerId 商户ID
     * @return
     */
    List<RecommendInfo> getRecommendInfoByBuyerId(String key, List<Integer> auctioncarIds, Integer buyerId);

    /**
     * 删除过期车辆
     * @param auctioncarid
     */
    void delExpiredCar(Integer auctioncarid);

    /**
     * 新增车辆推荐信息
     * @param auctionId auctionId
     * @param cartId 车辆Id
     */
    void addNewCarRecommendInfo(Integer auctionId, Integer cartId);

    /**
     *  好车推荐/ 渠道的车辆 推荐信息
     * @param buyerId 商户ID
     * @return
     */
    List<RecommendInfo> getRecommendInfoByBuyerId(Integer buyerId);

    /**
     *  竞拍大厅 /增加竞拍开始时间的排序
     * @param key 查询key 用查询条件拼装出来的额唯一key（同样的条件对应同一个key）
     * @param auctioncarIds 此次查询中用到的竞拍ID集合
     * @param buyerId 商户ID
     * @param flag     1、正序 2、反序 3、默认
     * @return
     */

    List<RecommendInfo> getRecommendInfoByBuyerIdSortByDate(String key, int flag, List<Integer> auctioncarIds,
                                                            Integer buyerId);

    /**
     * 竞拍出价结束后，根据规则返回推荐车辆
     * @param auctioncarId 竞拍车辆id
     * @param count 返回条目
     * @return
     */
    List<Integer> getGuessRecomnedInfo(String auctioncarId, double bidprice, String count);

    /**
     * @param buyerId buyerId
     * @param auctioncarId 竞拍车辆id
     * @param count 返回条目
     * @return
     */
    List<Integer> getGuessRecomnedInfo(int buyerId, String auctioncarId, double bidprice, String count);

}
