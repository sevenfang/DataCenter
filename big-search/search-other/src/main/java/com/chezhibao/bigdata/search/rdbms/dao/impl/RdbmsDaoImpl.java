package com.chezhibao.bigdata.search.rdbms.dao.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.common.utils.DateUtils;
import com.chezhibao.bigdata.dao.AucsCommonDao;
import com.chezhibao.bigdata.dao.ChezhibaoCommonDao;
import com.chezhibao.bigdata.dao.NewWareHouseDao;
import com.chezhibao.bigdata.search.es.util.DateUtil;
import com.chezhibao.bigdata.search.rdbms.dao.RdbmsDao;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author WangCongJun
 * @date 2018/5/24
 * Created by WangCongJun on 2018/5/24.
 */
@Repository
@Slf4j
public class RdbmsDaoImpl implements RdbmsDao {



    private static final String BID_DS_AUCS = "aucs";
    private static final String BID_DS_CHEZHIBAO = "chezhibao";
    private static final String BID_DS_DWV2 = "new_warehouse";

    @Reference(check = false)
    private AucsCommonDao commonDao;
    @Reference(check = false)
    private ChezhibaoCommonDao chezhibaoCommonDao;
    @Reference(check = false)
    private NewWareHouseDao newWareHouseDao;

    @Autowired
    private SqlTemplateService sqlTemplateService;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



    @Override
    public List<Map<String, Object>> getGuessRecommdByBuyerCityBrandModel(int flag,String auctioncarid,double bidprice,int cityid,String count) {
        String now = DateUtils.getCurrDateTimeStr();
        List<Map<String,Object>> cars;
        if(flag==1){
            // 同品牌、同车系、同车型 同城市
            Map<String,Object> params=ParamsBean.newInstance().put("auctioncarid",auctioncarid).put("count",Integer.parseInt(count)).put("cityid",cityid).put("now",now).build();
            String sql=sqlTemplateService.getSql("car.car_aucs.getGuessRecommdByBuyerCityBrandModel",params);
            cars=commonDao.select(sql,params);
        }else if(flag==2){
            // 同品牌、同车系  同城市
            Map<String,Object> params=ParamsBean.newInstance().put("auctioncarid",auctioncarid).put("count",Integer.parseInt(count)).put("cityid",cityid).put("now",now).build();
            String sql=sqlTemplateService.getSql("car.car_aucs.getGuessRecommdByBuyerCityModel",params);
            cars=commonDao.select(sql,params);
        }else{
            // 同品牌 同城市
            Map<String,Object> params=ParamsBean.newInstance().put("auctioncarid",auctioncarid).put("count",Integer.parseInt(count)).put("cityid",cityid).put("now",now).build();
            String sql=sqlTemplateService.getSql("car.car_aucs.getGuessRecommdByBuyerCityBrand",params);
            cars=commonDao.select(sql,params);
        }

        return cars;
    }
    /**
     * 猜你喜欢 出价结束后，根据规则
     * 有与商户出价车辆相同品牌、车系车辆并且出价次数小于10,并且选择与车商同省或市的车辆
     * @param auctioncarid
     * @param count
     * @return
     */
    @Override
    public List<Map<String, Object>> getGuessRecommdByBrandModel(int flag,String auctioncarid,double bidprice,List<Integer> cityList, String count) {
        String now = DateUtils.getCurrDateTimeStr();
        List<Map<String,Object>> cars;
        if(flag==1){
            // 品牌、车系、车型 同省
            Map<String,Object> params=ParamsBean.newInstance().put("auctioncarid",auctioncarid).put("count",Integer.parseInt(count)).put("citylist",cityList).put("now",now).build();
            String sql=sqlTemplateService.getSql("car.car_aucs.getGuessRecommdByBrandModel",params);
            cars=commonDao.select(sql,params);
        }else if(flag==2){
            // 品牌、车系 同省
            Map<String,Object> params=ParamsBean.newInstance().put("auctioncarid",auctioncarid).put("count",Integer.parseInt(count)).put("citylist",cityList).put("now",now).build();
            String sql=sqlTemplateService.getSql("car.car_aucs.getGuessRecommdByModel",params);
            cars=commonDao.select(sql,params);
        }else{
            // 品牌 同省
            Map<String,Object> params=ParamsBean.newInstance().put("auctioncarid",auctioncarid).put("count",Integer.parseInt(count)).put("citylist",cityList).put("now",now).build();
            String sql=sqlTemplateService.getSql("car.car_aucs.getGuessRecommdByBrand",params);
            cars=commonDao.select(sql,params);
        }
        return cars;
    }

