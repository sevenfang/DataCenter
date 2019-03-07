package com.chezhibao.bigdata.search.auction;

import com.chezhibao.bigdata.search.auction.service.AuctionCarRecommendService;
import com.chezhibao.bigdata.search.es.bo.SearchPage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/15.
 */
public abstract class ReCommendCarInfoQueryService implements AuctionCarRecommendService {

    /**
     * 获取默认车辆信息
     * @return
     */
    public abstract List<Integer> defaultSortRecommendCarInfo();

    @Override
    public List<Integer> getDefaultResult(List<Integer> defaultCar,SearchPage searchPage) {
        Integer page = searchPage.getPage();
        Integer size = searchPage.getSize();
        //排序
        defaultCar.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        Integer start = (page-1)*size;
        Integer end = page*size;
        //查询分页数据
        List<Integer> result = new ArrayList<>();
        for(int i = start;i<end;i++){
            if(i>=defaultCar.size()){
                return result;
            }
            result.add(defaultCar.get(i));
        }
        return result;
    }
}
