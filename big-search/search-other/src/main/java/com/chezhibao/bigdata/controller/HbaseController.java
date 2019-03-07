package com.chezhibao.bigdata.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.dbms.hbase.HbaseService;
import com.chezhibao.bigdata.dbms.hbase.bo.HbaseParam;
import com.chezhibao.bigdata.search.recommend.bo.BuyerInfo;
import com.chezhibao.bigdata.search.recommend.bo.RecommendBidCarIfo;
import com.chezhibao.bigdata.service.RecommendBuyerInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/28.
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class HbaseController {

    @Reference(check = false)
    private HbaseService hbaseService;

    @RequestMapping("log")
//    @SentinelResource(value = "test",blockHandler = "testHandlerr",fallback = "testFallBacker")
    public BigdataResult testLog(){
        int[] arr = {21};
        try {

            int r = arr[2];
        }catch (Exception e){
            log.error("{}中没有{}出错了：",arr,2,e);
        }
        return BigdataResult.ok(0);
    }


    @RequestMapping("hbase/save")
    public BigdataResult saveData(Integer id,Boolean flag){
        HbaseParam<List<RecommendBidCarIfo>> param = new HbaseParam<>();
        param.setTableName(RecommendBuyerInfoServiceImpl.TABLE_NAME);
        param.setCf("channel");
        param.setRowKey(RecommendBuyerInfoServiceImpl.RECOMMEND_BY_CHANNELID_KEY_PREFIX+id);
        List<RecommendBidCarIfo> recommendBidCarIfos = getValue(flag);

        param.setValue(recommendBidCarIfos);
        hbaseService.save(param);
        return BigdataResult.ok();
    }

    public List<RecommendBidCarIfo> getValue( Boolean flag){
        List<RecommendBidCarIfo> recommendBidCarIfos = new ArrayList<>();
        List<BuyerInfo> buyerInfos = new ArrayList<>();
        if(flag){
            BuyerInfo buyerInfo = getBuyerInfo("张献凯,131717,18710268739,献凯车行");
            buyerInfos.add(buyerInfo);
            BuyerInfo buyerInfo1 = getBuyerInfo("陈咏,301,18851024375,1111汽车");
            buyerInfos.add(buyerInfo1);
            BuyerInfo buyerInfo2 = getBuyerInfo("丛素祥,307,18068831026,7777汽车");
            buyerInfos.add(buyerInfo2);
            BuyerInfo buyerInfo3 = getBuyerInfo("李辉,303,18362966151,3333汽车");
            buyerInfos.add(buyerInfo3);
            BuyerInfo buyerInfo4 = getBuyerInfo("许仁祥,238327,15062379916,许仁祥");
            buyerInfos.add(buyerInfo4);
            BuyerInfo buyerInfo5 = getBuyerInfo("李晓彤,304,13852172967,0元4444汽车");
            buyerInfos.add(buyerInfo5);
            BuyerInfo buyerInfo6 = getBuyerInfo("刘恩民,238293,15210455640,南京市霸气车行");
            buyerInfos.add(buyerInfo6);
        }else{
            BuyerInfo buyerInfo5 = getBuyerInfo("张狼 50155 15295751033 小狼车行");
            buyerInfos.add(buyerInfo5);
        }




        RecommendBidCarIfo recommendBidCarIfo = getRecommendBidCarIfo("298211,1,别克,1674875,凯越,2015款 1.5L 自动尊享型,395596,2018-07-22,35100,北京市");
        recommendBidCarIfo.setBuyerList(buyerInfos);
        recommendBidCarIfos.add(recommendBidCarIfo);
        RecommendBidCarIfo recommendBidCarIfo1 = getRecommendBidCarIfo("298214,1,东风小康,1674879,东风小康K07II,2007款 1.0L 基本型BG10-01,395600,2018-07-22,85233,上海市");
        recommendBidCarIfo1.setBuyerList(buyerInfos);
        recommendBidCarIfos.add(recommendBidCarIfo1);
        RecommendBidCarIfo recommendBidCarIfo2 = getRecommendBidCarIfo("298213,1,现代,1674877,全新途胜,2013款 2.0L 手动两驱舒适型,395598,2018-07-22,20020,上海市");
        recommendBidCarIfo2.setBuyerList(buyerInfos);
        recommendBidCarIfos.add(recommendBidCarIfo2);
        RecommendBidCarIfo recommendBidCarIfo3 = getRecommendBidCarIfo("298212,1,斯柯达,1674876,明锐,2013款 2.0L 自动逸杰版,395597,2018-07-22,50000,上海市");
        recommendBidCarIfo3.setBuyerList(buyerInfos);
        recommendBidCarIfos.add(recommendBidCarIfo3);
        return recommendBidCarIfos;
    }

    public RecommendBidCarIfo getRecommendBidCarIfo(String data){
        String[] split = data.split(",");
        RecommendBidCarIfo recommendBidCarIfo = new RecommendBidCarIfo();
        recommendBidCarIfo.setAuctionCarId(split[0]);
        recommendBidCarIfo.setAuctionType(Integer.parseInt(split[1]));
        recommendBidCarIfo.setCarBand(split[2]);
        recommendBidCarIfo.setCarId(split[3]);
        recommendBidCarIfo.setCarModel(split[4]);
        recommendBidCarIfo.setCarType(split[5]);
        recommendBidCarIfo.setDetectionId(split[6]);
        recommendBidCarIfo.setDj(split[7]);
        recommendBidCarIfo.setMileage(split[8]);
        recommendBidCarIfo.setRegion(split[9]);
        return recommendBidCarIfo;
    }

    public BuyerInfo getBuyerInfo(String data){
        String[] split = data.split(",");
        BuyerInfo buyerInfo = new BuyerInfo();
        buyerInfo.setBuyerId(Integer.parseInt(split[1]));
        buyerInfo.setBuyerName(split[3]);
        buyerInfo.setBuyerContacts(split[0]);
        buyerInfo.setBuyerMobile(split[2]);
        return buyerInfo;
    }

}
