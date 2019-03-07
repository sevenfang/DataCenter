package com.chezhibao.bigdata.search.auction.service.impl;

import com.chezhibao.bigdata.common.log.LoggerUtils;
import com.chezhibao.bigdata.search.auction.service.AucsAllRecommendSjbService;
import com.chezhibao.bigdata.search.auction.service.ExposureWithoutBiddingService;
import com.chezhibao.bigdata.search.common.SearchLogEnum;
import com.chezhibao.bigdata.search.storm.DetailCarService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/17.
 */
@Service
public class ExposureWithoutBiddingServiceImpl implements ExposureWithoutBiddingService {

    private static final Logger log = LoggerUtils.Logger(SearchLogEnum.AUCTION_LOG);

    private static final Integer RECOMMEND_EXPOSURECAR_COUNT=10;

    private AucsAllRecommendSjbService aucsAllRecommendSjbService;
    private DetailCarService detailCarService;

    public ExposureWithoutBiddingServiceImpl(AucsAllRecommendSjbService aucsAllRecommendSjbService,
                                             DetailCarService detailCarService) {
        this.aucsAllRecommendSjbService = aucsAllRecommendSjbService;
        this.detailCarService = detailCarService;
    }

    /**
     * 增加曝光车 这个是第一版
     * @param buyerId
     * @param size
     * @param recommendCars
     */
    @Deprecated
    @Override
    public void addExposureWithoutBidding(Integer buyerId,Integer size,List<Integer> recommendCars) {
        //增加曝光车
        List<Integer> exposureWithoutBidding = aucsAllRecommendSjbService.getExposureWithoutBidding(buyerId);

        //判断exposureWithoutBidding是否为空
        if (!ObjectUtils.isEmpty(exposureWithoutBidding)) {
            //将报光车辆插入到推荐车辆中
            for (int i = exposureWithoutBidding.size() - 1, j = 0; i > 0; i--) {
                Integer integer = exposureWithoutBidding.get(i);
                int index = recommendCars.indexOf(integer);
                if (index < 0) {
                    //在推荐结果中没有这辆车则跳过
                    continue;
                }
                int indexInsert = j++ * size;
                if (indexInsert > recommendCars.size()) {
                    //若果j*size大于recommendCars的长度 结束
                    break;
                }
                //删除推荐中的这辆曝光车
                recommendCars.remove(index);
                //如果此时出现真好整数个元素 即原来有40 现在减了1个变成39 后面在40的位置插入就报错了
                //所以此时哟昂判断 列表的长度 如果小于列表长度  则出现了上述情况 所以需要在判断一次
                if (indexInsert > recommendCars.size()) {
                    //将这两车插入到列表尾部
                    recommendCars.add(integer);
                } else {
                    //将这两车插入到每页的首部
                    recommendCars.add(indexInsert, integer);
                }
            }

        }
    }

    @Override
    public void addExposureWithoutBidding(Integer buyerId, List<Integer> recommendCars) {
        //获取相似车
        List<Integer> similarDetail = detailCarService.getSimilarDetail(buyerId);
        log.info("【数据查询服务】获取商户{}的相似推荐车辆：{}",buyerId,similarDetail);
        if(ObjectUtils.isEmpty(similarDetail)){
            return;
        }
        //去交集,判断被曝光车是否在竞拍
        similarDetail.retainAll(recommendCars);
        if(ObjectUtils.isEmpty(similarDetail)){
            return;
        }
        int size = similarDetail.size();
        //取前10辆车
        if(size>RECOMMEND_EXPOSURECAR_COUNT){
            similarDetail = similarDetail .subList(0,10);
        }
        recommendCars.removeAll(similarDetail);
        recommendCars.addAll(0,similarDetail);
    }
}
