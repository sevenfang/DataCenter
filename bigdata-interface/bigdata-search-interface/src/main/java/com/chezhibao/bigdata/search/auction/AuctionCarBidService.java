package com.chezhibao.bigdata.search.auction;

import java.util.List;
import java.util.Map;

/**
 * 竞拍中车辆出价信息查询服务
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/27.
 */
public interface AuctionCarBidService {
    /**
     * 查询当前优良出价次数
     * 对接人：赵旭江
     * @param carIds
     * @return
     */
    Map<Integer,Integer> getExcellentCount(List<Integer> carIds);
}
