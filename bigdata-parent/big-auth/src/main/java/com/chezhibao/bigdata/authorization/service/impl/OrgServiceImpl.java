package com.chezhibao.bigdata.authorization.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.authorization.service.OrgService;
import com.chezhibao.bigdata.authorization.service.UserService;
import com.chezhibao.bigdata.dao.CBRCommonDao;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import static com.chezhibao.bigdata.authorization.LoggerUtils.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/2.
 */
@Service
public class OrgServiceImpl implements OrgService {

    @Reference(check = false)
    private CBRCommonDao cbrCommonDao;

    @Autowired
    private SqlTemplateService sqlTemplateService;

    @Autowired
    private UserService userService;

    @Override
    public List<String> getCityNameByOrgIds(List<Integer> orgIds) {

        Map<String, Object> params = ParamsBean.newInstance().put("orgIds", orgIds).build();
        String sql = sqlTemplateService.getSql("authorization.OrgService.getCityNameByOrgIds", params);
        List<LinkedHashMap<String, Object>> query = cbrCommonDao.query(sql, params);
        if(ObjectUtils.isEmpty(query)){
            LOG_AUTH_SYS.error("【CBR系统】权限校验，查询用户的管辖城市名称返回为空,orgIds:{}",orgIds);
            return null;
        }
        //取出查询的城市名称放入结果中
        List<String> result = new ArrayList<>();
        for(LinkedHashMap<String, Object> m: query){
            String cityName = m.get("cityName") + "";
            result.add(cityName);
        }
        return result;
    }

    @Override
    public List<Integer> getCityIdByOrgIds(List<Integer> orgIds) {
        Map<String, Object> params = ParamsBean.newInstance().put("orgIds", orgIds).build();
        String sql = sqlTemplateService.getSql("authorization.OrgService.getCityIdByOrgIds", params);
        List<LinkedHashMap<String, Object>> query = cbrCommonDao.query(sql, params);
        if(ObjectUtils.isEmpty(query)){
            LOG_AUTH_SYS.error("【CBR系统】权限校验，查询用户的管辖城市ID返回为空,orgIds:{}",orgIds);
            return null;
        }
        List<Integer> result = getCityIds(query);
        if(LOG_AUTH_SYS.isDebugEnabled()) {
            LOG_AUTH_SYS.debug("【CBR系统】权限校验，查询用户的orgIds:{}的管辖城市ID,cityIds:{}", orgIds, result);
        }
        return result;
    }

    private List<Integer> getCityIds(List<LinkedHashMap<String, Object>> query){
        //取出查询的城市ID放入结果中
        List<Integer> result = new ArrayList<>();
        for(LinkedHashMap<String, Object> m: query){
            Object cityId = m.get("cityId");
            if(!ObjectUtils.isEmpty(cityId)){
                result.add(Integer.parseInt(cityId+""));
            }
        }
        return result;
    }

    @Override
    public List<Integer> getManageCityIdByUserId(Integer userId) {
        Map<String, Object> params = ParamsBean.newInstance().put("regIdPaths", getRegIdPath(userId)).build();
        String sql = sqlTemplateService.getSql("authorization.OrgService.getManageCityIdByUserId", params);
        List<LinkedHashMap<String, Object>> query = cbrCommonDao.query(sql, params);
        if(ObjectUtils.isEmpty(query)){
            return new ArrayList<>();
        }
        List<Integer> result = getCityIds(query);
        if(LOG_AUTH_SYS.isDebugEnabled()){
            LOG_AUTH_SYS.debug("【CBR系统】权限校验，查询用户:{}的管辖城市ID,cityIds:{}",userId,result);
        }
        return result;
    }

    @Override
    public List<Integer> getManageOrgIdByUserId(Integer userId) {
        Map<String, Object> params = ParamsBean.newInstance().put("regIdPaths", getRegIdPath(userId)).build();
        String sql = sqlTemplateService.getSql("authorization.OrgService.getManageOrgIdByUserId", params);
        List<LinkedHashMap<String, Object>> query = cbrCommonDao.query(sql, params);
        List<Integer> result = getOrgIds(query);
        if(LOG_AUTH_SYS.isDebugEnabled()){
            LOG_AUTH_SYS.debug("【CBR系统】权限校验，查询用户:{}的管辖组织ID,orgIds:{}",userId,result);
        }
        return result;
    }
    private List<Integer> getOrgIds(List<LinkedHashMap<String, Object>> query){
        //取出查询的城市ID放入结果中
        List<Integer> result = new ArrayList<>();
        for(LinkedHashMap<String, Object> m: query){
            Object orgId = m.get("orgId");
            if(!ObjectUtils.isEmpty(orgId)){
                result.add(Integer.parseInt(orgId+""));
            }
        }
        return result;
    }
    private String getRegIdPath(Integer userId){
        List<String> paths = userService.getIdPathsById(userId);
        if(ObjectUtils.isEmpty(paths)){
            return null;
        }
        //拼装正则查询语句
        StringBuilder regIdpas = new StringBuilder();
        for(String path : paths){
            regIdpas.append("|").append("^").append(path).append(".*");
        }
        String substring = regIdpas.substring(1);
        if(LOG_AUTH_SYS.isDebugEnabled()){
            LOG_AUTH_SYS.debug("【权鉴系统】查询用户:{}的管辖IDPath正则表达式拼装结果:{}",userId,substring);
        }
        return substring;
    }

}
