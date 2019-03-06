package com.chezhibao.bigdata.controller;

import com.chezhibao.bigdata.authorization.bo.AuthBO;
import com.chezhibao.bigdata.authorization.service.Authorization;
import com.chezhibao.bigdata.service.CustomerService;
import com.chezhibao.bigdata.service.SysOrgService;
import com.chezhibao.bigdata.vo.CustomerParamsInVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/6.
 */
@RestController
@RequestMapping("/cbr/customer")
@Slf4j
public class CustomerController {

    @Autowired
    private Authorization authorization;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SysOrgService sysOrgService;

    /**
     * 查询线上运营指标
     *
     * @return
     */
    @RequestMapping("/online")
    public Object getOnline(CustomerParamsInVO paramsInVO, @CookieValue("LOGIN_USER_ID") Integer userId) {
        log.info("【实时报表】线上运营指标报表，入参：{}", paramsInVO);
        Integer type = paramsInVO.getType();
        String time = paramsInVO.getTime();
        //查询组织id
        AuthBO authBO = authorization.getAuthBo(userId,null);
        List<Integer> orgIds = null;
        if (!authBO.getIsAdministrator()) {
            orgIds = authBO.getOrgIds();
        }
        Object operationalIndicators = customerService.getOperationalIndicators(paramsInVO.getId() + "", type, time, orgIds);
        log.info("【实时报表】线下运营指标报表，结果：{}", operationalIndicators);
        return operationalIndicators;
    }

    /**
     * OCSA基础数据（新）
     *
     * @param id
     * @param type
     * @param time
     * @return
     */
    @RequestMapping("loadOCSAData_new")
    @ResponseBody
    public Object loadOCSADataNew(String id, int type, String time, @CookieValue("LOGIN_USER_ID") Integer userId) {
        Map<String, Object> result;
        AuthBO authBO = authorization.getAuthBo(userId,null);
        if (authBO.getIsAdministrator()) {
            result = customerService.loadOCSADataNew(id, type, time, null);
        } else {
            List<Integer> cityIds = authBO.getCityIds();
            result = customerService.loadOCSADataNew(id, type, time, cityIds);
        }
        if (0 != type) {
            return result.get("rows");
        }
        return result;
    }
    /**
     * 查询线下运营指标
     * @return
     */
    @RequestMapping("/offline")
    public Object getRealreport(CustomerParamsInVO paramsInVO,@CookieValue("LOGIN_USER_ID") Integer userId) {
        log.info("【实时报表】线下运营指标报表，入参：{}",paramsInVO);
        Integer type = paramsInVO.getType();
        String time = paramsInVO.getTime();
        //查询组织id
        AuthBO authBO = authorization.getAuthBo(userId,null);
        List<Integer> orgIds = null;
        if (!authBO.getIsAdministrator()) {
            orgIds = sysOrgService.getOnlineOrgIdsByOfflineUserId(userId);
        }

        Object operationalIndicators = customerService.getOperationalIndicators(paramsInVO.getId() + "", type, time, orgIds);
        log.info("【实时报表】线下运营指标报表，结果：{}",operationalIndicators);
        return operationalIndicators;
    }
}
