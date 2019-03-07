package com.chezhibao.bigdata.search.common.util;

import com.chezhibao.bigdata.search.es.bo.RecommendInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/16.
 */
public class AuctionCarUtils {
    public static List<Integer> transStringToInteger(Collection<String> strings){
        List<Integer> result = new ArrayList<>();
        for(String s:strings){
            int i = Integer.parseInt(s);
            result.add(i);
        }
        return result;
    }

    public static List<String> transToString(List<?> strings){
        List<String> result = new ArrayList<>();
        for(Object s:strings){
            result.add(s.toString());
        }
        return result;
    }

    public static List<RecommendInfo> tranIntetgerToRecommendInfos(List<Integer> auctionCarIds){
        List<RecommendInfo> recommendInfos = new ArrayList<>();
        RecommendInfo recommendInfo;
        int size = auctionCarIds.size();
        for(int i = 0; i<size; i++){
            recommendInfo = new RecommendInfo();
            recommendInfo.setScore(Double.parseDouble((size-i)+""));
            recommendInfo.setAuctionCarid(auctionCarIds.get(i));
            recommendInfos.add(recommendInfo);
        }
        return recommendInfos;
    }
}
