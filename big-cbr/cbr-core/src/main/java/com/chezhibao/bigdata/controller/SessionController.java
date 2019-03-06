package com.chezhibao.bigdata.controller;

import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.service.SessionService;
import com.chezhibao.bigdata.vo.SessionVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/6/22.
 */
@RestController
@Slf4j
@RequestMapping("/cbr/auction")
public class SessionController {

    private SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * 返回所有的有用的场次
     * @return
     */
    @GetMapping("session")
    public BigdataResult getAllSessions(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String start = sdf.format(DateUtils.addDays(date,-4))+" 00:00:00";
        List<String> allSignificantSession = sessionService.getAllSignificantSession(start);
        return BigdataResult.ok(allSignificantSession);
    }

    /**
     * 返回所有的有用的场次
     * @return
     */
    @GetMapping("all/session")
    public BigdataResult getSessions(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String start = sdf.format(DateUtils.addDays(date,-4))+" 00:00:00";
        List<Map<String,Object>> allSignificantSession = sessionService.getAllSignificantSessions(start);
        List<SessionVO> sessionVOS = ObjectCommonUtils.map2Object(allSignificantSession, SessionVO.class);
        return BigdataResult.ok(sessionVOS);
    }

    /**
     * 返回所有过滤的场次
     * @return
     */
    @GetMapping("session/exclusion")
    public BigdataResult getAllExcludeSessions(){
        List<String> allExcludeSession = sessionService.getAllExcludeSession();
        return BigdataResult.ok(allExcludeSession);
    }

    /**
     * 从过滤列表删除过滤场次
     * @return
     */
    @DeleteMapping("session")
    public BigdataResult delSession(String[] name){
        if(ObjectUtils.isEmpty(name)){
            log.error("【实时报表】删除过滤竞拍场次！场次为空");
            return BigdataResult.build(400,"删除过滤竞拍场次");
        }
        List<String> strings = Arrays.asList(name);
        log.info("【实时报表】竞拍场次删除name:"+strings);
        sessionService.delExcludeSession(strings);
        return BigdataResult.ok();
    }

    /**
     * 添加过滤场次到过滤列表
     * @return
     */
    @PostMapping("session")
    public BigdataResult addSession(String[] name){
        if(ObjectUtils.isEmpty(name)){
            log.error("【实时报表】添加过滤竞拍场次！场次为空");
            return BigdataResult.build(400,"删除过滤竞拍场次");
        }

        List<String> strings = Arrays.asList(name);
        log.info("【实时报表】竞拍场次添加name:"+ strings);

        sessionService.addExcludeSession(strings);
        return BigdataResult.ok();
    }
}
