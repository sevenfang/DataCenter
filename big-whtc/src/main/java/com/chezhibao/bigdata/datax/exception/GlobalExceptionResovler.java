package com.chezhibao.bigdata.datax.exception;

import com.chezhibao.bigdata.common.exception.BigException;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author WangCongJun
 * @date 2018/5/9
 * Created by WangCongJun on 2018/5/9.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionResovler {

    @ExceptionHandler(value=Exception.class)
    public BigdataResult resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.debug("进入异常Exception处理器......");
        log.debug("handler : "+o.getClass());
        log.debug("系统出现异常",e);
        return BigdataResult.build(200000,e.getMessage());
    }

    @ExceptionHandler(value=BigException.class)
    public BigdataResult resolveBigException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.debug("进入异常UnauthenticatedException处理器......");
        log.debug("handler : "+o.getClass());
        log.debug("系统出现异常",e);
        BigException dinkTalkException = (BigException)e;
        return BigdataResult.build(dinkTalkException.getCode(),e.getMessage());
    }
}