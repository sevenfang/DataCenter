package com.chezhibao.bigdata.common.message.impl;

import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.common.message.MessageList;
import com.chezhibao.bigdata.common.message.MessageObject;

import java.util.List;
import java.util.Vector;

/**
 * @author WangCongJun
 * @date 2018/4/26
 * Created by WangCongJun on 2018/4/26.
 */
public class MessageListImpl<V> extends Vector<V> implements MessageList<V> {

    @Override
    public MessageObject getObject(int index) {
        Object o = get(index);
        if (o instanceof MessageObject) {
            return (MessageObject) o;
        }
        throw new ClassCastException();
    }

    @Override
    public MessageList getList(int index) {
        Object o = get(index);
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
