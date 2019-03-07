package com.chezhibao.bigdata.system.service;

import com.chezhibao.bigdata.common.pojo.AppBasicInfo;

import java.util.List;

/**
 * 系统运行信息管理服务
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/22.
 */
public interface SystemRuntimeStatusService {
    /**
     * 根据系统名称获取系统的运行信息
     * @param sysName
     */
    List<AppBasicInfo> getSystemInfoBySystemName(String sysName);
}
