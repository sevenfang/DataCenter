package com.chezhibao.bigdata.dbms.server.auth.service.impl;

import com.chezhibao.bigdata.config.client.ParamsUtil;
import com.chezhibao.bigdata.dbms.server.auth.AdminAspect;
import com.chezhibao.bigdata.dbms.server.auth.bo.DbAuthBO;
import com.chezhibao.bigdata.dbms.server.auth.service.AuthenticationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/16.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String AUTH_TMP_DB ="auth.tmp.db";
    private static final String AUTH_TMP_SCHEMA ="auth.tmp.schema";
    private static final String AUTH_TMP_TABLE ="auth.tmp.table";

    @Override
    public List<String> getDbInstance(DbAuthBO dbAuthBO) {
        //TODO 临时配置
        return ParamsUtil.getDynamicValueList(AUTH_TMP_DB,String.class);
    }

    @Override
    public List<String> getSchema(DbAuthBO dbAuthBO) {
        //TODO 临时配置
        return ParamsUtil.getDynamicValueList(AUTH_TMP_SCHEMA,String.class);
    }

    @Override
    public List<String> getDbTable(DbAuthBO dbAuthBO) {
        //TODO 临时配置
        return ParamsUtil.getDynamicValueList(AUTH_TMP_TABLE,String.class);
    }

    @Override
    public Boolean isAdministrator(Integer userId) {
        List<Integer> integers = AdminAspect.getAdminWhitelist();
        if (!integers.contains(userId)) {
            return false;
        }
        return true;
    }
}
