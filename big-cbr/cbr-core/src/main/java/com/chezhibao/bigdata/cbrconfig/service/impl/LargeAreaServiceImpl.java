package com.chezhibao.bigdata.cbrconfig.service.impl;

import com.chezhibao.bigdata.cbrconfig.dao.LargeAreaDao;
import com.chezhibao.bigdata.cbrconfig.pojo.LargeArea;
import com.chezhibao.bigdata.cbrconfig.service.LargeAreaService;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/18.
 */
@Service
@Slf4j
public class LargeAreaServiceImpl implements LargeAreaService {


    private LargeAreaDao largeAreaDao;

    public LargeAreaServiceImpl(LargeAreaDao largeAreaDao) {
        this.largeAreaDao = largeAreaDao;
    }

    @Override
    public List<LargeArea> getAllLargeAreaInfo() {
        List<LinkedHashMap<String, Object>> allLargeArea = largeAreaDao.getAllLargeArea();
        List<LargeArea> largeAreas = new ArrayList<>();
        LargeArea largeArea ;
        for(LinkedHashMap<String, Object> m : allLargeArea){
            largeArea = ObjectCommonUtils.map2Object(m,LargeArea.class);
            largeAreas.add(largeArea);
        }
        return largeAreas;
    }

    @Override
    public Boolean updateLargeArea(LargeArea largeArea) {
        if(largeArea.getId()==null){
            log.error("【CBR系统服务】更新大区信息出错！！Id为空");
            return false;
        }
        //TODO 此处更新了largeArea的名称那么 channel_user表的名称也要更改
        Map<String, Object> map = ObjectCommonUtils.object2Map(largeArea);
        largeAreaDao.updateLargeArea(map);
        return true;
    }

    @Override
    public Boolean delLargeArea(LargeArea largeArea) {
        if(largeArea.getId()==null){
            log.error("【CBR系统服务】删除大区信息出错！！Id为空");
            return false;
        }
        //TODO 在channel_user表中的数据不为空  则不能删除
        largeAreaDao.delLargeArea(largeArea.getId());
        return true;
    }

    @Override
    public Boolean saveLargeArea(LargeArea largeArea) {
        Map<String, Object> map = ObjectCommonUtils.object2Map(largeArea);
        largeAreaDao.saveLargeArea(map);
        return true;
    }

    @Override
    public Map<Integer, String> getLargeAreaIdAndName() {
        Map<Integer, String> result = new HashMap<>();
        List<LargeArea> allLargeAreaInfo = getAllLargeAreaInfo();
        for(LargeArea l : allLargeAreaInfo){
            result.put(l.getLargeAreaId(),l.getLargeAreaName());
        }
        return result;
    }
}
