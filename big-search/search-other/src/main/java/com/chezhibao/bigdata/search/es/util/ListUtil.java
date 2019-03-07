package com.chezhibao.bigdata.search.es.util;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class ListUtil {
    /**
     * 合并多个list
     * @param lists
     * @param <Integer>
     * @return
     */
    public static <Integer> String  mergeLists(List<Integer>... lists) {
        String recommendCar = "";
        for (int i = 0, len = lists.length; i < len; i++) {
            if(!lists[i].isEmpty()){
                recommendCar += lists[i].toString().substring(1,lists[i].toString().length()-1)+",";
            }
        }
        return recommendCar.substring(0,recommendCar.length()-1);
    }

    public static List jsonToList(String json) {
        if (json.isEmpty()) {
            return null;
        }
        return JSON.parseObject(json, List.class);
    }


}
