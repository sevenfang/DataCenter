package com.chezhibao.bigdata.dbms.server.handler;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/7.
 */
public interface Desensitization<T> {
    /**
     * 对数据进行脱敏处理
     * @param t
     * @return
     */
      T desensitization(T t);
}
