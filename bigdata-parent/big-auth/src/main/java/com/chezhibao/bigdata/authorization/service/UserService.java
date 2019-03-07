package com.chezhibao.bigdata.authorization.service;

import com.chezhibao.bigdata.authorization.bo.SysRole;
import com.chezhibao.bigdata.authorization.bo.UserInfo;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/30.
 */
public interface UserService {
    /**
     * 获取人员角色
     * @param id
     * @return
     */
    List<SysRole> getUserRoles(Integer id);

    /**
     * 获取组织机构ID Path
     * @param id
     * @return
     */
    List<String> getIdPathsById(Integer id);

    /**
     * 通过角色code获取用户的等级
     * 1、超级管理员
     * 。。。。。
     * @param userId
     * @return
     */
    Integer getLevel(Integer userId);
    /**
     * 判断是否是数据部人员
     * @param userId
     * @return
     */
    Boolean isSJB(Integer userId);

    /**
     * 检查用户是否登陆
     * @param id
     * @return 用户登陆信息 null 没有获取到登陆信息
     */
    UserInfo getCacheUserById(Integer id);
}
