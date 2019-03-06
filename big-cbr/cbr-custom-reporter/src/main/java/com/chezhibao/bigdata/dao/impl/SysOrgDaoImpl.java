package com.chezhibao.bigdata.dao.impl;

import com.chezhibao.bigdata.dao.CBRCommonDao;
import com.chezhibao.bigdata.dao.SysOrgDao;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import com.chezhibao.bigdata.util.SelectResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/5/31.
 */
@Repository
public class SysOrgDaoImpl implements SysOrgDao {

    private static final String SYS_DS="dw_v2";

    @Resource(name="cBRCommonDao")
    private CBRCommonDao commonDao;

    @Autowired
    private SqlTemplateService sqlTemplateService;
    @Override
    public List<Integer> getUnderOrgIds(String idPath,Integer orgId) {
        Map<String, Object> params = ParamsBean.newInstance().put("idPath", idPath).put("id", orgId).build();
        String sql = sqlTemplateService.getSql("sys.sys_org.get_under_user_orgid", params);
        List<Map<String, Object>> orgIds = commonDao.select(sql, params);
        return SelectResultUtils.flatMap(orgIds);
    }

    @Override
    public List<Integer> getUnderOus(String idPath, Integer orgId) {
        Map<String, Object> params = ParamsBean.newInstance().put("idPath", idPath).put("id", orgId).build();
        String sql = sqlTemplateService.getSql("sys.sys_org.getUnderOus", params);
        List<Map<String, Object>> orgIds = commonDao.select(sql, params);
        if(ObjectUtils.isEmpty(orgIds)){
          return new ArrayList<>();
        }
        return SelectResultUtils.flatMap(orgIds);
    }

    @Override
    public List<Integer> transOfflineOrgIdsToOnlin(List<Integer> orgIds) {
        Map<String, Object> params = ParamsBean.newInstance().put("orgIds", orgIds).build();
        String sql = sqlTemplateService.getSql("sys.sys_org.trans_offline_to_online", params);
        List<Map<String, Object>> orgIdsOnline = commonDao.select(sql, params);
        return SelectResultUtils.flatMap(orgIdsOnline);
    }

    @Override
    public List<Map<String, Object>> getOrgInfoByOrgId(Integer orgId) {
        Map<String, Object> params = ParamsBean.newInstance().put("id", orgId).build();
        String sql = sqlTemplateService.getSql("sys.sys_org.get_user_org_info", params);
        List<Map<String, Object>> orgInfos = commonDao.select(sql, params);
        return orgInfos;
    }

    @Override
    public List<Map<String, Object>> getAllOrgInfosByUserId(Integer userId) {
        Map<String, Object> params = ParamsBean.newInstance().put("userId", userId).build();
        String sql = sqlTemplateService.getSql("sys.sys_org.getAllOrgInfosByUserId", params);
        return commonDao.select(sql, params);
    }

    @Override
    public List<Map<String, Object>> getOfflineOrgIds(Map<String, Object> params) {
        String sql = sqlTemplateService.getSql("sys.sys_org.getOfflineOrgIds", params);
        return commonDao.select(sql, params);
    }
}
