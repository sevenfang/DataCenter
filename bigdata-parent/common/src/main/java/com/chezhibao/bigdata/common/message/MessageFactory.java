package com.chezhibao.bigdata.common.message;

import com.chezhibao.bigdata.common.message.impl.MessageListImpl;
import com.chezhibao.bigdata.common.message.impl.MessageObjectImpl;

/**
 * @author WangCongJun
 * @date 2018/4/26
 * Created by WangCongJun on 2018/4/26.
 */
public class MessageFactory {
    /**
     * 创建一个MessageObject
     * @return
     */
    public static MessageObject getMessageObject(){
        return new MessageObjectImpl();
    }

    /**
     * 创建一个MessageList
     * @return
     */
    public static MessageList getMessageList(){
        return new MessageListImpl();
    }

}
