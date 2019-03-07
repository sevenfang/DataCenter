package com.chezhibao.bigdata.authorization.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import static com.chezhibao.bigdata.authorization.LoggerUtils.*;
import com.chezhibao.bigdata.authorization.bo.SysRole;
import com.chezhibao.bigdata.authorization.bo.UserInfo;
import com.chezhibao.bigdata.authorization.service.UserService;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.config.client.ParamsUtil;
import com.chezhibao.bigdata.dao.CBRCommonDao;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import com.lebo.chezhibao.org.entity.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/30.
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * 数据部人员列表
     */
    private static final String SJB_USER_LIST = "auth.sjb_user_list";
    /**
     * 超管人员列表
     */
    private static final String ADM_USER_LIST = "auth.adm_user_list";

    @Autowired
    private SqlTemplateService sqlTemplateService;

    @Reference(check = false)
    private CBRCommonDao cbrCommonDao;

    @Reference(check = false)
    private com.lebo.chezhibao.org.user.service.UserService user;

    @Override
    public List<SysRole> getUserRoles(Integer id) {
        Map<String, Object> params = ParamsBean.newInstance().put("userId", id).build();
        String sql = sqlTemplateService.getSql("sys.UserService.getUserRoles", params);
        List<Map<String, Object>> select = cbrCommonDao.select(sql, params);
        List<SysRole> sysRoles = ObjectCommonUtils.map2Object(select, SysRole.class);
        if(LOG_AUTH_SYS.isDebugEnabled()){
            LOG_AUTH_SYS.debug("【权鉴系统】查询用户{}角色结果：{}",id,sysRoles);
        }
        return sysRoles;
    }

    @Override
    public List<String> getIdPathsById(Integer id) {
        Map<String, Object> params = ParamsBean.newInstance().put("userId", id).build();
        String sql = sqlTemplateService.getSql("sys.UserService.getIdPathsById", params);
        List<Map<String, Object>> select = cbrCommonDao.select(sql, params);
        List<String> result = new ArrayList<>(select.size());
        for(Map<String,Object> m: select){
            Object idPath = m.get("idPath");
            if(StringUtils.isEmpty(idPath)){
                continue;
            }
            result.add(idPath+"");
        }
        if(LOG_AUTH_SYS.isDebugEnabled()){
            LOG_AUTH_SYS.debug("【权鉴系统】查询用户{}IdPath结果：{}",id,result);
        }
        return result;
    }

    @Override
    public Boolean isSJB(Integer userId) {
        //判断是否时数据部人员
        List<Integer> dynamicValueList = ParamsUtil.getDynamicValueList(SJB_USER_LIST, Integer.class);
        return dynamicValueList.contains(userId);
    }

    @Override
    public Integer getLevel(Integer userId) {
        List<SysRole> userRoles = getUserRoles(userId);
        List<String> codes = new ArrayList<>();
        for(SysRole s : userRoles){
            codes .add(s.getCode());
        }
        //判断是否是超管人员
        List<String> dynamicValueList = ParamsUtil.getDynamicValueList(ADM_USER_LIST, String.class);
        for(String code : dynamicValueList){
            if(codes.contains(code)){
                LOG_AUTH_SYS.info("【权鉴系统】超管：{}访问报表",userId);
                return 1;
            }
        }
        return 0;
    }

    @Override
    public UserInfo getCacheUserById(Integer id) {
        UserInfo userInfo = null;
        SysUser cacheUser = user.getCacheUser(id);
        if(LOG_AUTH_SYS.isDebugEnabled()){
            LOG_AUTH_SYS.debug("【权鉴系统】用户信息：{}",cacheUser);
        }
        if(cacheUser!=null){
            user.cacheUser(cacheUser);
            userInfo = new UserInfo();
            BeanUtils.copyProperties(cacheUser,userInfo);
        }
        return userInfo;
    }
}
