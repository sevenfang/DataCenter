package com.chezhibao.bigdata.realreport;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/5/28.
 */
public interface RealreportService {

    /**
     * 获取运用指标数据
     * @param level  等级
     * @param id  用户ID
     * @param type 用户类型
     * @param time 时间
     * @param orgIds 组织ID
     * @return
     */
    Object getOperationalIndicators(Integer level,String id, int type, String time, List<Integer> orgIds);
}
