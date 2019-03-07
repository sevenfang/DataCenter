package com.chezhibao.bigdata.msg.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.msg.service.CrawlMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/11.
 */
@RestController()
@Slf4j
@RequestMapping("/noauth/msg")
public class CrawlMessageController {

    private CrawlMessageService crawlMessageService;

    public CrawlMessageController(CrawlMessageService crawlMessageService) {
        this.crawlMessageService = crawlMessageService;
    }

    @RequestMapping("/crawl/msg")
    public BigdataResult getCrawlData(String msg){
        crawlMessageService.addNewMsg(msg);
        return BigdataResult.ok();
    }
    @RequestMapping("/crawl/msg/{switch}")
    public BigdataResult switcher(@PathVariable("switch") String switcher){
        Boolean isSend=false;
        if(switcher.equals("open")){
            crawlMessageService.start();
        }else {
            crawlMessageService.stop();
        }

        return BigdataResult.ok();
    }
}
