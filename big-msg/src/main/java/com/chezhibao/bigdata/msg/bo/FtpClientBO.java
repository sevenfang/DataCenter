package com.chezhibao.bigdata.msg.bo;

import lombok.Data;

import java.util.Map;

/**
 * Created by jerry on 2018/10/11.
 * 供FTP连接使用
 */
@Data
public class FtpClientBO {
    /**
     * ip地址
     */
    private String ipAddr;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String pwd;

    public FtpClientBO(Map<String, String> clientProperty) {
        this.ipAddr = clientProperty.get("ipAddr");
        this.port = Integer.parseInt(clientProperty.get("port"));
        this.userName = clientProperty.get("userName");
        this.pwd = clientProperty.get("pwd");
    }
}
