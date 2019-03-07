package com.chezhibao.bigdata.msg.bo;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/11.
 */
public class CrawlMessageBO {
    private final AtomicReference<List<Object>> row = new AtomicReference<>();

    public List<Object> get(){
       return row.get();
    }
}
