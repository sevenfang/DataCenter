package com.chezhibao.bigdata.authorization.service.impl;

import static com.chezhibao.bigdata.authorization.LoggerUtils.*;

import com.chezhibao.bigdata.authorization.RequestHelper;
import com.chezhibao.bigdata.authorization.bo.UserInfo;
import com.chezhibao.bigdata.authorization.service.AuthService;
import com.chezhibao.bigdata.authorization.service.UserService;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.common.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/4.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private String loginuUrl = "https://passport.mychebao.com/index.htm";
    private String boss = "http://boss.mychebao.com";
    private String domain = "mychebao.com";
    private String unAuth = "free";

    @Autowired
    private UserService userService;

    @Override
    public Boolean isLogin(Integer id) {
        UserInfo cacheUserById = userService.getCacheUserById(id);
        return !(cacheUserById == null);
    }

    @Override
    public void sendToLoginPage() throws Exception {
        HttpServletResponse response = RequestHelper.getResponse();
        HttpServletRequest request = RequestHelper.getRequest();
        String strReferer = request.getHeader("Referer");
        if (StringUtils.isEmpty(strReferer) || !strReferer.contains(domain)) {
            response.sendRedirect(boss);
        } else {
            response.sendRedirect(loginuUrl + "?" + URLEncoder.encode(strReferer, "UTF-8"));
        }
    }

    @Override
    public void loginHandler(String path) throws Exception {
        if (LOG_AUTH_SYS.isDebugEnabled()) {
            LOG_AUTH_SYS.debug("【权鉴系统】访问的mapping路径是{}", path);
        }
        String loginSso = CookieUtils.getCookieValue(RequestHelper.getRequest(), "LOGIN_SSO");
        String loginUserId = CookieUtils.getCookieValue(RequestHelper.getRequest(), "LOGIN_USER_ID");
        if(StringUtils.isEmpty(loginSso) || StringUtils.isEmpty(loginUserId)){
            sendToLoginPage();
            return;
        }

        String env = System.getProperty("env");

        if (StringUtils.isEmpty(path)) {
            path = "/";
        } else {
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
        }

        if (path.startsWith("auth")) {
            //必须校验
            Integer userId = RequestHelper.getAndVerifyUserId();
            Boolean login = isLogin(userId);
            if (!login) {
                sendToLoginPage();
            }
            return;
        }

        //非生产环境 或 指明不需要权鉴的 直接放过
        if (!"prod".equals(env) || path.startsWith(unAuth)) {
            return;
        }

        Integer userId = RequestHelper.getAndVerifyUserId();
        Boolean login = isLogin(userId);
        if (!login) {
            sendToLoginPage();
        }

    }

    @Override
    public BigdataResult loginCheck(String path) throws Exception {
        HttpServletRequest request = RequestHelper.getRequest();
        StringBuffer requestURL = request.getRequestURL();
        if(requestURL!=null){
            String s = requestURL.toString();
            if(s.matches("^http\\S?://(127.0.0.1|localhost)(:\\d*)?/.*")){
                return null;
            }
        }
        if (LOG_AUTH_SYS.isDebugEnabled()) {
            LOG_AUTH_SYS.debug("【权鉴系统】访问的mapping路径是{}", path);
        }
        String loginSso = CookieUtils.getCookieValue(request, "LOGIN_SSO");
        String loginUserId = CookieUtils.getCookieValue(request, "LOGIN_USER_ID");
        if(StringUtils.isEmpty(loginSso) || StringUtils.isEmpty(loginUserId)){
            String page = sendLoginPage();
            return BigdataResult.build(4004,page);
        }

        String env = System.getProperty("env");

        if (StringUtils.isEmpty(path)) {
            path = "/";
        } else {
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
        }

        if (path.startsWith("auth")) {
            //必须校验
            Integer userId = RequestHelper.getAndVerifyUserId();
            Boolean login = isLogin(userId);
            if (!login) {
                String page = sendLoginPage();
                return BigdataResult.build(4004,page);
            }
            return null;
        }

        //非生产环境 或 指明不需要权鉴的 直接放过
        if (!"prod".equals(env) || path.startsWith(unAuth)) {
            return null;
        }

        Integer userId = RequestHelper.getAndVerifyUserId();
        Boolean login = isLogin(userId);
        if (!login) {
            String page = sendLoginPage();
            return BigdataResult.build(4004,page);
        }
        return null;
    }

    private String sendLoginPage() throws Exception {
        HttpServletRequest request = RequestHelper.getRequest();
        String strReferer = request.getHeader("Referer");
        if (StringUtils.isEmpty(strReferer) || !strReferer.contains(domain)) {
            return  boss;
        } else {
            return loginuUrl + "?from=" + URLEncoder.encode(strReferer, "UTF-8");
        }
    }


}
