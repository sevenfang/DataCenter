package com.chezhibao.bigdata.dbms.server.auth;

import com.chezhibao.bigdata.dbms.server.RequestHelper;
import com.chezhibao.bigdata.dbms.server.auth.annotation.AuthManage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/20.
 */
@Aspect
@Component
@Slf4j
public class AuthManageAspect {

    /**
     * 业务人员校验校验
     * @param authManage
     * @throws Throwable
     */
    @Before("@annotation(authManage)")
    public void adminCheck(JoinPoint point, AuthManage authManage) throws Throwable{
        Integer userId = RequestHelper.getAndVerifyUserId();
        log.info("【数据查询平台】权限校验;usrId：{}", userId);
        log.info("@Before：参数为   ：{}" , Arrays.toString(point.getArgs()));
    }
}
