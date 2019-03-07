package com.chezhibao.bigdata.gateway.pojo.utils;

import com.chezhibao.bigdata.gateway.pojo.ApiInfo;
import org.springframework.util.Assert;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/7.
 */
public class ApiKeyUtils {
    public static String getKey(ApiInfo apiInfo){
        String uri = apiInfo.getUri();
        Assert.notNull(uri, "http访问路径不能为空！！！");
        StringBuilder builder = new StringBuilder(uri.split("\\?")[0]);

        String method = apiInfo.getMethod();
        if (method != null) {
            builder.append("#").append(method.toUpperCase());
        }
        String version = apiInfo.getVersion();
        if (version != null) {
            builder.append("#").append(version);
        }
        return builder.toString();
    }
}
