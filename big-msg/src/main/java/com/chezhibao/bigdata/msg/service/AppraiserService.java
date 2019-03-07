package com.chezhibao.bigdata.msg.service;

import com.chezhibao.bigdata.msg.bo.CarValuationBO;
import com.chezhibao.bigdata.msg.pojo.Appraiser;
import com.chezhibao.bigdata.msg.pojo.AppraiserPrice;
import com.chezhibao.bigdata.msg.vo.AppraiserVO;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/10.
 */
public interface AppraiserService {
    /**
     * 根据city选择估价师
     * @param city
     * @return
     */
    Appraiser selectAppraiser(String city);

    /**
     * 存储估价师的出价
     * @param appraiserVO
     * @return
     */
    Boolean saveAppraiserPrice(AppraiserVO appraiserVO);

    /**
     * 将需要估价师出价的记录数据库
     * @param valuation
     * @return
     */
    Boolean saveAppraiserPrice(CarValuationBO valuation);

    /**
     * 添加估价到数据库
     * @param appraiserVO
     * @return
     */
    Boolean bidPrice(AppraiserVO appraiserVO);
    /**
     * 存储估价师的出价
     * @param appraiserVO
     * @return
     */
    AppraiserPrice getAppraiserPrice(AppraiserVO appraiserVO);

    /**
     * 检查数据库是否有估价师的出价
     * @param detectId
     * @return
     */
    AppraiserPrice getAppraiserPriceByDetectId(String detectId);

    /**
     * 查询评估师出价的记录
     * @param appraiserPrice
     * @return
     */
    List<AppraiserPrice> getAppraiserPrice(AppraiserPrice appraiserPrice);

    /**
     * 检查估价是否发送给了ocse
     * true 是
     * false 否
     * @param appraiserVO
     * @return
     */
    Boolean checkIsSend(AppraiserVO appraiserVO);

    /**
     * 更新估价单状态为已发送
     * @param appraiserVO
     * @return
     */
    Boolean updateStatusToSent(AppraiserVO appraiserVO);

    String getAppraiseUrlByCarId(Integer carId);
}
