package com.chezhibao.bigdata.msg.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * 估价师
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/10.
 */
@Data
@ToString
public class Appraiser {
    /**
     * 估价师ID
     */
    private Integer userId;
    /**
     * 估价师名称
     */
    private String userName;
    /**
     * 待估价车辆数
     */
    private String count;
    /**
     * 状态 1 正常 0 离职 2 离开
     */
    private Integer status;
}
