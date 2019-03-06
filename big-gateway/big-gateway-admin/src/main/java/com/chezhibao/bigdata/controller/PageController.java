package com.chezhibao.bigdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/5.
 */
@Controller
public class PageController {
    @RequestMapping({"index","/"})
    public String index(){
        return "index";
    }
}
