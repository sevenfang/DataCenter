package com.chezhibao.bigdata.config.client.exception;

/**
 * nacos与本地的配置的环境、应用名不一致
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/14.
 */
public class EvnOrAppNameConfigException extends RuntimeException{
    public EvnOrAppNameConfigException(String message) {
        super(message);
    }
}
