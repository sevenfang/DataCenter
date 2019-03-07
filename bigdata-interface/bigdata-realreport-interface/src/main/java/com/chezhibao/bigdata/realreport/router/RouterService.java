package com.chezhibao.bigdata.realreport.router;


import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/14.
 */
public interface RouterService {
    Boolean addRouter(Router router);

    Boolean updateRouter(Router router);

    Map<String, Router> getAllRouter();

    Router getRouterByLocation(String location);

    Boolean delRouter(String location) ;
}
