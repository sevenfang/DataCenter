package com.chezhibao.bigdata.service;

import com.chezhibao.bigdata.bo.CBRBO;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/11.
 */
public interface CBRService {
    Boolean saveCBR(CBRBO cbr);
    Boolean changeStatusById(String realreportId,Integer status);
    Boolean changeLevelById(String realreportId,Integer level);
    Boolean saveOrUpdateCBR(CBRBO cbr);
    CBRBO getCbrById(String realreportId);
    Boolean updateCBR(CBRBO cbr);
    List<CBRBO> getAllCBR();
}
