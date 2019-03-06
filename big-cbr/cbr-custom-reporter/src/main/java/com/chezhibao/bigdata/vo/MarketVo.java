package com.chezhibao.bigdata.vo;

import lombok.Data;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/5.
 */
@Data
public class MarketVo {
    private String id;
    private int type;
    private String time;
    private String channel;
    private String parentChannel;
    private String childChannel;
}
