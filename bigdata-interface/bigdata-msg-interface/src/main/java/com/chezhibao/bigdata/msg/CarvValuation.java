package com.chezhibao.bigdata.msg;

/**
 *
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/10.
 */

public interface CarvValuation {
    /**
     * 发送检测报告给估价师
     * @param json
     * @return
     */
    Boolean sentTestReportToAppraiser(String json);
}
