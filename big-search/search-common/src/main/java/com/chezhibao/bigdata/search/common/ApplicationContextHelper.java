package com.chezhibao.bigdata.search.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/21.
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware {

    public static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context=applicationContext;
    }
    public static <T> T get(Class<T> clazz) {
        return context.getBean(clazz);
    }
    public static Object get(String className){
        try {
            Class<?> aClass = Class.forName(className);
            return context.getBean(aClass);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
