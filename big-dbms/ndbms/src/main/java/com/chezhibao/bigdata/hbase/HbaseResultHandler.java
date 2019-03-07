package com.chezhibao.bigdata.hbase;

import org.apache.hadoop.hbase.client.Result;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/25.
 */
public interface HbaseResultHandler<T> {
    /**
     * 处理返回的数据
     * @param results
     * @param tClass
     * @return
     */
     List<T> handler(Class<T> tClass, Result... results);
}
