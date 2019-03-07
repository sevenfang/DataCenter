package com.chezhibao.bigdata.search.feed.collect;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.search.feed.service.LogService;
import com.chezhibao.bigdata.search.recommend.RecomendFeedService;
import com.chezhibao.bigdata.search.recommend.bo.FeedReqInfo;
import org.springframework.stereotype.Component;
import static com.chezhibao.bigdata.search.common.SearchLogUtils.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/10.
 */
@Component
@Service(interfaceClass = RecomendFeedService.class)
public class RecommendFeedServiceImpl implements RecomendFeedService {

    private LogService logService;

    public RecommendFeedServiceImpl(LogService logService) {
        this.logService = logService;
    }

    @Override
    public void sendReqInfo(FeedReqInfo feedReqInfo) {
        FEED_LOG.debug("【查询系统】获取用户列表点击信息：{}",feedReqInfo);
        String s = JSON.toJSONString(feedReqInfo);
        Long aLong = logService.sendLog(s);
        FEED_LOG.info("【查询系统】存入消息队列，返回值：{}",aLong);
    }
}
