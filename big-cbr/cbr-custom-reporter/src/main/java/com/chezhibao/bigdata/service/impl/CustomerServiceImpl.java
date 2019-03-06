package com.chezhibao.bigdata.service.impl;

import com.chezhibao.bigdata.common.utils.SortUtils;
import com.chezhibao.bigdata.dao.CustomerDao;
import com.chezhibao.bigdata.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/5/28.
 */
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public Object getOperationalIndicators( String id, int type, String time, List<Integer> orgIds) {
        time = ("".equals(time) || "null".equals(time)) ? null : time;
        if (0 != type && null == time) {
            return "";
        }
        // 非空处理处理time
        if (time == null) {
            time = customerDao.getMaxLoadDateTime();
            log.debug("【实时报表】确定最大时间：{}",time);
        }
        Map<String,Object> result;
        log.info("【实时报表】查询参数id：{}；type：{}；time：{}，orgIds：{}",id,type,time,orgIds);
        result = loadData(id, type, time, orgIds);
        log.info("【实时报表】查询结果：{}",result);
        if (0 != type) {
            return result.get("rows");
        }

        return result;
    }

    private Map<String,Object> loadData(String id, int type, String time, List<Integer> orgIds){
        Map<String, Object> result = new HashMap<String, Object>();

        time = ("".equals(time) || "null".equals(time)) ? null : time;
        if (0 != type && null == time) {
            return result;
        }
        // 非空处理处理time
        if (time == null) {
            time = customerDao.getMaxLoadDateTime();
            log.debug("【实时报表】确定最大时间：{}",time);
        }
        List<Map<String, Object>> list = customerDao.loadData(id, type, time, orgIds);
        for (Map<String, Object> map : list) {
            // treegread组件需要参数,root层不需要
            if (0 != type) {
                map.put("_parentId", id);
            }
            // treegread组件需要参数 最后一层不需要折叠
            if (3 != type) {
                map.put("state", "closed");
            }
            map.put("type", type + 1);
            map.put("time", time);
        }
        SortUtils.customerSort(list);
        result.put("rows", list);
        result.put("total", result.size());
        result.put("loadDateTime", time);
        return result;
    }

    /**
     * OCSA基础数据（新）
     * @param id
     * @param type
     * @param time
     * @param cityIds
     * @return
     */
    @Override
    public Map<String, Object> loadOCSADataNew(String id, int type, String time, List<Integer> cityIds) {
        Map<String, Object> result = new HashMap<String, Object>();
        time = ("".equals(time) || "null".equals(time)) ? null : time;
        if (0 != type && null == time)
        {
            return result;
        }
        // 非空处理处理time
        if (time == null)
        {
            time = customerDao.getMaxLoadOCSADateTimeNew();
        }
        List<Map<String, Object>> list = customerDao.loadOCSADataNew(id, type, time, cityIds);
        for (Map<String, Object> map : list)
        {
            // treegread组件需要参数,root层不需要
            if (0 != type)
            {
                map.put("_parentId", id);
            }
            // treegread组件需要参数 最后一层不需要折叠
            if (3 != type)
            {
                map.put("state", "closed");
            }
            map.put("type", type + 1);
            map.put("time", time);

        }
        SortUtils.customerSort(list);
        result.put("rows", list);
        result.put("total", result.size());
        result.put("loadDateTime", time);
        return result;
    }
}
