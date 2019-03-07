package com.chezhibao.bigdata.authorization.aspect;

import com.chezhibao.bigdata.authorization.annotation.LoginCheck;
import com.chezhibao.bigdata.authorization.service.AuthService;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.chezhibao.bigdata.authorization.LoggerUtils.*;


/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/4.
 */
@Aspect
@Component
public class LoginCheckAspect {

    @Autowired
    private AuthService authService;

    /**
     * 用户登陆校验校验
     * @param loginCheck
     */
    @Around("@annotation(loginCheck)")
    public Object loginCheck(ProceedingJoinPoint invocation, LoginCheck loginCheck) throws Exception{
        BigdataResult result = authService.loginCheck("");
        if (result!=null){
            return result;
        }
        Object proceed = null;
        try {
            proceed = invocation.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            LOG_AUTH_SYS.error("【权限校验】出错了！",throwable);
        }
        return proceed;
    }
    /**
     * 用户登陆校验校验
     * @param requestMapping
     */
    @Around("@annotation(requestMapping)")
    public Object authReqMapping(ProceedingJoinPoint invocation, RequestMapping requestMapping) throws Exception{
        BigdataResult result = authService.loginCheck(requestMapping.value()[0]);
        if (result!=null){
            return result;
        }
        Object proceed = null;
        try {
            proceed = invocation.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            LOG_AUTH_SYS.error("【权限校验】出错了！",throwable);
        }
        return proceed;
    }
}
