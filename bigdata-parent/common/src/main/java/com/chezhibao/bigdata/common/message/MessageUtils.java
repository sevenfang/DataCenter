package com.chezhibao.bigdata.common.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/4/26
 * Created by WangCongJun on 2018/4/26.
 */
@Slf4j
public class MessageUtils {
    /**
     * 将字符串转换成MessageList
     * @param str
     * @return
     */
    public static MessageList string2ML(Object str) {
        return string2ML(str.toString(),",");
    }

    /**
     * 将字符串转换成MessageList
     * @param str
     * @param delimiter 分隔符
     * @return
     */
    public static MessageList string2ML(Object str, String delimiter) {
        MessageList messageList = MessageFactory.getMessageList();
        for(String s:str.toString().split(delimiter)){
            messageList.add(s);

        }
        return messageList;
    }


    /**
     * 转换json字符串为ML对象
     * @param json
     * @return
     */
    public static MessageList json2ML(String json){
        log.debug("【MessageUtils】当前的json串：{}",json);
        List<Map<String, Object>> messageObjects = JSON.parseObject(json.replaceAll("\\\\\"", ""), new TypeReference<List<Map<String, Object>>>() {
        });
        MessageList ml = MessageFactory.getMessageList();
        ml.addAll(messageObjects);
        return ml;
    }



}
