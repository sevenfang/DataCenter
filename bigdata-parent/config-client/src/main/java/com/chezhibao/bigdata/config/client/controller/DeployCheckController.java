package com.chezhibao.bigdata.config.client.controller;

import com.chezhibao.bigdata.common.pojo.BigdataResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/23.
 */
@RestController
@RequestMapping("/deploy")
public class DeployCheckController {
    /**
     * 发布成功检查
     * @return
     */
    @RequestMapping("/check")
    public BigdataResult check(){
        return BigdataResult.ok();
    }
}
