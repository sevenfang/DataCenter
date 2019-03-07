package com.chezhibao.bigdata.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.bigdata.common.utils.DateUtils;
import com.chezhibao.bigdata.dao.CBRCommonDao;
import com.chezhibao.bigdata.dao.NewWareHouseDao;
import com.chezhibao.bigdata.dbms.hbase.HbaseService;
import com.chezhibao.bigdata.dbms.hbase.bo.HbaseParam;
import com.chezhibao.bigdata.search.es.bo.AuctionPromotionBO;
import com.chezhibao.bigdata.search.es.service.AuctionPromotionService;
import com.chezhibao.bigdata.template.ParamsBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * create 'AuctionPromotion','info'
 *
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/25.
 */
@Service(interfaceClass = AuctionPromotionService.class)
@Component
@Slf4j
public class AuctionPromotionServiceImpl implements AuctionPromotionService {

    private static final String TABLE_NAME = "AuctionPromotion";

    /**
     * 促销活动key 对接人：闻磊 赵旭江 Hbase 表：AUCTION_PROMOTION
     */
    public static final String AUCTION_PROMOTION_KEY_PREFIX="auction_promotion_";

    @Reference(check = false)
    private NewWareHouseDao newWareHouseDao;

    @Reference(check = false)
    private CBRCommonDao cbrCommonDao;

    @Reference(check = false)
    private HbaseService hbaseService;

    /**
     * 原先的老接口:新的替代接口
     * {@link AuctionPromotionServiceImpl#saveAuctionPromotionCarInfo(Integer, Date, Map)}
     * @param integer
     * @param date
     * @return
     */
    @Deprecated
    @Override
    public Boolean saveAuctionPromotionCarInfo(Integer integer, Date date) {
        log.debug("【搜索系统】促销活动：存放车辆活动信息：carId：{}",integer);
        Map<String, Object> promotionInfo = getPromotionInfo(integer, date);
        if(ObjectUtils.isEmpty(promotionInfo)){
            //不是活动车 清理hbase 防止上次将其按照活动车添加到hbase中
            return deletePromotionInfoIfExist(integer);
        }
        //存入Hbase
        HbaseParam<Map<String,Object>> param = new HbaseParam<>();
        param.setTableName(TABLE_NAME);
        param.setRowKey(AUCTION_PROMOTION_KEY_PREFIX + integer);
        param.setValue(promotionInfo);
        return hbaseService.save(param);
    }

    /**
     * 根据检测传来的车辆ID查询活动车信息
     * @param integer
     * @param date
     * @return 返回空 不是活动车 否则是
     */
    private Map<String,Object> getPromotionInfo(Integer integer, Date date){
        //查询车辆的信息
        Map<String, Object> params = ParamsBean.newInstance().put("carId", integer).build();
        String sql = "select carModel as carSeriesName , carType as carModelName ,region from `dw_new`.`basic_car` where id=#{params.carId}";
        List<Map<String, Object>> select = newWareHouseDao.select(sql, params);
        if(ObjectUtils.isEmpty(select)){
            return null;
        }
        Map<String, Object> map = select.get(0);
        String carSeriesName = map.get("carSeriesName")+"";
        String carModelName = map.get("carModelName")+"";
        String region = map.get("region")+"";
        String cityClassify = CityClassify.getCity(region);

        //查询车辆的活动信息
        params = ParamsBean.newInstance().put("carSeriesName", carSeriesName).put("carModelName",carModelName).put("cityClassify",cityClassify).build();
        sql = "select replaceMoney,logisticsMoney from `realreport`.`city_carseries_activity` where carSeriesName = #{params.carSeriesName} and carModelName = #{params.carModelName} and cityClassify=#{params.cityClassify}";
        select = cbrCommonDao.select(sql, params);
        if(ObjectUtils.isEmpty(select)){
            return null;
        }
        map = select.get(0);
        map.put("carId",integer);
        map.put("createtime",DateUtils.format(date,DateUtils.yyyy_MM_dd_HH_mm_ss));
        log.debug("【搜索系统】促销活动：查询到车辆活动信息：map：{}",map);
        return map;
    }

