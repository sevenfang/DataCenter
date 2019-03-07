package com.chezhibao.bigdata.config.client.pojo;

import lombok.Data;

/**
 * 在配置文件中
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/14.
 */
@Data
public class NacosFileConfig {
    /**
     * 配置的ID
     */
    private String dataId;
    /**
     * 是否自动刷新
     */
    private Boolean autoRefresh;
}
