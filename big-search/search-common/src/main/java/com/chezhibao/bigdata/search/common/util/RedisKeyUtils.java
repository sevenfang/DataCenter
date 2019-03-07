package com.chezhibao.bigdata.search.common.util;

import com.chezhibao.bigdata.search.common.Constants;
import com.chezhibao.bigdata.search.es.bo.SearchPage;
import org.springframework.util.StringUtils;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/15.
 */
public class RedisKeyUtils {

    public static String getReCommendCarInfoQueryKey(Integer buyerId, SearchPage searchPage){

        String sessionName = searchPage.getSessionName();

        return getReCommendCarInfoQueryKey(buyerId,sessionName);
    }

    public static String getChannelCommendCarInfoQueryKey(Integer buyerId, SearchPage searchPage){

        String sessionName = searchPage.getSessionName();

        return getChannelCommendCarInfoQueryKey(buyerId,sessionName);
    }

    public static String getReCommendCarInfoQueryKey(Integer buyerId, String sessionName){
        StringBuilder key = new StringBuilder() ;
        key.append(Constants.AUCS_ALL_RECOMMEND_CACHE_PREFIX);
        if(!StringUtils.isEmpty(sessionName)){
            key.append(sessionName).append(":");
        }
        return key.append(buyerId).toString();
    }

    public static String getChannelCommendCarInfoQueryKey(Integer buyerId, String sessionName){
        StringBuilder key = new StringBuilder() ;
        key.append(Constants.CHANNEL_BUYER_RECOMMEND_CACHE_PREFIX);
        if(!StringUtils.isEmpty(sessionName)){
            key.append(sessionName).append(":");
        }
        return key.append(buyerId).toString();
    }
}