    /**
     *
     * @param integer
     * @return
     */
    private Boolean deletePromotionInfoIfExist(Integer integer){
        //不是活动车 清理hbase 防止上次将其按照活动车添加到hbase中
        HbaseParam hbaseParam = new HbaseParam();
        hbaseParam.setTableName(TABLE_NAME);
        hbaseParam.setRowKey(AUCTION_PROMOTION_KEY_PREFIX + integer);
        hbaseService.deleteByrowKey(hbaseParam);
        return false;
    }
    /**
     * 先由检测那边将车传过来，
     * 判断是否为活动车，是则直接入库
     * 不是则需要判断是否符合活动规则
     * 符合直接入库
     * 不符合判断之前是否是活动车,是则删除:
     * {@link AuctionPromotionServiceImpl#deletePromotionInfoIfExist(Integer)}
     * @param integer
     * @param date
     * @param map
     * @return
     */
    @Override
    public Boolean saveAuctionPromotionCarInfo(Integer integer, Date date, Map<String, Object> map) {
        log.debug("【搜索系统】促销活动：存放车辆活动信息：carId：{},参数：{}",integer,map);
        //判断传来的是否是活动车
        boolean isPromotion = Boolean.parseBoolean(map.get("isPromotion") + "");
        if(!isPromotion) {
            //不能确定是否是活动车,需要我们这边进行判断
            Map<String, Object> promotionInfo = getPromotionInfo(integer, date);
            if (ObjectUtils.isEmpty(promotionInfo)) {
                //不是活动车 清理hbase 防止上次将其按照活动车添加到hbase中
                return deletePromotionInfoIfExist(integer);
            }
        }
        //是活动活动车就直接存入hbase
        map.remove("isPromotion");
        map.put("carId",integer);
        map.put("createtime",DateUtils.format(date,DateUtils.yyyy_MM_dd_HH_mm_ss));
        HbaseParam<Map<String,Object>> param = new HbaseParam<>();
        param.setTableName(TABLE_NAME);
        param.setRowKey(AUCTION_PROMOTION_KEY_PREFIX + integer);
        param.setValue(map);
        return hbaseService.save(param);
    }

    @Override
    public List<AuctionPromotionBO> getAuctionPromotionCarInfos(List<Integer> list) {
        log.debug("【搜索系统】促销活动：获取车辆活动信息：list：{}",list);

        return new ArrayList<>();
    }


    static class CityClassify {
        /**
         * 7.城市分类，是指计算车系的转化强度、出价热度、竞拍次数时所对应的车辆归属地城市；
         * A.首批老城市，各自城市为一个城市分类：南京市,北京市,上海市,常州市,成都市,无锡市,杭州市,武汉市,深圳市,苏州市,西安市,郑州市,重庆市,长沙市,青岛市)；
         * B.17年开通的城市，为一个分类，“17年开通城市”：东莞市,临沂市,佛山市,保定市,南宁市,南昌市,南通市,厦门市,台州市,台州市,哈尔滨市,唐山市,嘉兴市,大连市,天津市,太原市,宁波市,广州市,徐州市,昆明市,沈阳市,沧州市,泉州市,济南市,温州市,石家庄市,福州市,贵阳市,邯郸市,金华市,长春市
         * C.18年开通的城市，为一个分类，“18年开通城市”：鞍山市,蚌埠市,滨州市,常德市,赤峰市,大庆市,大同市,抚顺市,赣州市,桂林市,衡阳市,葫芦岛市,淮安市,惠州市,吉林市,济宁市,锦州市,兰州市,廊坊市,连云港市,柳州市,洛阳市,绵阳市,南充市,南阳市,齐齐哈尔市,日照市,汕头市,商丘市,绍兴市,松原市,绥化市,泰安市,泰州市,铁岭市,威海市,潍坊市,芜湖市,西宁市,湘潭市,襄阳市,新乡市,宿迁市,烟台市,盐城市,宜昌市,银川市,营口市,岳阳市,运城市,枣庄市,张家口市,漳州市,中山市,珠海市,淄博市
         * D.其他城市为一个分类，“未开通城市”；
         */
        private static List<String> cityA;
        private static List<String> cityB;
        private static List<String> cityC;
        private static List<String> regionNames;

        static {
            String[] a = "1867,1889,1898,1931,1970,2002,2024,2053,2072,2073,2075,2076,2102,2123,2176".split(",");
            String[] names ="北京市,上海市,重庆市,青岛市,郑州市,武汉市,长沙市,深圳市,南京市,无锡市,常州市,苏州市,成都市,杭州市,西安市".split(",");
            String[] b = "2067,1942,2056,1904,2085,1919,2077,1911,2132,2038,1900,2126,1989,1892,1947,2124,2051,2074,2134,1988,1907,1914,1930,2125,1899,1910,2167,1902,2129,2015".split(",");
            String[] c = "2068,2193,2112,1982,2016,1983,1948,2043,1939,2005,2084,2029,2030,1908,1905,2061,1991,1976,1940,2021,1933,2086,2087,2055,1938,2083,1972,1936,1932,2079,2026,1945,1915,1935,1934,2054,2080,2128,2049,2107,2151,1995,2001,2152,2027,7349,2207,1925,1961,1954,2078,1999,2215,1994,1990,2039".split(",");
            cityA = Arrays.asList(a);
            cityB = Arrays.asList(b);
            cityC = Arrays.asList(c);
            regionNames = Arrays.asList(names);
        }

        public static String getCity(String region) {
            if (cityA.contains(region)) {
                int i = cityA.indexOf(region);
                return regionNames.get(i);
            }
            if (cityB.contains(region)) {
                return "17年开通城市";
            }
            if (cityC.contains(region)) {
                return "18年开通城市";
            }
            return "未开通城市";
        }
    }
}
