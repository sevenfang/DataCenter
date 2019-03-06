package com.chezhibao.bigdata.service;

import com.chezhibao.bigdata.bo.SysBO;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/5/31.
 */
public interface SysOrgService {

    /**
     * 通过线下用户ID查询对应的线上的组织ID
     * @param userId
     * @return
     */
    List<Integer> getOnlineOrgIdsByOfflineUserId(Integer userId);

    /**
     * 通过用户ID查询对应管理的组织ID
     * @param userId
     * @return
     */
    List<Integer> getOrgIdsByUserId(Integer userId);

    /**
     * 根据用户ID获取线下组织Id
     * @param sysBO
     * @return
     */
    List<Integer> getOfflineOrgIdsByUserId(SysBO sysBO);

    /**
     * 根据用户ID获取线下ou(ou 就是对应的cityId)
     * @param sysBO
     * @return
     */
    List<Integer> getOfflineOusByUserId(SysBO sysBO);

    /**
     * 获取线下管理cityNames
     * @param sysBO
     * @return
     */
    List<Integer> getManageCityNames(SysBO sysBO);
}
