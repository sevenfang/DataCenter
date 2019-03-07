package com.chezhibao.bigdata.search.common;

/**
 * @author WangCongJun
 * @date 2018/5/16
 * Created by WangCongJun on 2018/5/16.
 */
public class Constants {

    /**
     * 存在redis中的最新Type (版本key)
     */
    public static final String LATEST_RECOMMEND_VERSION ="latest_recommend_type";

    /**
     * 存在redis中的带竞拍开始时间最新Type (版本key)
     */
    public static final String LATEST_BIDTIME_RECOMMEND_VERSION ="latest_bidtime_recommend_type";

    /**
     * 推荐版本的前缀/用户缓存redis中的key前缀（前缀+buyerId+page+size），用来拼装每个商户各自独有的版本号
     */
    public static final String RECOMMENDREDISKEYPREFIX="es:recommend_version";

    /**
     * 推荐时数据缓存在redis中的key前缀
     */
    public static final String ESRECOMMENDKEY="es:aucs:recommend:";

    /**
     * 新逻辑：根据竞拍开始时间进行排序，然后再根据规则得到rank
     * 推荐版本的前缀/用户缓存redis中的key前缀（前缀+buyerId+page+size），用来拼装每个商户各自独有的版本号
     */
    public static final String RECOMMENDBIDTIMEREDISKEYPREFIX="es:recommend_bidtime_version";

    /**
     * redis中存放的用户信息
     */
    public static final String BUYERID_INFO ="buyerid_info";


    /**
     * es中存放的index
     */
    public static final String INDEX ="_index";

    /**
     * es中存放的type
     */
    public static final String TYPE ="_type";

    /**
     * es中存放的好车推荐flag为1
     */
    public static final Integer GOOD_CAR_RECOMMENDATION_TYPE =1;

    /**
     * redis中存放的好车推荐的商户信息
     */
    public static final String GOOD_CAR_RECOMMENDATION_BUYER_INFO ="es:recommend_better_car";

    /**
     * 全部推荐sjb排序信息前缀
     */
    public static final String AUCS_ALL_RECOMMEND_SJB_PREFIX ="aucs:all:recommend:sjb:";

    /**
     * 这对用户的缓存数据(真实返回给用户的数据)
     */
    public static final String AUCS_ALL_RECOMMEND_CACHE_PREFIX ="aucs:all:recommend:cache:";

    /**
     * 默认排序的所有车辆  这里与闻磊那边的车辆数据一致
     */
    public static final String AUCS_ALL_RECOMMEND_DEFAULT ="aucs:all:recommend:default:";

    /**
     * 全部推荐 新增车源队列key
     */
    public static final String AUCS_ALL_RECOMMEND_ADD ="aucs:all:recommend:add";

    /**
     * 全部推荐 删除车源队列key
     */
    public static final String AUCS_ALL_RECOMMEND_DEL ="aucs:all:recommend:del";

    /**
     * 这对渠道商户的缓存数据(真实返回给用户的数据)
     */
    public static final String CHANNEL_BUYER_RECOMMEND_CACHE_PREFIX ="channel:buyer:recommend:cache:";

}
