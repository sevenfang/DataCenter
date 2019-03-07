package com.chezhibao.bigdata.service.store;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.common.log.LoggerUtils;
import com.chezhibao.bigdata.dao.CBRCommonDao;
import com.chezhibao.bigdata.search.common.SearchLogEnum;
import com.chezhibao.bigdata.search.store.StoreService;
import com.chezhibao.bigdata.search.store.bo.StoreStatusInfo;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/3.
 */
@Component
@Service(interfaceClass = StoreService.class)
public class StoreServiceImpl implements StoreService {
    private static final Logger log = LoggerUtils.Logger(SearchLogEnum.STORE);
    @Reference(check = false)
    private CBRCommonDao cbrCommonDao;
    @Autowired
    private SqlTemplateService sqlTemplateService;

    /**
     * 对接人是 李俊峰
     * @param list
     * @return
     */
    @Override
    public List<StoreStatusInfo> getCarIdsOfAbnormalStore(List<StoreStatusInfo> list) {
        if(log.isDebugEnabled()) {
            log.debug("【查询系统】查询门店异常车辆{}", list);
        }
        Map<String, StoreStatusInfo> result = new HashMap<>(list.size());
        //查询前的准备 拼装数据key
        for (StoreStatusInfo store : list) {
            String key = store.getStoreId() + "-" + store.getAbnormalStatusFlag();
            store.setCarIds(new ArrayList<>());
            result.put(key, store);
        }
        Map<String, Object> params = ParamsBean.newInstance().put("keys", result.keySet()).build();
        log.info("【查询系统】查询门店异常车辆参数{}", params);
        String sql = sqlTemplateService.getSql("store.getCarIdsOfAbnormalStore", params);
        List<LinkedHashMap<String, Object>> query = cbrCommonDao.query(sql, params);
        //处理结果中的key
        for (Map<String, Object> m : query) {
            String key = m.get("key") + "";
            Object carid = m.get("carid");
            if (ObjectUtils.isEmpty(carid)) {
                continue;
            }
            result.get(key).getCarIds().add(Integer.parseInt(carid + ""));
        }
        if(log.isDebugEnabled()){
            log.debug("【查询系统】查询门店异常车辆结果{}",result);
        }
        return new ArrayList<>(result.values());
    }
}
