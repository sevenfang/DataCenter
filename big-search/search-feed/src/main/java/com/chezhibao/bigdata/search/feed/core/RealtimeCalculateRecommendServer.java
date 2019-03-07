package com.chezhibao.bigdata.search.feed.core;

import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.search.feed.service.LogService;
import com.chezhibao.bigdata.search.recommend.bo.FeedReqInfo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import static com.chezhibao.bigdata.search.common.SearchLogUtils.*;
/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/19.
 */
@Component
public class RealtimeCalculateRecommendServer{

    private LogService logService;

    private RealtimeCalculateRecommendTask recommendTask;

    public RealtimeCalculateRecommendServer(LogService logService,
                                            RealtimeCalculateRecommendTask recommendTask) {
        this.logService = logService;
        this.recommendTask = recommendTask;
    }

    @Scheduled(fixedDelay = 1000*5)
    public void calc() {
        FEED_LOG.info("【实时推荐】开始扫描日志进行推荐！！");
        String log = logService.getLog();
        while (log != null){
            try {
                FeedReqInfo feedReqInfo = JSON.parseObject(log, FeedReqInfo.class);
                recommendTask.calc(feedReqInfo);
            }catch (Exception e){
              FEED_LOG.error("【实时推荐】转化信息失败！！{}",log);
            }
            log = logService.getLog();
        }
    }
}
@Component
class RealtimeCalculateRecommendTask{
    private RecommendStrategyServer recommendStrategyServer;

    public RealtimeCalculateRecommendTask(RecommendStrategyServer recommendStrategyServer) {
        this.recommendStrategyServer = recommendStrategyServer;
    }

    @Async
    public void calc(FeedReqInfo feedReqInfo){
        if(FEED_LOG.isDebugEnabled()){
            FEED_LOG.debug("【实时推荐】实时推荐计算开始{}",feedReqInfo);
        }
        RecommendStrategy recommendStrategy = recommendStrategyServer.choiceStrategy(feedReqInfo);
        //不做推荐
        if(recommendStrategy==null){
            return;
        }
        recommendStrategy.recommend(feedReqInfo);
    }
}