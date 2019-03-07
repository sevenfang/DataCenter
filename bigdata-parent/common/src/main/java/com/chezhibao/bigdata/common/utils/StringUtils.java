package com.chezhibao.bigdata.common.utils;

/**
 * @author WangCongJun
 * @date 2018/5/11
 * Created by WangCongJun on 2018/5/11.
 */
public class StringUtils {

    public static String assembledRedisKey(Object...strings){
       StringBuilder result=new StringBuilder("");
        if(strings!=null&&strings.length>0){
            for(int i =0;i<strings.length;i++){
                if(i==0){
                    result.append(strings[i]);
                }else{
                    result.append(":").append(strings[i]);
                }
            }
        }
        return result.toString();
    }
}
