package com.chezhibao.bigdata.search.similarcar.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.common.log.LoggerUtils;
import com.chezhibao.bigdata.common.utils.StringUtils;
import com.chezhibao.bigdata.dbms.redis.IRedisService;
import com.chezhibao.bigdata.dbms.redis.ListRedisService;
import com.chezhibao.bigdata.search.common.SearchLogEnum;
import com.chezhibao.bigdata.search.similarcar.SimilarCarService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/7.
 */
@Service
public class SimilarCarServiceImpl implements SimilarCarService {

    private static final Logger log = LoggerUtils.Logger(SearchLogEnum.SIMILAR_CAR);
    @Reference(check = false)
    private ListRedisService listRedisService;

    @Reference(check = false)
    private IRedisService redisService;

    /**
     * redis中存放某车相似的车辆
     */
    public static final String SIMILAR_CAR   ="similar:kmeans:car";

    public static final String RECOMMEND_CAR_INDEX   ="similar:recommend:list:index";

    /**
     * 获取相似车辆
     * @param auctioncarId 竞拍车辆id
     */
    @Override
    public List<Integer> getsimilarCarByauctionCarid(String auctioncarId) {
        // 拼接key
        String similarCarKey = StringUtils.assembledRedisKey(SIMILAR_CAR,auctioncarId);
        log.debug("【推荐|相似车辆】 竞拍车辆key：{}", similarCarKey);
        String indexRecommendCarKey = StringUtils.assembledRedisKey(RECOMMEND_CAR_INDEX,auctioncarId);
        log.debug("【推荐|相似车辆】 索引下标key：{}", indexRecommendCarKey);
        // 定义返回的相似车辆列表
        List<Integer> carlist = new ArrayList<>();
        // redis的返回值
        String carStringList;
        String returnListIndex;
        try{
            //从redis中获取相似车辆
            carStringList= redisService.get(similarCarKey);
            log.debug("【推荐|相似车辆】 获取相似车辆：{}", carStringList);
            if(carStringList !=null){
                if (org.springframework.util.StringUtils.isEmpty(carStringList)) {
                    return null;
                }
                carlist = JSON.parseArray(carStringList, Integer.class);
                //从redis中获取返回相似车辆的数量下标
                returnListIndex = redisService.get(indexRecommendCarKey);

                //如果redis中存储索引下标key
                if(returnListIndex !=null) {
                    // key值为空
                    if (org.springframework.util.StringUtils.isEmpty(carStringList)) {
                        //返回5辆相似车辆
                        if(carlist.size()>5){
                            carlist = carlist.subList(0,5);
                        }
                        log.debug("【推荐|相似车辆】 索引为空推荐5辆车：{}", carlist);
                        // 存储下次应该推荐的下标
                        redisService.set(indexRecommendCarKey,"5");
                    }else{
                        log.debug("【推荐|相似车辆】 索引值为：{}", returnListIndex);
                        log.debug("【推荐|相似车辆】 数组长度为：{}", carlist.size());
                        log.debug("【推荐|相似车辆】 数组长度减去6为：{}", carlist.size()-6);
                        //如果已经被推荐车辆的下标在数组最后5个之间  subList 左闭右开
                        if(Integer.parseInt(returnListIndex)>carlist.size()-6 && Integer.parseInt(returnListIndex)<carlist.size()){
                            //推荐最后的几辆车
                            carlist = carlist.subList(Integer.parseInt(returnListIndex),carlist.size());
                            log.debug("【推荐|相似车辆】 推荐最后的几辆车：{}", carlist);
                            redisService.set(indexRecommendCarKey,"0");
                        }else if(Integer.parseInt(returnListIndex)>=carlist.size()){
                            // 推荐车辆下标大于数组长度
                            carlist = carlist.subList(0,5);
                            log.debug("【推荐|相似车辆】 推荐车辆下标大于数组长度 推荐：{}", carlist);
                            redisService.set(indexRecommendCarKey,"5");
                        }else{
                            // 推荐的车辆下标在正常范围之内
                            carlist = carlist.subList(Integer.parseInt(returnListIndex),Integer.parseInt(returnListIndex)+5);
                            int newIndex = Integer.parseInt(returnListIndex)+6;
                            log.debug("【推荐|相似车辆】 新索引值为{} ,推荐的车辆下标在正常范围之内 推荐：{}",newIndex, carlist);
                            redisService.set(indexRecommendCarKey,""+newIndex);
                            log.debug("【推荐|相似车辆】 redis中{}新索引值：{}", indexRecommendCarKey,redisService.get(indexRecommendCarKey));
                        }
                        log.debug("【推荐|相似车辆】 索引不为空推荐辆车：{}", carlist);
                    }
                }else{
                    //如果redis中没有存储该key，表示还没有推荐过车辆
                    //返回5辆相似车辆
                    if(carlist.size()>5){
                        carlist = carlist.subList(0,5);
                        // 存储下次应该推荐的下标
                        redisService.set(indexRecommendCarKey,"5");
                        log.debug("【推荐|相似车辆】 还没有推荐过车辆，推荐5辆车：{}", carlist);
                    }
                }
            }
        }catch (Exception e){
            log.error("query redis error",e);
        }
        log.debug("【推荐|相似车辆】 最终redis中{}新索引值：{}", indexRecommendCarKey,redisService.get(indexRecommendCarKey));
        return carlist;
    }
}
