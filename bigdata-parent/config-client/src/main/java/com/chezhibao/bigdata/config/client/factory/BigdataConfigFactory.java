package com.chezhibao.bigdata.config.client.factory;

import com.chezhibao.bigdata.config.client.pojo.SysConfigParamProperties;
import com.chezhibao.bigdata.config.client.service.BigdataConfigService;
import com.chezhibao.bigdata.config.client.service.BigdataNacosConfigService;
import com.chezhibao.bigdata.config.client.service.impl.BigdataConfigServiceImpl;
import com.chezhibao.bigdata.config.client.service.impl.BigdataNacosConfigServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/20.
 */
@Slf4j
public class BigdataConfigFactory {
    private static BigdataConfigService bigdataConfigService;

    static {
        try {
            BigdataNacosConfigService bigdataNacosConfigService = new BigdataNacosConfigServiceImpl();
            bigdataConfigService = new BigdataConfigServiceImpl(bigdataNacosConfigService);
        }catch (Exception e){
            log.error("【配置服务】初始化配置出错！！",e);
        }
    }
    public static BigdataConfigService getBigdataConfigService() {
        return bigdataConfigService;
    }
}
