package com.chezhibao.bigdata.dbms.server.auth;

import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.config.client.ParamsUtil;
import com.chezhibao.bigdata.config.client.pojo.SysConfigParamProperties;
import com.chezhibao.bigdata.dbms.server.RequestHelper;
import com.chezhibao.bigdata.dbms.server.auth.annotation.Admin;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/20.
 */
@Aspect
@Component
@Slf4j
public class AdminAspect {

    private static final String ADMIN_WHITE_LIST="auth.admin.whitelist";
    /**
     * 管理员校验
     * @param admin
     * @throws Throwable
     */
    @Before("@annotation(admin)")
    public void adminCheck(JoinPoint point, Admin admin){
        Integer userId = RequestHelper.getAndVerifyUserId();
        log.info("【数据查询平台】管理员权限校验;usrId：{}", userId);
        List<Integer> integers = getAdminWhitelist();
        if(!integers.contains(userId)){
            throw new RuntimeException("非法操作！不是管理员！");
        }
        log.info("@Before：参数为：{}" , Arrays.toString(point.getArgs()));
    }

    public static List<Integer>   getAdminWhitelist(){
        return ParamsUtil.getDynamicValueList(ADMIN_WHITE_LIST, Integer.class);
    }
}
