package com.chezhibao.bigdata.authorization.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.authorization.annotation.LoginCheck;
import com.chezhibao.bigdata.authorization.bo.AuthBO;
import com.chezhibao.bigdata.authorization.service.Authorization;
import com.chezhibao.bigdata.authorization.service.OrgService;
import com.chezhibao.bigdata.authorization.service.UserService;
import com.chezhibao.bigdata.config.client.ParamsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

import static com.chezhibao.bigdata.authorization.LoggerUtils.LOG_AUTH_SYS;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/2.
 */
@Service
public class AuthorizationImpl implements Authorization {


    @Autowired
    private OrgService orgService;

    @Autowired
    private UserService userService;

    /**
     * 1、通过老报表中的接口获取组织ID，
     * 2、通过组织ID获取对应的城市分组
     *
     * @param userId
     * @return
     */
    @Override
    public AuthBO getCityNameByUseId(Integer userId) {
        AuthBO authBO = new AuthBO();
        Integer level = userService.getLevel(userId);
        if(LOG_AUTH_SYS.isDebugEnabled()) {
            LOG_AUTH_SYS.debug("【实时报表】根据用户ID:{},查询管理角色信息:{}", userId, level);
        }
        if (level == 1) {
            //是超级管理员直接返回
            authBO.setIsAdministrator(true);
            return authBO;
        }
        //不是超级管理员 则需要权鉴参数
        authBO.setIsAdministrator(false);
        List<Integer> orgIds = orgService.getManageOrgIdByUserId(userId);
        if(LOG_AUTH_SYS.isDebugEnabled()) {
            LOG_AUTH_SYS.debug("【CBR系统】根据用户ID:{}查询管理部门信息:{}", userId, orgIds);
        }
        List<Integer> orgids = orgIds;
        if (ObjectUtils.isEmpty(orgids)) {
            LOG_AUTH_SYS.error("【CBR系统】权限校验，查询用户的管辖组织ID为空");
            return authBO;
        }
        authBO.setOrgIds(orgids);
        //获取城市名称
        List<String> cityNames = orgService.getCityNameByOrgIds(orgids);
        if (ObjectUtils.isEmpty(cityNames)) {
            return authBO;
        }
        authBO.setAuthParams(cityNames);
        return authBO;
    }


    @Override
    public AuthBO getAuthBo(Integer userId) {
        AuthBO authBO = new AuthBO();
        Integer level = userService.getLevel(userId);
        if(LOG_AUTH_SYS.isDebugEnabled()) {
            LOG_AUTH_SYS.debug("【实时报表】根据用户ID:{},查询管理角色信息:{}", userId, level);
        }
        if (level == 1) {
            //是超级管理员直接返回
            authBO.setIsAdministrator(true);
            return authBO;
        }
        List<Integer> orgids = orgService.getManageOrgIdByUserId(userId);
        //不是超级管理员 则需要权鉴参数
        authBO.setIsAdministrator(false);
        if(LOG_AUTH_SYS.isDebugEnabled()) {
            LOG_AUTH_SYS.debug("【CBR系统】根据用户ID:{}查询管理部门信息:{}", userId, orgids);
        }
        if (ObjectUtils.isEmpty(orgids)) {
            LOG_AUTH_SYS.error("【CBR系统】权限校验，查询用户的管辖组织ID为空");
            return authBO;
        }
        authBO.setOrgIds(orgids);
        //获取城市Id
        List<Integer> cityIds = orgService.getManageCityIdByUserId(userId);
        if (ObjectUtils.isEmpty(cityIds)) {
            return authBO;
        }
        authBO.setAuthParams(cityIds);
        authBO.setCityIds(cityIds);
        return authBO;
    }

    @Override
    public Integer getCityIdBySJBUser(Integer userId,Integer simulatorId ) {
        try {
            if (userService.isSJB(userId)) {
                LOG_AUTH_SYS.info("【CBR】人员ID:{}为数据部内部人员", userId);
                //默认2275
                return simulatorId==null?2275:simulatorId;
            }
        } catch (Exception e) {
            LOG_AUTH_SYS.error("【CBR】获取模拟人员ID失败，userId:{}", userId, e);
        }
        return null;
    }

    @Override
    public AuthBO getAuthBo(Integer userId, Integer simulatorId) {
        //查询模拟人员的ID
        Integer cityIdBySJBUser = getCityIdBySJBUser(userId, simulatorId);
        //用模拟人员的ID覆盖请求ID
        userId=cityIdBySJBUser != null?cityIdBySJBUser:userId;
        //调用正常的查询流程
        return getAuthBo(userId);
    }

    @Override
    public AuthBO getManagerOrgIds(Integer userId) {
        AuthBO authBO = new AuthBO();
        Integer level = userService.getLevel(userId);
        List<Integer> orgIds=null;
        if(level.equals(1) || userService.isSJB(userId)){
            authBO.setIsAdministrator(true);
            return authBO;
        }
        authBO.setIsAdministrator(false);
        authBO.setAuthParams(orgService.getManageOrgIdByUserId(userId));
        return authBO;
    }
}
