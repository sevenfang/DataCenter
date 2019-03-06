package com.chezhibao.bigdata.service;

import com.chezhibao.bigdata.bo.CBRParamBO;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/15.
 */
public interface CBRParamService {
    /**
     * 保存报表参数
     * @param cbrParamBO
     * @return
     */
    Boolean saveParam(CBRParamBO cbrParamBO);

    /**
     * 更新报表参数
     * @param cbrParamBO
     * @return
     */
    Boolean updateParamByName(CBRParamBO cbrParamBO);
    /**
     * 更新报表参数
     * @param id
     * @return
     */
    Boolean deleteParamById(List<Integer> id);
    /**
     * 根据Id获取报表参数
     * @param id
     * @return
     */
    CBRParamBO getParamById(Integer id);
    /**
     * 通过id更新报表参数
     * @param cbrParamBO
     * @return
     */
    Boolean updateParamById(CBRParamBO cbrParamBO);

    /**
     * 批量更新保存报表参数
     * @param cbrParamBOs
     * @return
     */
    Boolean saveOrupdateParam(List<CBRParamBO> cbrParamBOs);

    /**
     * 获取指定报表的所有参数
     * @param realreportId
     * @return
     */
    List<CBRParamBO> getAllParamsByRealreportId(String realreportId);

    /**
     * 根据报表参数名获取指定参数
     * @param realreportId
     * @param paramName
     * @return
     */
    CBRParamBO getParamByParamName(String realreportId, String paramName);
}
