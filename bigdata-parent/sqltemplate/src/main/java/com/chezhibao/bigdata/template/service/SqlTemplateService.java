package com.chezhibao.bigdata.template.service;

import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/4/26
 * Created by WangCongJun on 2018/4/26.
 */
public interface SqlTemplateService {
    /**
     * 从xml中获取sql模板并解析
     * 获取对应的sql语句
     * @param sqlId
     * @param params
     * @return
     */
    String getSql(String sqlId,Map<String,Object> params);

    /**
     * 用户传入sql模板并解析
     * 获取对应的sql语句
     * @param sqlStringTemplate
     * @param params
     * @return
     */
    String getSqlFromStringTemplate(String sqlStringTemplate,Map<String,Object> params);
}
