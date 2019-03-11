package com.chezhibao.bigdata.service;

import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/11.
 */
public interface MockService {
     Object post(String url, Map<String,Object> params);
     Object send(String url,String method, Map<String,Object> params);
     Object get();
     Object put();
     Object delete();
}
