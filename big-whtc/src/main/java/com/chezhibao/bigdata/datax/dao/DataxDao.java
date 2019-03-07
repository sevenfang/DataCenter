package com.chezhibao.bigdata.datax.dao;

import com.chezhibao.bigdata.datax.pojo.DataxConfig;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/5/7
 * Created by WangCongJun on 2018/5/7.
 */
public interface DataxDao {
    /**
     * 插入数据
     * @param params 参数
     * @return
     */
    Boolean saveOrUpdate(DataxConfig params);

    /**
     * 获取任务信息
     * @param params
     * @return
     */
    List<Map<String,Object>> getJobInfo(Map<String, Object> params);

    /**
     * 查询总数
     * @param params
     * @return
     */
    Integer count(Map<String, Object> params);

    /**
     * 根据Id删除task
     * @param params
     * @return
     */
    Boolean delete(Map<String, Object> params);

    /**
     * 更新任务信息
     * @param params
     * @return
     */
    Boolean update(Map<String, Object> params);

}