    /**
     * 猜你喜欢 出价结束后，根据规则推荐车辆
     * 有与商户出价车辆相同品牌、车系车辆并且出价次数小于10
     * @param auctioncarid
     * @param count
     * @return
     */
    @Override
    public List<Map<String, Object>> getGuessRecommdByBrandModelWithoutCity(int flag,String auctioncarid,double bidprice, String count) {
        String now = DateUtils.getCurrDateTimeStr();
        List<Map<String,Object>> cars;
        if(flag==1){
            // 品牌、车型、车系 全国
            Map<String,Object> params=ParamsBean.newInstance().put("auctioncarid",auctioncarid).put("count",Integer.parseInt(count)).put("now",now).build();
            String sql=sqlTemplateService.getSql("car.car_aucs.getGuessRecommdByBrandModelWithoutCity",params);
            cars=commonDao.select(sql,params);
        }else if(flag==2){
            // 品牌 车系 全国
            Map<String,Object> params=ParamsBean.newInstance().put("auctioncarid",auctioncarid).put("count",Integer.parseInt(count)).put("now",now).build();
            String sql=sqlTemplateService.getSql("car.car_aucs.getGuessRecommdByModelWithoutCity",params);
            cars=commonDao.select(sql,params);
        }else{
            // 品牌 全国
            Map<String,Object> params=ParamsBean.newInstance().put("auctioncarid",auctioncarid).put("count",Integer.parseInt(count)).put("now",now).build();
            String sql=sqlTemplateService.getSql("car.car_aucs.getGuessRecommdByBrandWithoutCity",params);
            cars=commonDao.select(sql,params);
        }

        return cars;
    }

    @Override
    public List<Map<String, Object>> getGuessRecommdByBuyerCityBidprice(String auctioncarid,double bidprice,int cityid,String count) {
        String now = DateUtils.getCurrDateTimeStr();
        List<Map<String,Object>> cars;
        Map<String,Object> params=ParamsBean.newInstance().put("auctioncarid",auctioncarid).put("count",Integer.parseInt(count)).put("bidprice",bidprice).put("cityid",cityid).put("now",now).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getGuessRecommdByBuyerCityBidprice",params);
        cars=commonDao.select(sql,params);
        return cars;
    }


    /**
     * 猜你喜欢 出价结束后，根据规则推荐车辆
     * 有与商户出价车辆相同品牌 全国车辆
     * @param auctioncarid
     * @param count
     * @return
     */
    @Override
    public List<Map<String, Object>> getRecommdByBrandWithoutCity(String auctioncarid,double bidprice, String count) {
        String now = DateUtils.getCurrDateTimeStr();
        List<Map<String,Object>> cars;
        // 品牌 全国
        Map<String,Object> params=ParamsBean.newInstance().put("auctioncarid",auctioncarid).put("count",Integer.parseInt(count)).put("now",now).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getRecommdByBrandWithoutCity",params);
        cars=commonDao.select(sql,params);
        if(cars.isEmpty()){
            return new ArrayList<>();
        }
        return cars;
    }

    /**
     * 获取商户出价过的车型和车系
     * @param auctionCarId
     * @return
     */
    @Override
    public List<Map<String, Object>> getBidddingCarModelType(String auctionCarId){
        List<Map<String,Object>> cars;
        Map<String,Object> params=ParamsBean.newInstance().put("auctioncarid",auctionCarId).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getBiddingCarModelType",params);
        cars=commonDao.select(sql,params);
        return cars;
    }

    /**
     * 猜你喜欢 出价结束后，根据规则
     * 无与商户出价车辆相同品牌、车系车辆并且出价次数小于10，取相同价位的车辆,并且选择与车商同省或市的车辆
     * @param auctioncarid
     * @param count
     * @return
     */
    @Override
    public List<Map<String, Object>> getGuessRecommdByBidprice(String auctioncarid,double bidprice,List<Integer> cityList, String count) {
        String now = DateUtils.getCurrDateTimeStr();
        List<Map<String,Object>> cars;
        Map<String,Object> params=ParamsBean.newInstance().put("auctioncarid",auctioncarid).put("count",Integer.parseInt(count)).put("bidprice",bidprice).put("citylist",cityList).put("now",now).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getGuessRecommdByBidprice",params);
        cars=commonDao.select(sql,params);
        return cars;
    }

