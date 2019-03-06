package com.chezhibao.bigdata.dao;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/5/31.
 */
public interface SysUserDao {

    /**
     * 查询用户信息
     * @param id
     * @return
     */
    List<Map<String,Object>> getUserinfoById(Integer id);
}
