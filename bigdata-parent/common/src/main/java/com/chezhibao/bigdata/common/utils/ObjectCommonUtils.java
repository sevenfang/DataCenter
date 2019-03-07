package com.chezhibao.bigdata.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.TypeUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/5/11
 * Created by WangCongJun on 2018/5/11.
 */
@Slf4j
public class ObjectCommonUtils {

    /**
     * 将对象转换为Map
     * @return
     */
    public static Map<String,Object> object2Map(Object obj){
        //TODO 此处可能有性能问题
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            log.error("对象转换出错了",e);
        }
        return map;
    }

    /**
     * 将List<Map>转换为List<对象>
     * @return
     */
    public static <T> List<T> map2Object(List<Map<String,Object>> maps, Class<T> clazz){
        //TODO 此处可能有性能问题
        List<T> result = new ArrayList<>();
        try {
           for(Map<String,Object> map:maps){
               result.add(map2Object(map, clazz));
           }
            return result;
        }catch (Exception e){
            log.error("【公共模块】ObjectCommonUtils.object2Map对象转换失败！",e);
        }
        return null;
    }


    /**
     * 将Map转换为对象
     * @return
     */
    public static <T> T map2Object(Map<String,Object> map, Class<T> clazz){
        //TODO 此处可能有性能问题
        try {
            return TypeUtils.castToJavaBean(map,clazz);

        }catch (Exception e){
            log.error("【公共模块】ObjectCommonUtils.object2Map对象转换失败！",e);
        }
        return null;
    }

}
