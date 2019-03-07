package com.chezhibao.bigdata.common.message.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chezhibao.bigdata.common.message.MessageList;
import com.chezhibao.bigdata.common.message.MessageObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author WangCongJun
 * @date 2018/4/26
 * Created by WangCongJun on 2018/4/26.
 */
@Slf4j
public class MessageObjectImpl extends JSONObject implements MessageObject {


    @Override
    public MessageObject getObject(String key) {
        Object o = get(key);
        if (o instanceof MessageObject) {
            return (MessageObject) o;
        }

        throw new ClassCastException();
    }

    @Override
    public MessageList getList(String key) {
        Object o = get(key);
        if (o instanceof MessageList) {
            return (MessageList) o;
        }
        throw new ClassCastException();
    }

    @Override
    public String toString() {

        return JSON.toJSONString(this);
    }

}
