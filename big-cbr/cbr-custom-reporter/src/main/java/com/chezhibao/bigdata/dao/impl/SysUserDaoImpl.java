package com.chezhibao.bigdata.dao.impl;

import com.chezhibao.bigdata.dao.CBRCommonDao;
import com.chezhibao.bigdata.dao.SysUserDao;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/5/31.
 */
@Repository
@Slf4j
public class SysUserDaoImpl implements SysUserDao {

    @Resource(name = "cBRCommonDao")
    private CBRCommonDao commonDao;

    @Autowired
    private SqlTemplateService sqlTemplateService;


    @Override
    public List<Map<String,Object>> getUserinfoById(Integer id) {
        Map<String,Object> params = ParamsBean.newInstance().put("id",id).build();
        String sql = sqlTemplateService.getSql("sys.sys_user.get_user_info", params);
        List<Map<String, Object>> userInfo = commonDao.select(sql, params);
        log.debug("【实时报表】查询用户信息：{}",userInfo.get(0));
        return userInfo;
    }
}
