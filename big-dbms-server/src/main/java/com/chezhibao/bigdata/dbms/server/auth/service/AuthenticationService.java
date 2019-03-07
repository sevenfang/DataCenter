package com.chezhibao.bigdata.dbms.server.auth.service;


import com.chezhibao.bigdata.dbms.server.auth.bo.DbAuthBO;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/16.
 */
public interface AuthenticationService {
    /**
     * 查询可以看的数据实例
     * @param dbAuthBO
     * @return
     */
    List<String> getDbInstance(DbAuthBO dbAuthBO);

    /**
     * 查询可以看的数据schema
     * @param dbAuthBO
     * @return
     */
    List<String> getSchema(DbAuthBO dbAuthBO);
    /**
     * 查询可以看的数据表
     * @param dbAuthBO
     * @return
     */
    List<String> getDbTable(DbAuthBO dbAuthBO);

    /**
     * 判断是否是超级管理员
     * @param userId
     * @return
     */
    Boolean isAdministrator(Integer userId);
}
