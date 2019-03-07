package com.chezhibao.bigdata.dbms.hbase;

import com.chezhibao.bigdata.dbms.hbase.bo.HbaseParam;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/25.
 */
public interface HbaseService {
    /**
     *
     * 根据表名，rowkey删除指定数据
     * 列族不指定则为默认值 info
     * 列不指定则为默认值 data
     * @param hbaseParam
     * @return
     */
    Boolean deleteByrowKey(HbaseParam hbaseParam);



    /**
     * 将T对象存入Hbase  直接将对象转化成JSON字符串存放在 cf（列族）： 自定义  qu（属性）：data
     * @param param
     * @param <T>
     * @return
     */
    <T>Boolean save(HbaseParam<T> param);


    /**
     *  根据指定的字段或数据
     *  如果cf为空则给定默认值 info
     *  如果qualifier为空则给定默认值 data
     * @param param
     * @param <T>
     * @return
     */
    <T> T get(HbaseParam<T> param);

    /**
     * 只获取其中的字符串不做转换
     * @param param
     * @return
     */
    String getString(HbaseParam<String> param);
}
