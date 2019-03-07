package com.chezhibao.bigdata.authorization.service;


import com.chezhibao.bigdata.authorization.bo.AuthBO;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/2.
 */
public interface Authorization {
    /**
     * 根据用户ID获取，所管辖的城市名称
     */
    AuthBO getCityNameByUseId(Integer userId);
    /**
     * 根据用户ID获取，所管辖的城市Id
     */
    AuthBO getAuthBo(Integer userId);

    /**
     * 数据部中的人员自助查看服务接口
     * 1、当数据部内部人员登陆时可以动态指定模拟人员查看报表
     * @param userId
     * @param simulatorId
     * @return 模拟人员的ID
     */
    Integer getCityIdBySJBUser(Integer userId, Integer simulatorId);

    /**
     * 获取权鉴对象
     * @param userId
     * @param simulatorId 模拟人员ID
     * @return
     */
    AuthBO getAuthBo(Integer userId, Integer simulatorId);

    /**
     * 根据userId获取对应的管理部门ID,
     *
     * @param userId
     * @return AuthBO中的authParams为部门ID列表
     */
    AuthBO getManagerOrgIds(Integer userId);

}
