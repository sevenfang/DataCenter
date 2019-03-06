package com.chezhibao.bigdata.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/6/27.
 */
@Data
public class SessionBO {
    private List<String> sessionNames;
    private String sessionId;
    /**
     * 场次开始时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date start;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date end;
    /**
     * 场次结束时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date overStart;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date overEnd;
}
