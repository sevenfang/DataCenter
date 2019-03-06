package com.chezhibao.bigdata.router;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.realreport.router.Router;
import com.chezhibao.bigdata.realreport.router.RouterService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/14.
 */
@RestController
@RequestMapping("/cbr/router")
public class RouterController {

    @Reference(check = false)
    private RouterService routerService;

    /**
     * 获取所有路由配置信息
     * @return
     */
    @RequestMapping("info/all")
    public BigdataResult getAllRouterInfo(){
        Map<String, Router> allRouter = routerService.getAllRouter();
        return BigdataResult.ok(allRouter);
    }

    /**
     * 获取指定的一个代理信息
     * @param location
     * @return
     */
    @RequestMapping("info")
    public BigdataResult getRouter(String location){
        Router routerByLocation = routerService.getRouterByLocation(location);
        if(ObjectUtils.isEmpty(routerByLocation)){
            return BigdataResult.build(4005,"次路由信息不存在！！");
        }
        return BigdataResult.ok(routerByLocation);
    }
    @RequestMapping("info/del")
    public BigdataResult delRouter(String location){
        Boolean aBoolean = routerService.delRouter(location);
        if(aBoolean){
          return BigdataResult.ok();
        }
        return BigdataResult.build(4005,"次路由信息不存在!!");
    }

    /**
     * 更新（路由）代理信息
     * @param router
     * @return
     */
    @RequestMapping("info/update")
    public BigdataResult updateRouter(@RequestBody Router router){
        Boolean aBoolean = routerService.updateRouter(router);
        if(aBoolean){
            return BigdataResult.ok();
        }
        return BigdataResult.build(4006,"次路由信息不存在!!");
    }

    /**
     * 添加（路由）代理信息
     * @param router
     * @return
     */
    @RequestMapping("info/add")
    public BigdataResult addRouter(@RequestBody Router router){
        Boolean aBoolean = routerService.addRouter(router);
        if(aBoolean){
            return BigdataResult.ok();
        }
        return BigdataResult.build(4006,"次路由信息已存在!!");
    }
}
