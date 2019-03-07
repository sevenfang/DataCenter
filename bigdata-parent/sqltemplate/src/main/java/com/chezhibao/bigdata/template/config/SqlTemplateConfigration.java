package com.chezhibao.bigdata.template.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author WangCongJun
 * @date 2018/4/25
 * Created by WangCongJun on 2018/4/25.
 */
@ConfigurationProperties(prefix = "sql.template")
@Data
public class SqlTemplateConfigration {
    private String path;

}
