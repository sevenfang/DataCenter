package com.chezhibao.bigdata.config.client.service;

import com.chezhibao.bigdata.config.client.pojo.SysConfigParamProperties;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/14.
 */
public interface BigdataConfigService {

     void initLogback();

    SysConfigParamProperties getAllParams();
}
