package com.chezhibao.bigdata.realreport.consumer;

import com.chezhibao.bigdata.realreport.consumer.bo.UserDetail;

import java.util.List;

/**
 * 获取原先服务实时报表服务接口
 * 根据用户Id查询相关信息
 * @author WangCongJun
 * Created by WangCongJun on 2018/5/30.
 */
public interface RealreportConsumer {

    /**
     * 根据用户Id查询组织信息
     * @param userId
     * @return 用户信息
     */
    UserDetail getOrgIdsByuserId(Integer userId);

    /**
     * 根据用户Id查询用户级别
     * @param userId
     * @return
     */
    Integer getUserLevelByUserId(Integer userId);

}
