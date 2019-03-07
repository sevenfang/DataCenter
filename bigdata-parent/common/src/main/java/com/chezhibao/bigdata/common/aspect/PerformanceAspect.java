package com.chezhibao.bigdata.common.aspect;

import com.chezhibao.bigdata.common.aspect.annotation.InvokeTime;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/20.
 */
@Aspect
@Component
public class PerformanceAspect {
    private static  Logger logger = LoggerFactory.getLogger("com.chezhibao.invokeTime");
    @Around("@annotation(invokeTime)")
    public Object beforeSwitchDS(ProceedingJoinPoint pjp, InvokeTime invokeTime) throws Throwable{
        long begin = System.currentTimeMillis();
        String method = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().getName();
        Object ret = pjp.proceed();
        long l = System.currentTimeMillis() - begin;
        if(logger.isDebugEnabled()){
            logger.debug("【性能监控】 method<{}.{}> cost time <{}>ms",className,method, l);
        }else{
            if(l>100){
                logger.info("【性能监控】 method<{}.{}> cost time <{}>ms",className,method, l);
            }
        }
        return ret;
    }
}
