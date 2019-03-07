package com.chezhibao.bigdata.msg.dao.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.dao.NewWareHouseDao;
import com.chezhibao.bigdata.msg.dao.FtpBuyerDao;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jerry on 2018/10/24.
 */
@Repository
public class FtpBuyerDaoImpl implements FtpBuyerDao {
    @Reference(check = false,timeout = 3000)
    private NewWareHouseDao newWareHouseDao;
    @Autowired
    private SqlTemplateService sqlTemplateService;

    @Override
    public List<Map<String, Object>> selectBuyerByUser(String userId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", userId);
        String sql = sqlTemplateService.getSql("ftp.ftp_buyer.selectByUserId", params);
        return newWareHouseDao.select(sql, params);
    }


    @Override
    public List<Map<String, Object>> selectBuyerByUser(List<String> userIds) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("ids", userIds);
        if (userIds.isEmpty()) {
            return new ArrayList<>();
        }
        String sql = sqlTemplateService.getSql("ftp.ftp_buyer.selectByUserIds", params);
        return newWareHouseDao.select(sql, params);
    }

    @Override
    public List<Map<String, Object>> selectBuyerByBuyerName(String buyerName) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", buyerName);
        String sql = sqlTemplateService.getSql("ftp.ftp_buyer.selectByBuyerName", params);
        return newWareHouseDao.select(sql, params);
    }
}
