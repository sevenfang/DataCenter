package com.chezhibao.bigdata.search.rdbms.dao;


import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/5/14
 * Created by WangCongJun on 2018/5/14.
 */
public interface RdbmsDao {

    List<Map<String, Object>> getGuessRecommdByBuyerCityBrandModel(int flag, String auctioncarid, double bidprice, int cityid, String count);

    /**
     * 猜你喜欢 出价结束后，根据规则
     * 有与商户出价车辆相同品牌、车系车辆并且出价次数小于10,并且选择与车商同省或市的车辆
     * @param auctioncarid
     * @param count
     * @return
     */
    List<Map<String, Object>> getGuessRecommdByBrandModel(int flag, String auctioncarid, double bidprice, List<Integer> cityList, String count);

    /**
     * 猜你喜欢 出价结束后，根据规则
     * 有与商户出价车辆相同品牌、车系车辆并且出价次数小于10,并且选择与车商同省的车辆
     * @param auctioncarid
     * @param count
     * @return
     */

    List<Map<String, Object>> getGuessRecommdByBuyerCityBidprice(String auctioncarid, double bidprice, int cityid, String count);
    /**
     * 猜你喜欢 出价结束后，根据规则
     * 有与商户出价车辆相同品牌、车系车辆并且出价次数小于10
     * @param auctioncarid
     * @param count
     * @return
     */
    List<Map<String, Object>> getGuessRecommdByBrandModelWithoutCity(int flag, String auctioncarid, double bidprice, String count);

    /**
     * 猜你喜欢 出价结束后，根据规则
     * 有与商户出价车辆相同品牌、车系车辆并且出价次数小于10
     * @param auctioncarid
     * @param count
     * @return
     */
    List<Map<String, Object>> getRecommdByBrandWithoutCity(String auctioncarid, double bidprice, String count);

    /**
     * 猜你喜欢 出价结束后，根据规则
     * 无与商户出价车辆相同品牌、车系车辆并且出价次数小于10，取相同价位的车辆,并且选择与车商同省或市的车辆
     * @param auctioncarid
     * @param count
     * @return
     */
    List<Map<String, Object>> getGuessRecommdByBidprice(String auctioncarid, double bidprice, List<Integer> cityList, String count);

    /**
     * 猜你喜欢 出价结束后，根据规则
     * 无与商户出价车辆相同品牌、车系车辆并且出价次数小于10，取相同价位的车辆
     * @param auctioncarid
     * @param count
     * @return
     */
    List<Map<String, Object>> getGuessRecommdByBidpriceWithoutCity(String auctioncarid, double bidprice, String count);

    /**
     * 猜你喜欢 出价结束后，根据规则
     * 无与商户出价车辆相同品牌、车系车辆并且出价次数小于10，取相同价位的车辆
     * @param auctioncarid
     * @param count
     * @return
     */
    List<Map<String, Object>> getGuessRecommdByBidpriceWithoutCityWithBrandMdelType(String auctioncarid, double bidprice, String count);

    /**
     * 根据竞拍车辆id,获取该车的估价均值
     * @param auctioncarid
     * @return
     */
    List<Map<String, Object>> getAuctioncarElvaPrice(String auctioncarid);


    /**
     * 猜你喜欢 出价结束后，根据规则
     * 有与商户出价车辆相同品牌、车系车辆并且出价次数小于10
     * @param auctioncarid
     * @param count
     * @return List<Integer>  hasBidCars,
     */
    List<Map<String, Object>> getGuessRecommdByBrandModelList(String auctioncarid, double bidprice, String count);

    /**
     * 猜你喜欢 出价结束后，根据规则
     * 无与商户出价车辆相同品牌、车系车辆并且出价次数小于10，取相同价位的车辆
     * @param auctioncarid
     * @param count
     * @return
     */
    List<Map<String, Object>> getGuessRecommdByBidpriceList(String auctioncarid, double bidprice, List<Integer> hasBidCars, String count);

    /**
     * 猜你喜欢 出价结束后，根据规则
     * 无与商户出价车辆相同品牌、车系车辆并且出价次数小于10，取相同价位的车辆
     * @param buyerId
     * @return
     */

    List<Map<String, Object>> getBuyerBiddingCarInfo(int buyerId);

    /**
     * 车商订阅的车系id
     * @param buyerId
     * @return
     */
    List<Map<String, Object>> getBuyerSubscriptCarSeries(int buyerId);

    /**
     * 车商订阅的品牌id
     * @param buyerId
     * @return
     */
    List<Map<String, Object>> getBuyerSubscriptCarBrand(int buyerId);


    /**
     * 猜你喜欢 出价结束后，根据规则
     * 无与商户出价车辆相同品牌、车系车辆并且出价次数小于10，取相同价位的车辆
     * @param auctionCarId
     * @return
     */

    List<Map<String, Object>> getBidddingCarModelType(String auctionCarId);


    /**
     * 当首页好车推荐没有推荐车辆时，给用户推荐默认车辆，limit
     * @return
     */
    List<Map<String, Object>> getDefaultCarInfo(int limit);

    /**
     * 获取车主降价的车辆
     * @return
     */
    List<Map<String, Object>> getReductionCarInfo();

    /**
     * 当首页好车推荐没有推荐车辆时，给用户推荐默认车辆，limit
     * @return
     */
    List<Map<String, Object>> getDefaultCarInfoWithDetect(int limit);

    /**
     * 当前竞拍车辆的车辆id
     * @return
     */
    public List<Map<String, Object>> getCaridofAuctioncar(int limit);



    /**
     * 当好车推荐没有推荐车辆时，在出价结束后给用户推荐默认车辆(价格相似)，limit为30
     * @return
     */
    List<Map<String, Object>> getBidFinishDefaultCarInfo(int limit, double minPrice, double maxPrice);


    /**
     * 同车位推荐 根据商户id 查询商户所在市
     * @param buyerId
     */

    List<Map<String, Object>> getBuyerCity(int buyerId);

    /**
     * 同车位推荐 根据商户id 查询商户所在省份的所有市
     * @param buyerId
     */
    List<Map<String, Object>> getBuyerCityList(int buyerId);

    /**
     * 个性化推荐 获取车商近3个月的出价记录
     * @param buyerId
     */

    List<Map<String, Object>> getBuyerBidNum(int buyerId);

    /**
     * 个性化推荐 获取车商近3个月的中标记录
     * @param buyerId
     */
    List<Map<String, Object>> getBuyerWinNum(int buyerId);


    /**
     *  对于新商户来说 获取车况比较好的车辆
     * @param regionList
     */
    public List<Map<String, Object>> getbetterCarCondition(List<String> regionList);

    /**
     *  对于新商户来说 获取车况比较好的车辆，包括检测单id
     * @param regionList
     */
    public List<Map<String, Object>> getbetterCarConditionWithDetectId(List<String> regionList);

    /**
     *  对于新商户来说 获取车况比较好的车辆
     * @param regionList
     */
    public List<Map<String, Object>> getbidNumLessFive(List<String> regionList);

    /**
     *  对于新商户来说 获取车况比较好的车辆，包括检测单id
     * @param regionList
     */
    public List<Map<String, Object>> getbidNumLessFiveWithDetectId(List<String> regionList);

    /**
     * 获取渠道对应的商户
     * @param ChannelId
     */
    List<Map<String, Object>> getBuyerByChannelId(int ChannelId);

    /**
     *  获取渠道对应需要开拓的商户
     * @param staffId
     */
    List<Map<String, Object>> getexploreCarBystaffId(int staffId);
}
