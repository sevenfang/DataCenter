package com.chezhibao.bigdata.controller;

import com.chezhibao.bigdata.config.client.ParamsUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/13.
 */
@RestController
@RequestMapping("/cbr")

public class TestController {
    @RequestMapping("auth/test")
    public Object test(HttpServletResponse response) throws Exception{

        return ParamsUtil.getDynamicValue("auth.sjb_user_list");
    }
    @RequestMapping("free/1")
    public Object test() throws Exception{
        return ParamsUtil.getDynamicValue("auth.sjb_user_list");
    }
}
