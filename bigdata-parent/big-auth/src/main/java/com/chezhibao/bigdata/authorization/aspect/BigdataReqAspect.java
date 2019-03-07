package com.chezhibao.bigdata.authorization.aspect;

import com.chezhibao.bigdata.authorization.RequestHelper;
import com.chezhibao.bigdata.authorization.annotation.AuthReqMapping;
import com.chezhibao.bigdata.authorization.annotation.LoginCheck;
import com.chezhibao.bigdata.authorization.bo.UserInfo;
import com.chezhibao.bigdata.authorization.service.AuthService;
import com.chezhibao.bigdata.authorization.service.UserService;
import com.chezhibao.bigdata.common.utils.CookieUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.chezhibao.bigdata.authorization.LoggerUtils.* ;


/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/4.
 */
//@Aspect
//@Component
public class BigdataReqAspect {

    @Autowired
    private AuthService authService;

    /**
     * 用户登陆校验校验
     * @param loginCheck
     */
    @Before("@annotation(loginCheck)")
    public void loginCheck(JoinPoint point, LoginCheck loginCheck) throws Exception{
        authService.loginHandler("");
    }
    /**
     * 用户登陆校验校验
     * @param requestMapping
     */
    @Before("@annotation(requestMapping)")
    public void authReqMapping(JoinPoint point, RequestMapping requestMapping) throws Exception{
        authService.loginHandler(requestMapping.value()[0]);
    }
}
