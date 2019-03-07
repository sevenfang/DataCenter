package com.chezhibao.bigdata.system.listener;

import com.chezhibao.bigdata.system.service.ZkParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
public class InitData implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ZkParamsService zkParamsService;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //只在root application context初始化完成后调用逻辑代码，其他的容器的初始化完成，则不做任何处理，修改后代码
        if (event.getApplicationContext().getParent() == null) {
            Thread thread = new Thread(()->zkParamsService.initListener());
            thread.start();
        }

    }
}
