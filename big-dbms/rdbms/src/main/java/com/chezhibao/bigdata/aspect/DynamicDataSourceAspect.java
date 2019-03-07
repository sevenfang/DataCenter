package com.chezhibao.bigdata.aspect;

import com.alibaba.dubbo.rpc.RpcContext;
import com.chezhibao.bigdata.annotation.DS;
import com.chezhibao.bigdata.datasource.DataSourceContextHolder;
import com.chezhibao.bigdata.dbms.common.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/4/25
 * Created by WangCongJun on 2018/4/25.
 */
@Aspect
@Component
public class DynamicDataSourceAspect {
    private static Logger log = LoggerUtil.RDBMS_LOG;
    @Before("@annotation(DS)")
    public void beforeSwitchDS(DS DS){
        String dataSource = DataSourceContextHolder.DEFAULT_DS;
        String value = DS.value();
        RpcContext context = RpcContext.getContext();
        String clientIP = context.getRemoteHost();
        int remotePort = context.getRemotePort();
        log.debug("【数据库管理系统】当前注解中的数据库名;{},调用方Ip:{},port:{}",value,clientIP,remotePort);
        if(!StringUtils.isEmpty(value)){
            dataSource=value;
        }
        // 切换数据源
        DataSourceContextHolder.setDB(dataSource);
    }


    @After("@annotation(DS)")
    public void afterSwitchDS(DS DS){
        DataSourceContextHolder.setDB(DataSourceContextHolder.DEFAULT_DS);
    }
}
