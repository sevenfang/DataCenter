package com.chezhibao.api.intf.auction;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/25.
 */
public interface AuctionClient {
    Map<Integer, Double> getYikoujia(List<Integer> var1);
}
