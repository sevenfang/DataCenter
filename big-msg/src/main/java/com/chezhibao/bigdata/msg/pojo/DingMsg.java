package com.chezhibao.bigdata.msg.pojo;

import lombok.Data;

/**
 * @author WangCongJun
 * @date 2018/5/9
 * Created by WangCongJun on 2018/5/9.
 */
@Data
public class DingMsg {
    private String title = "钉钉消息";
    private String group;
    //多个请用，隔开
    private String userId;
    private String msg;
    private Boolean all=false;
    private String mobiles;
}
