package com.chezhibao.bigdata.dao;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/5/31.
 */
public interface SysOrgDao {

    /**
     * 查询用户下面管理的组织id
     * @param orgId 组织ID
     * @return
     */
    List<Integer> getUnderOrgIds(String idPath, Integer orgId);

    /**
     * 查询用户下面管理的组织信息
     * @param orgId 组织ID
     * @return
     */
    List<Integer> getUnderOus(String idPath, Integer orgId);

    /**
     * 将线下组织id转换成线上组织id
     * @param orgIds
     * @return
     */
    List<Integer> transOfflineOrgIdsToOnlin(List<Integer> orgIds);

    /**
     * 将线下组织id转换成线上组织id
     * @param orgId
     * @return
     */
    List<Map<String,Object>> getOrgInfoByOrgId(Integer orgId);

    /**
     * 根据userId查询所有orgId（包括兼职）信息
     * @param userId
     * @return
     */
    List<Map<String,Object>> getAllOrgInfosByUserId(Integer userId);

    /**
     * 查询线下运营组织信息
     * @param params
     * @return
     */
    List<Map<String,Object>> getOfflineOrgIds(Map<String, Object> params);
}
