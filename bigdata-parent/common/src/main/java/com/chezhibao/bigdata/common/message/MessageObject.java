package com.chezhibao.bigdata.common.message;

import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/4/26
 * Created by WangCongJun on 2018/4/26.
 */
public interface MessageObject extends Map<String,Object> {
    /**
     * 获取一个MessageObject
     * @param key
     * @return
     */
    MessageObject getObject(String key);

    /**
     * 获取一个MessageList
     * @param key
     * @return
     */
    MessageList getList(String key);


}
