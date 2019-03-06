package com.chezhibao.bigdata.gateway.core.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/20.
 */
@Data
public class ApiInfoBO {
   private String uri;
   private String method;
   private String version;

    public ApiInfoBO(String uri, String method, String version) {
        this.uri = uri;
        this.method = method;
        this.version = version;
    }
}