    /**
     * 猜你喜欢 出价结束后，根据规则推荐车辆
     * 有与商户出价车辆相同品牌、车系车辆并且出价次数小于10，取相同价位的车辆
     * @param auctioncarid
     * @param count
     * @return
     */
    @Override
    public List<Map<String, Object>> getGuessRecommdByBidpriceWithoutCity(String auctioncarid,double bidprice, String count) {
        String now = DateUtils.getCurrDateTimeStr();
        List<Map<String,Object>> cars;
        Map<String,Object> params=ParamsBean.newInstance().put("auctioncarid",auctioncarid).put("count",Integer.parseInt(count)).put("bidprice",bidprice).put("now",now).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getGuessRecommdByBidpriceWithoutCity",params);
        cars=commonDao.select(sql,params);
        return cars;
    }

    /**
     * 猜你喜欢 出价结束后，根据规则推荐车辆
     * 有与商户出价车辆相同品牌、车系车辆并且出价次数小于10，取相同价位的车辆
     * @param auctioncarid
     * @param count
     * @return
     */
    @Override
    public List<Map<String, Object>> getGuessRecommdByBidpriceWithoutCityWithBrandMdelType(String auctioncarid,double bidprice, String count) {
        String now = DateUtils.getCurrDateTimeStr();
        List<Map<String,Object>> cars;
        Map<String,Object> params=ParamsBean.newInstance().put("auctioncarid",auctioncarid).put("count",Integer.parseInt(count)).put("bidprice",bidprice).put("now",now).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getGuessRecommdByBidpriceWithoutCityWithBrandModelType",params);
        cars=commonDao.select(sql,params);
        return cars;
    }

    /**
     * 根据竞拍车辆id,获取该车的估价均值
     * @param auctioncarid
     * @return
     */
    @Override
    public List<Map<String, Object>> getAuctioncarElvaPrice(String auctioncarid) {
        String now = DateUtils.getCurrDateTimeStr();
        List<Map<String,Object>> cars;
        Map<String,Object> params=ParamsBean.newInstance().put("auctioncarid",auctioncarid).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getAuctioncarElvaPrice",params);
        cars=commonDao.select(sql,params);
        return cars;
    }

    /**
     * 获取商户已经出价过的竞拍车辆编号
     * @param buyerId
     * @return
     */
    @Override
    public List<Map<String, Object>> getBuyerBiddingCarInfo(int buyerId){
        // 当天00:00:00
        String from = DateUtils.getCurrDateStr() + " 14:30:00";
        // 当前时间
        String to = DateUtils.getCurrDateTimeStr();
        List<Map<String,Object>> cars;
        Map<String,Object> params=ParamsBean.newInstance().put("buyerId",buyerId).put("from",from).put("to",to).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getBuyerBiddingCarInfo",params);
        cars=commonDao.select(sql,params);
        return cars;
    }

    /**
     * 车商订阅的车系id
     * @param buyerId
     * @return
     */
    @Override
    public List<Map<String, Object>> getBuyerSubscriptCarSeries(int buyerId) {
        List<Map<String,Object>> cars;
        Map<String,Object> params=ParamsBean.newInstance().put("buyer_id",buyerId).build();
        String sql=sqlTemplateService.getSql("car.czb_buyer.getBuyerSubscriptCarSeries",params);
        cars=chezhibaoCommonDao.select(sql,params);
        return cars;
    }

    /**
     * 车商订阅的品牌id
     * @param buyerId
     * @return
     */
    @Override
    public List<Map<String, Object>> getBuyerSubscriptCarBrand(int buyerId) {
        List<Map<String,Object>> cars;
        Map<String,Object> params=ParamsBean.newInstance().put("buyer_id",buyerId).build();
        String sql=sqlTemplateService.getSql("car.czb_buyer.getBuyerSubscriptCarBrand",params);
        cars=chezhibaoCommonDao.select(sql,params);
        return cars;
    }

    /**
     * 获取当天竞拍车辆列表，取默认
     * @param limit
     * @return
     */
    @Override
    public List<Map<String, Object>> getDefaultCarInfo(int limit){
        List<Map<String,Object>> cars;
        // 当前时间
        String currentDate = DateUtils.getCurrDateTimeStr();
        Map<String,Object> params=ParamsBean.newInstance().put("currentDate",currentDate).put("limit",limit).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getDefaultCarInfo",params);
        cars=commonDao.select(sql,params);
        return cars;
    }

