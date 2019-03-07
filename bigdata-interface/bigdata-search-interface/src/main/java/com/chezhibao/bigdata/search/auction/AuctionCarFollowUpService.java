package com.chezhibao.bigdata.search.auction;

import com.chezhibao.bigdata.search.auction.bo.CarFollowUpInfo;

import java.util.List;
import java.util.Map;

/**
 * 车辆跟进数据服务
 * 对接人：方超
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/27.
 */
public interface AuctionCarFollowUpService {
    /**
     * 查询车辆最近一次竞拍结束到待约成交之间接通次数与时长
     * @param carIds
     * @return
     */
    Map<Integer,CarFollowUpInfo> getLatestCarFollowUpInfos(List<Integer> carIds);
}
