package com.chezhibao.bigdata.service.impl;

import com.chezhibao.bigdata.bo.SysBO;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.dao.SysOrgDao;
import com.chezhibao.bigdata.dao.SysUserDao;
import com.chezhibao.bigdata.exception.SysUserOrgException;
import com.chezhibao.bigdata.service.SysOrgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/5/31.
 */
@Service
@Slf4j
public class SysOrgServiceImpl implements SysOrgService {

    @Autowired
    private SysOrgDao sysOrgDao;

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public List<Integer> getOnlineOrgIdsByOfflineUserId(Integer userId) {
        List<Integer> offlineOrgIds = getOrgIdsByUserId(userId);
        log.debug("【实时报表】通过usrId查询到的线下orgIds：{}",offlineOrgIds);
        /**
         * 这里的线下大区为空，查询的就是所有的线上大区信息（后期要更改）
         * 解决：通过判断level确定是否走这里的代码
         * {@link com.chezhibao.bigdata.realreport.controller.RealreportControler#getRealreport }
         */
        List<Integer> onlineOrgids = sysOrgDao.transOfflineOrgIdsToOnlin(offlineOrgIds);
        log.info("【实时报表】通过usrId查询到的线上orgIds：{}",onlineOrgids);
        return onlineOrgids;
    }


    @Override
    public List<Integer> getOrgIdsByUserId(Integer userId) {
        //查询用户信息
        List<Map<String, Object>> userInfoById = sysUserDao.getUserinfoById(userId);
        if(ObjectUtils.isEmpty(userInfoById)||userInfoById.size()>1){
            throw new SysUserOrgException(310001,"查询结果太多，大于1了！");
        }
        Map<String, Object> map = userInfoById.get(0);
        Object orgID = map.get("orgID");
        log.debug("【实时报表】通过usrId查询到的orgId：{}",orgID);
        if(ObjectUtils.isEmpty(orgID)){
            throw new SysUserOrgException(310002,"查询参数不正确！！");
        }
        Object idPath = map.get("orgIDPath");
        if(ObjectUtils.isEmpty(idPath)){
            throw new SysUserOrgException(310002,"查询参数不正确！！");
        }
        log.debug("【实时报表】通过usrId查询到的idPath：{}",idPath);
        List<Integer> orgIds = sysOrgDao.getUnderOrgIds(idPath.toString(), Integer.parseInt(orgID.toString()));
        log.debug("【实时报表】通过usrId查询到的线下orgIds：{}",orgIds);
        //修复一个主管也能看全国的bug
        if(ObjectUtils.isEmpty(orgIds)){
            orgIds=new ArrayList<>();
            orgIds.add(Integer.parseInt(orgID+""));
        }
        return orgIds;
    }

    @Override
    public List<Integer> getOfflineOrgIdsByUserId(SysBO sysBO) {
        //查询出所有的管理组织ID
        List<Map<String, Object>> allOrgInfosByUserId = sysOrgDao.getAllOrgInfosByUserId(sysBO.getUserId());
        ArrayList<Integer> orgIds = new ArrayList<>();
        for(Map<String,Object> m: allOrgInfosByUserId){
            String orgId = m.get("orgId") + "";
            orgIds.add(Integer.parseInt(orgId));
        }
        sysBO.setOrgIds(orgIds);
        //过滤出线下的组织ID
        Map<String, Object> params = ObjectCommonUtils.object2Map(sysBO);
        List<Map<String, Object>> offlineOrgIds = sysOrgDao.getOfflineOrgIds(params);
        log.info("【实时报表】查询的线下管理orgId：{}",offlineOrgIds);
        HashSet<Integer> hashSet = new HashSet<>();
        for(Map<String,Object> m: offlineOrgIds){
            String ou = m.get("ou") + "";
            String orgId = m.get("id") + "";
            log.info("【实时报表】ou:{},orgId:{}",ou,orgId);
            if(StringUtils.isEmpty(ou)){
                log.info("【实时报表】查询orgId：{}的ou为null",orgId);
                continue;
            }
            String idPath = m.get("idPath") + "";
            //ou为0表示其下面还有城市
            if("0".equals(ou)){
                List<Integer> underOrgIds = sysOrgDao.getUnderOrgIds(idPath, Integer.parseInt(orgId));
                hashSet.addAll(underOrgIds);
            }else {
                hashSet.add(Integer.parseInt(orgId));
            }
        }

        return new ArrayList<>(hashSet);
    }

    @Override
    public List<Integer> getOfflineOusByUserId(SysBO sysBO) {
        //查询出所有的管理组织ID
        List<Map<String, Object>> allOrgInfosByUserId = sysOrgDao.getAllOrgInfosByUserId(sysBO.getUserId());
        ArrayList<Integer> orgIds = new ArrayList<>();
        for(Map<String,Object> m: allOrgInfosByUserId){
            String orgId = m.get("orgId") + "";
            orgIds.add(Integer.parseInt(orgId));
        }
        sysBO.setOrgIds(orgIds);
        //过滤出线下的组织ID
        Map<String, Object> params = ObjectCommonUtils.object2Map(sysBO);
        List<Map<String, Object>> offlineOrgIds = sysOrgDao.getOfflineOrgIds(params);
        log.info("【实时报表】查询的线下管理orgId：{}",offlineOrgIds);
        HashSet<Integer> hashSet = new HashSet<>();
        for(Map<String,Object> m: offlineOrgIds){
            Integer ou = Integer.parseInt(m.get("ou") + "");
            String orgId = m.get("id") + "";
            log.info("【实时报表】ou:{},orgId:{}",ou,orgId);
            if(StringUtils.isEmpty(ou)){
                log.info("【实时报表】查询orgId：{}的ou为null",orgId);
                continue;
            }
            String idPath = m.get("idPath") + "";
            //ou为0表示其下面还有城市
            if(0==ou){
                List<Integer> underOus = sysOrgDao.getUnderOus(idPath, Integer.parseInt(orgId));
                hashSet.addAll(underOus);
            }else {
                hashSet.add(ou);
            }
        }
        return new ArrayList<>(hashSet);
    }

    @Override
    public List<Integer> getManageCityNames(SysBO sysBO) {
        List<Integer> cityIds = getOfflineOusByUserId(sysBO);

        return null;
    }
}