    /**
     * 获取车主降价车辆
     * @return
     */
    @Override
    public List<Map<String, Object>> getReductionCarInfo(){
        List<Map<String,Object>> cars;
        // 当前时间
        String currentDate = DateUtils.getCurrDateTimeStr();
        Map<String,Object> params=ParamsBean.newInstance().build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getReductionCarInfo",params);
        cars=commonDao.select(sql,params);
        return cars;
    }

    /**
     * 获取当天竞拍车辆列表，取默认,带检测单id
     * @param limit
     * @return
     */
    @Override
    public List<Map<String, Object>> getDefaultCarInfoWithDetect(int limit){
        List<Map<String,Object>> cars;
        // 当前时间
        String currentDate = DateUtils.getCurrDateTimeStr();
        Map<String,Object> params=ParamsBean.newInstance().put("currentDate",currentDate).put("limit",limit).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getDefaultCarInfoWithDetect",params);
        cars=commonDao.select(sql,params);
        return cars;
    }

    /**
     * 获取当天竞拍车辆的车辆id
     * @param limit
     * @return
     */
    @Override
    public List<Map<String, Object>> getCaridofAuctioncar(int limit){
        List<Map<String,Object>> cars;
        // 当前时间
        String currentDate = DateUtils.getCurrDateTimeStr();
        Map<String,Object> params=ParamsBean.newInstance().put("currentDate",currentDate).put("limit",limit).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getCaridofAuctioncar",params);
        cars=commonDao.select(sql,params);
        return cars;
    }



    /**
     * 获取当天竞拍车辆列表，取默认
     * @param limit
     * @return
     */
    @Override
    public List<Map<String, Object>> getBidFinishDefaultCarInfo(int limit,double maxPrice,double minPrice){
        List<Map<String,Object>> cars;
        // 当前时间
        String currentDate = DateUtils.getCurrDateTimeStr();
        Map<String,Object> params=ParamsBean.newInstance().put("currentDate",currentDate).put("maxPrice",maxPrice).put("minPrice",minPrice).put("limit",limit).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getBidFinishDefaultCarInfo",params);
        cars=commonDao.select(sql,params);
        return cars;
    }

    /**
     * 猜你喜欢 出价结束后，根据规则推荐车辆 列表
     * 有与商户出价车辆相同品牌、车系车辆并且出价次数小于10
     * @param auctioncarid
     * @param count
     * @return
     */
    @Override
    public List<Map<String, Object>> getGuessRecommdByBrandModelList(String auctioncarid,double bidprice , String count) {

        String now = DateUtils.getCurrDateTimeStr();
        List<Map<String,Object>> cars;
        Map<String,Object> params=ParamsBean.newInstance().put("auctioncarid",auctioncarid).put("count",100).put("now",now).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getGuessRecommdByBrandModelList",params);
        log.info("【rdbmsdaoimpl】sql:{}", sql);
        cars=commonDao.select(sql,params);
        return cars;
    }

    /**
     * 猜你喜欢 出价结束后，根据规则推荐车辆 列表
     * 无与商户出价车辆相同品牌、车系车辆并且出价次数小于10，取相同价位的车辆
     * @param auctioncarid
     * @param count
     * @return
     */
    @Override
    public List<Map<String, Object>> getGuessRecommdByBidpriceList(String auctioncarid,double bidprice,List<Integer> hasBidCars, String count) {
        String now = DateUtils.getCurrDateTimeStr();
        List<Map<String,Object>> cars;
        Map<String,Object> params=ParamsBean.newInstance().put("auctioncarid",auctioncarid).put("count",Integer.parseInt(count)).put("hasBidCars",hasBidCars).put("bidprice",bidprice).put("now",now).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getGuessRecommdByBidpriceList",params);
        cars=commonDao.select(sql,params);
        return cars;
    }

    /**
     * 同车位推荐 根据商户id 查询商户所在市
     * @param buyerId
     */
    @Override
    public List<Map<String, Object>> getBuyerCity(int buyerId) {
        List<Map<String,Object>> cars;
        Map<String,Object> params=ParamsBean.newInstance().put("buyer_id",buyerId).build();
        String sql=sqlTemplateService.getSql("car.czb_buyer.getBuyerCity",params);
        cars=chezhibaoCommonDao.select(sql,params);
        return cars;
    }

    /**
     * 同车位推荐 根据商户id 查询商户所在省份的所有市
     * @param buyerId
     */
    @Override
    public List<Map<String, Object>> getBuyerCityList(int buyerId) {
        List<Map<String,Object>> cars;
        Map<String,Object> params=ParamsBean.newInstance().put("buyer_id",buyerId).build();
        String sql=sqlTemplateService.getSql("car.czb_buyer.getBuyerCityList",params);
        cars=chezhibaoCommonDao.select(sql,params);
        return cars;
    }

