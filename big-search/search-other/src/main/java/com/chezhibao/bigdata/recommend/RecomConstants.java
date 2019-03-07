package com.chezhibao.bigdata.recommend;

/**
 * @author WangCongJun
 * @date 2018/5/16
 * Created by WangCongJun on 2018/5/16.
 */
public class RecomConstants {


    /**
     * redis中存放的好车推荐的商户信息
     */
    public static final String GOOD_CAR_RECOMMENDATION_INFO ="bettercar";

    /**
     * 竞拍出价结束，存储车商推荐个性化列表
     */
    public static final String GOOD_CAR_BID_FINISH_RECOMMENDATION_INFO ="bidfinish:bettercar";

    /**
     * 竞拍出价结束，存储车商推荐同品牌列表
     */
    public static final String GOOD_CAR_BID_FINISH_SAMEBRAND_INFO ="bidfinish:samebrand";

    /**
     * 车辆所在城市
     */
    public static final String GOOD_BUYER_CITY_INFO ="recommend:buyerCity";

    /**
     * 车辆所在省份城市
     */
    public static final String GOOD_BUYER_PROVINCE_CITY_INFO ="recommend:buyerProvinceCity";

    /**
     * 竞拍结束，同品牌推荐
     */
    public static final String GOOD_RECOMMEND_SAME_BRAND_CAR_INFO ="recommend:samebrand:car";

    /**
     * redis中存放的商户信息地区信息
     */
    public static final String BUYER_REGION_INFO ="buyerregion";

    /**
     * redis中存放的商户看了未出价车辆
     */
    public static final String BUYER_NO_BID_INFO ="detail_car";
    /**
     * redis中存放的商户是否是新老用户
     */
    public static final String BUYER_ABILITY_FLAG ="buyer:ability:flag";
    /**
     * redis中存放的达到车均或者出价大于系统评估价的车辆
     */
    public static final String DEVELOPED_AUCTIONCAR   ="developed_auctioncar";

    /**
     * redis中存放当前正在竞拍车辆
     */
    public static final String BUYER_IS_SETTING_CARA ="recommend:isbidding:car";
    /**
     * redis中存放当前车主降价车辆
     */
    public static final String BUYER_IS_REDUCTION_CARA ="recommend:isreduction:car";

    /**
     * redis中存放当前正在竞拍车辆id以及检测单id
     */
    public static final String BUYER_AUCTIONCARID_DETECTID ="recommend:auctioncarid:detectid";

}
