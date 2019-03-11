package com.chezhibao.bigdata.vo;

import lombok.Data;

import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/11.
 */
@Data
public class MockVO {
    private String gatewayServer;
    private String uri;
    private String method;
    private String version;
    private Map<String,Object> params;

    public String getURL(){
        return gatewayServer+uri+"?version="+version;
    }
}
