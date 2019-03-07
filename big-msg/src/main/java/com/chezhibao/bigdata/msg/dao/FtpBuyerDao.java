package com.chezhibao.bigdata.msg.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by jerry on 2018/10/24.
 */
public interface FtpBuyerDao {
    List<Map<String,Object>> selectBuyerByUser(String userId);

    List<Map<String,Object>> selectBuyerByUser(List<String> userIds);

    List<Map<String,Object>> selectBuyerByBuyerName(String buyerName);
}
