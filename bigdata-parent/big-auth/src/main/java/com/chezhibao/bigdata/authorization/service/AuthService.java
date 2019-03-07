package com.chezhibao.bigdata.authorization.service;

import com.chezhibao.bigdata.common.pojo.BigdataResult;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/4.
 */
public interface AuthService {

    Boolean isLogin(Integer id);

    void sendToLoginPage() throws Exception;

    /**
     *
     * @param path mapping 中的路径
     * @throws Exception
     */
    @Deprecated
    void loginHandler(String path)throws Exception;

    /**
     *
     * @param path
     * @throws Exception
     */
    BigdataResult loginCheck(String path)throws Exception;

}
