package com.chezhibao.bigdata.gateway.auth.service;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.cookie.Cookie;

import java.util.Set;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/5.
 */
public interface AuthService {

    /**
     * 判断是否有权限
     * @param request
     * @return
     */
    void authentic(FullHttpRequest request) throws Exception;
}
