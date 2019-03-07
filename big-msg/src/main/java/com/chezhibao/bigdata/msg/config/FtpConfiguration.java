package com.chezhibao.bigdata.msg.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/5/9
 * Created by WangCongJun on 2018/5/9.
 */
@Component
@ConfigurationProperties(prefix = "ftp")
@Data
public class FtpConfiguration {

    private Map<String,String> client = new HashMap<>();
}
