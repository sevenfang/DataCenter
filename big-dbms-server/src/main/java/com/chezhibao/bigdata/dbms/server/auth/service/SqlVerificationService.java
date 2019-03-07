package com.chezhibao.bigdata.dbms.server.auth.service;

import com.chezhibao.bigdata.dbms.server.auth.bo.DbAuthBO;
import com.chezhibao.bigdata.dbms.server.vo.DbSqlExecuteVO;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/16.
 */
public interface SqlVerificationService {
    /**
     * 校验sql
     * @param dbAuthBO
     * @return
     */
    Boolean verifySql(DbAuthBO dbAuthBO);
    void verifySql(DbSqlExecuteVO executeVO,Integer userId);
}
