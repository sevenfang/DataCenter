package com.chezhibao.bigdata.service.auctioncar;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.common.log.LoggerUtils;
import com.chezhibao.bigdata.dao.NewWareHouseDao;
import com.chezhibao.bigdata.search.auction.AuctionCarBidService;
import com.chezhibao.bigdata.search.common.SearchLogEnum;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/27.
 */
@Service(interfaceClass =AuctionCarBidService.class )
@Component
public class AuctionCarBidServiceImpl implements AuctionCarBidService {
    private static final Logger log = LoggerUtils.Logger(SearchLogEnum.AUCTION_CAR);
    @Reference(check = false)
    private NewWareHouseDao newWareHouseDao;
    @Autowired
    private SqlTemplateService sqlTemplateService;
    @Override
    public Map<Integer, Integer> getExcellentCount(List<Integer> carIds) {
        if(log.isDebugEnabled()){
            log.debug("【查询服务】查询优良出价参数：{}",carIds);
        }
        if(ObjectUtils.isEmpty(carIds)){
            return new HashMap<>(0);
        }
        Map<Integer, Integer> result = new HashMap<>(carIds.size());
        Map<String, Object> params = ParamsBean.newInstance().put("auctionCarIds", carIds).build();
        String sql = sqlTemplateService.getSql("auctioncar.getExcellentCount", params);
        List<Map<String, Object>> select = newWareHouseDao.select(sql, params);
        if(log.isDebugEnabled()){
            log.debug("【查询服务】查询优良出价次数结果：{}",select);
        }
        for(Map<String, Object> m : select){
            String auctionCarIdStr = m.get("auctionCarId") + "";
            String countsStr = m.get("counts") + "";
            if(StringUtils.isEmpty(auctionCarIdStr)){
                continue;
            }
            Integer auctionCarId = Integer.parseInt(auctionCarIdStr);
            if(StringUtils.isEmpty(countsStr)) {
                result.put(auctionCarId,0);
            }else{
                result.put(auctionCarId,Integer.parseInt(countsStr));
            }
        }
        log.info("【查询服务】查询优良出价次数:查询数量：{}，结果数量：{}",carIds.size(),result.size());
        return result;
    }
}
