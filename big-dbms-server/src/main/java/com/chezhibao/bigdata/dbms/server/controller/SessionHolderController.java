package com.chezhibao.bigdata.dbms.server.controller;

import com.chezhibao.bigdata.common.pojo.BigdataResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/2.
 */
@RestController
@RequestMapping("/dbms/server")
public class SessionHolderController {

    /**
     * 用来保持session
     * @param userId
     * @return
     */
    @RequestMapping("/session")
    public BigdataResult sessionHold(@CookieValue("LOGIN_USER_ID") Integer userId){
        return BigdataResult.ok(userId);
    }
}
