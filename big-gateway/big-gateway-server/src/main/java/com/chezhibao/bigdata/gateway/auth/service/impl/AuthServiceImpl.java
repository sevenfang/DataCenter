package com.chezhibao.bigdata.gateway.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.gateway.adapter.dubbo.DubboProxyService;
import com.chezhibao.bigdata.gateway.auth.exception.AuthException;
import com.chezhibao.bigdata.gateway.auth.exception.LoginException;
import com.chezhibao.bigdata.gateway.auth.service.AuthCheck;
import com.chezhibao.bigdata.gateway.auth.service.AuthService;
import com.chezhibao.bigdata.gateway.auth.service.LoginCheck;
import com.chezhibao.bigdata.gateway.core.Api;
import com.chezhibao.bigdata.gateway.core.ApiExecutor;
import com.chezhibao.bigdata.gateway.core.factory.ApiFactory;
import com.chezhibao.bigdata.gateway.pojo.ApiInfo;
import com.chezhibao.bigdata.gateway.core.pojo.ApiInfoBO;
import com.chezhibao.bigdata.gateway.core.service.ApiServiceDelegate;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/5.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private static final String LOGIN_USER_ID = "LOGIN_USER_ID";
    private static final String LOGIN_SSO = "LOGIN_SSO";
    private static final String DOMAIN = "chezhibao.com";

    @Autowired
    private LoginCheck loginCheck;

    @Autowired
    private AuthCheck authCheck;

    @Override
    public void authentic(FullHttpRequest request) throws LoginException,AuthException {
        String value = request.headers().get(HttpHeaderNames.COOKIE);
        Set<Cookie> cookies = ServerCookieDecoder.STRICT.decode(value);
        String userId = null;
        for(Cookie c : cookies){
            String domain = c.domain();
            if(StringUtils.isEmpty(domain)) {
                continue;
            }
            String name = c.name();
            if(StringUtils.isEmpty(name)) {
                continue;
            }
            if(domain.equals(DOMAIN) && name.equals(LOGIN_USER_ID)){
                userId = c.value();
            }
        }
        if(StringUtils.isEmpty(userId)) {
            throw new LoginException("没有登陆");
        }
        //判断是否登陆
        Boolean login = loginCheck.isLogin(userId);
        if(!login){
            throw new LoginException("没有登陆");
        }
        String uri = request.uri().split("\\?")[0];
        String name = request.method().name();
        if(!authCheck.auth(userId,uri,name)){
            throw new AuthException("没有权限");
        }
    }


}
