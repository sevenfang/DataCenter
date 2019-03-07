package com.chezhibao.bigdata.common.message;

import java.util.List;

/**
 * @author WangCongJun
 * @date 2018/4/26
 * Created by WangCongJun on 2018/4/26.
 */
public interface MessageList<V> extends List<V>{
    /**
     * 获取一个MessageObject
     * @param index
     * @return
     */
   MessageObject getObject(int index);

    /**
     * 获取一个MessageList
     * @param index
     * @return
     */
   MessageList getList(int index);

}