    /**
     * 个性化推荐 获取车商出价记录数
     * @param buyerId
     */
    @Override
    public List<Map<String, Object>> getBuyerBidNum(int buyerId) {
        List<Map<String,Object>> cars;
        Date now = new Date();
        Date newDate = DateUtil.stepMonth(now, -3);
        String last_bid_three_month = DATE_FORMAT.format(newDate);
        Map<String,Object> params=ParamsBean.newInstance().put("buyer_id",buyerId).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getBuyerBidNum",params);
        cars=commonDao.select(sql,params);
        return cars;
    }

    /**
     * 个性化推荐 获取车商近中标记录数
     * @param buyerId
     */
    @Override
    public List<Map<String, Object>> getBuyerWinNum(int buyerId) {
        List<Map<String,Object>> cars;
        Date now = new Date();
        Date newDate = DateUtil.stepMonth(now, -3);
        String last_win_three_month = DATE_FORMAT.format(newDate);
        Map<String,Object> params=ParamsBean.newInstance().put("buyer_id",buyerId).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getBuyerWinNum",params);
        cars=commonDao.select(sql,params);
        return cars;
    }

    /**
     * 从当前竞拍车辆中获取车况较好的车辆，且是商户所在地区/附近地区的车辆
     * @param regionList
     * @return
     */
    @Override
    public List<Map<String, Object>> getbetterCarCondition(List<String> regionList) {
        String now = DateUtils.getCurrDateTimeStr();
        List<Map<String,Object>> cars;
        Map<String,Object> params=ParamsBean.newInstance().put("region_list",regionList).put("now",now).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getbetterCarCondition",params);
        cars=commonDao.select(sql,params);
        return cars;
    }

    /**
     * 从当前竞拍车辆中获取车况较好的车辆，且是商户所在地区/附近地区的车辆
     * @param regionList
     * @return
     */
    @Override
    public List<Map<String, Object>> getbetterCarConditionWithDetectId(List<String> regionList) {
        String now = DateUtils.getCurrDateTimeStr();
        List<Map<String,Object>> cars;
        Map<String,Object> params=ParamsBean.newInstance().put("region_list",regionList).put("now",now).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getbetterCarConditionWithDetectId",params);
        cars=commonDao.select(sql,params);
        return cars;
    }

    /**
     * 获取当前竞拍车辆中出价次数小于5次的车辆
     * @param regionList
     * @return
     */
    @Override
    public List<Map<String, Object>> getbidNumLessFive(List<String> regionList) {
        String now = DateUtils.getCurrDateTimeStr();
        List<Map<String,Object>> cars;
        Map<String,Object> params=ParamsBean.newInstance().put("region_list",regionList).put("now",now).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getbidNumLessFive",params);
        cars=commonDao.select(sql,params);
        return cars;
    }

    /**
     * 获取当前竞拍车辆中出价次数小于5次的车辆,包括检测单id
     * @param regionList
     * @return
     */
    @Override
    public List<Map<String, Object>> getbidNumLessFiveWithDetectId(List<String> regionList) {
        String now = DateUtils.getCurrDateTimeStr();
        List<Map<String,Object>> cars;
        Map<String,Object> params=ParamsBean.newInstance().put("region_list",regionList).put("now",now).build();
        String sql=sqlTemplateService.getSql("car.car_aucs.getbidNumLessFiveWithDetectId",params);
        cars=commonDao.select(sql,params);
        return cars;
    }


    /**
     * 获取渠道对应的商户
     * @param ChannelId
     */
    @Override
    public List<Map<String, Object>> getBuyerByChannelId(int ChannelId){
        List<Map<String,Object>> buyer;
        Map<String,Object> params=ParamsBean.newInstance().put("fzr",ChannelId).build();
        String sql=sqlTemplateService.getSql("car.czb_buyer.getBuyerByChannelId",params);
        buyer=chezhibaoCommonDao.select(sql,params);
        return buyer;
    }
    /**
     * 获取渠道对应的商户
     * @param staffId
     */
    @Override
    public List<Map<String, Object>> getexploreCarBystaffId(int staffId){
        List<Map<String,Object>> car;
        Map<String,Object> params=ParamsBean.newInstance().put("staffId",staffId).build();
        String sql=sqlTemplateService.getSql("car.czb_dwv2.getexploreCarBystaffId",params);
        car=newWareHouseDao.select(sql,params);
        return car;
    }

}
