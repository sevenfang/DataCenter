package com.chezhibao.bigdata.authorization.service;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/2.
 */
public interface OrgService {
    List<String> getCityNameByOrgIds(List<Integer> orgIds);
    List<Integer> getCityIdByOrgIds(List<Integer> orgIds);

    /**
     * 获取管理城市的ID
     * @param userId 用户ID
     * @return
     */
    List<Integer> getManageCityIdByUserId(Integer userId);
    /**
     * 获取管理组织ID
     * @param userId 用户ID
     * @return
     */
    List<Integer> getManageOrgIdByUserId(Integer userId);
}
