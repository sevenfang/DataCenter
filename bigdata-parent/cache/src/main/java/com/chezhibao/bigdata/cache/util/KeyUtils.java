package com.chezhibao.bigdata.cache.util;


/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/11.
 */
public class KeyUtils {
    public static String createKey(String setName,String column){
        return setName.concat(":").concat(column);
    }
    public static String[] createKey(String setName,String... columns){
        int len = columns.length;
        if(len ==0){
            return new String[0];
        }
        String[]keys = new String[len];
        for(int i = 0;i<len;i++){
            keys[i] = createKey(setName,columns[i]);
        }
        return keys;
    }
}
