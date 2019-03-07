package com.chezhibao.bigdata.service.auctioncar;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.common.log.LoggerUtils;
import com.chezhibao.bigdata.dao.NewWareHouseDao;
import com.chezhibao.bigdata.search.auction.AuctionCarFollowUpService;
import com.chezhibao.bigdata.search.auction.bo.CarFollowUpInfo;
import com.chezhibao.bigdata.search.common.SearchLogEnum;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/27.
 */
@Component
@Service(interfaceClass =AuctionCarFollowUpService.class )
public class AuctionCarFollowUpServiceImpl implements AuctionCarFollowUpService {
    private static final Logger log = LoggerUtils.Logger(SearchLogEnum.AUCTION_CAR);
    @Reference(check = false)
    private NewWareHouseDao newWareHouseDao;
    @Autowired
    private SqlTemplateService sqlTemplateService;
    @Override
    public Map<Integer, CarFollowUpInfo> getLatestCarFollowUpInfos(List<Integer> carIds) {
        if(log.isDebugEnabled()){
            log.debug("【查询服务】查询车辆最近一次竞拍结束到待约成交之间接通次数与时长：{}",carIds);
        }
        if(ObjectUtils.isEmpty(carIds)){
            return new HashMap<>(0);
        }
        Map<Integer, CarFollowUpInfo> result = new HashMap<>(carIds.size());
        Map<String, Object> params = ParamsBean.newInstance().put("carIds", carIds).build();
        String sql = sqlTemplateService.getSql("auctioncar.getLatestCarFollowUpInfos", params);
        List<Map<String, Object>> select = newWareHouseDao.select(sql, params);
        if(log.isDebugEnabled()){
            log.debug("【查询服务】查询车辆最近一次竞拍结束到待约成交之间接通次数与时长结果：{}",select);
        }
        CarFollowUpInfo carFollowUpInfo ;
        for(Map<String, Object> m : select){
            Object caridObj = m.get("carid");
            if(ObjectUtils.isEmpty(caridObj)){
                continue;
            }
            carFollowUpInfo = new CarFollowUpInfo();
            Integer carId = Integer.parseInt(caridObj+"");
            result.put(carId,carFollowUpInfo);
            Object calltimesObj = m.get("calltimes");
            Integer calltimes = ObjectUtils.isEmpty(calltimesObj)?0:Integer.parseInt(calltimesObj+"");
            carFollowUpInfo.setTrakingNum(calltimes);
            Object calldurationObj = m.get("callduration");
            Double callduration = ObjectUtils.isEmpty(calldurationObj)?0:Double.parseDouble(calldurationObj+"");
            carFollowUpInfo.setTrakingTime(callduration);
        }
        log.info("【查询服务】查询车辆最近一次竞拍结束到待约成交之间接通次数与时长:查询数量：{}，结果数量：{}",carIds.size(),result.size());
        return result;
    }
}
