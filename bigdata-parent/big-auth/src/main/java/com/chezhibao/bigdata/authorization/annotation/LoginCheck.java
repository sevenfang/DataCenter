package com.chezhibao.bigdata.authorization.annotation;


import java.lang.annotation.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/4.
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginCheck {
}
