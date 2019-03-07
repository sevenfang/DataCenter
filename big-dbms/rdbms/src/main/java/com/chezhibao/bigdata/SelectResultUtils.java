package com.chezhibao.bigdata;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/5/31.
 */
public class SelectResultUtils {
    /**
     * 查询结果中的map长度必须为1
     * @param result
     * @return
     */
    public static  List flatMap(List<Map<String,Object>> result){
        List list = new ArrayList<>();
        for(Map<String,Object> map:result){
            list.addAll(map.values());
        }
        return list;
    }
}
