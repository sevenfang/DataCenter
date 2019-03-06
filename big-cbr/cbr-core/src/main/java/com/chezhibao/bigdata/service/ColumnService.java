package com.chezhibao.bigdata.service;

import com.chezhibao.bigdata.bo.CBRColumnBO;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/11.
 */
public interface ColumnService {
    /**
     * 批量新增
     * @param cbrColumnBOS
     * @return
     */
    Boolean saveOrUpdateColumn(List<CBRColumnBO> cbrColumnBOS);

    /**
     * 新增字段
     * @param cbrColumnBO
     * @return
     */
    Boolean saveColumn(CBRColumnBO cbrColumnBO);

    /**
     * 通过Order更新字段
     * @param cbrColumnBO
     * @return
     */
    Boolean updateColumnByRealreportIdAndKey(CBRColumnBO cbrColumnBO);
    /**
     * 通过Id更新字段
     * @param cbrColumnBO
     * @return
     */
    Boolean updateColumnById(CBRColumnBO cbrColumnBO);
    /**
     * 通过Id删除字段
     * @param id
     * @return
     */
    Boolean deleteColumnById(List<Integer> id);
    /**
     * 通过Id获得字段
     * @param id
     * @return
     */
    CBRColumnBO getColumnById(Integer id);

    /**
     * 根据顺序查找字段
     * @param realreportId
     * @return
     */
    CBRColumnBO getColumnByRealreportIdAndKey(String realreportId, String key);
    /**
     * 根据报表ID查找字段
     * @param realreportId
     * @return
     */
    List<CBRColumnBO> getColumnByRealreportId(String realreportId);
}
