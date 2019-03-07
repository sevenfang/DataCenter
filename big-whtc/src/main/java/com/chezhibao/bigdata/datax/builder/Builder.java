package com.chezhibao.bigdata.datax.builder;

import com.chezhibao.bigdata.common.message.MessageObject;

import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/4/26
 * Created by WangCongJun on 2018/4/26.
 */
public interface Builder {
    /**
     * 创建任务
     * @return
     */
    MessageObject build(Map reader,Map writer);



    /**
     * 将配置中的datasource转换成真正的链接地址
     * @param configData
     */
    Map translateWriterDatasource(Map configData);

    /**
     * 将配置中的datasource转换成真正的链接地址
     * @param configData
     */
    Map translateReaderDatasource(Map configData);

}
