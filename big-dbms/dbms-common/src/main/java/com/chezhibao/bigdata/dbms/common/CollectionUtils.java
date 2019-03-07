package com.chezhibao.bigdata.dbms.common;

import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/18.
 */
public class CollectionUtils {
    public static String[]  list2Array(Collection<String> collection){
        String[] ts = new String [collection.size()];
        return collection.toArray(ts);
    }
    public static <T>List<T>  Array2List(T[] ts){
        ArrayList<T> ts1 = new ArrayList<>();
        return Arrays.asList(ts);
    }
}
