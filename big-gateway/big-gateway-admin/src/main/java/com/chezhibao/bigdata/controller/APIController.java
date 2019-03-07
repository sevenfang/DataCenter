package com.chezhibao.bigdata.controller;

import com.chezhibao.bigdata.gateway.pojo.ApiInfo;
import com.chezhibao.bigdata.service.ApiManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/7.
 */
@RestController
@RequestMapping("/admin/gateway")
public class APIController {

    @Autowired
    private ApiManageService apiManageService;

    @PostMapping("/api")
    public Object createApi(@RequestBody ApiInfo apiInfo) {
        System.out.println(apiInfo);
        apiManageService.saveApi(apiInfo);
        return "0000";
    }
}
