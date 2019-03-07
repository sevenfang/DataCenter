package com.chezhibao.bigdata.msg.enums;

import lombok.Getter;

/**
 * @author WangCongJun
 * @date 2018/5/9
 * Created by WangCongJun on 2018/5/9.
 */
@Getter
public enum  DingTalkEnums {
    /**
     * 发送消息失败
     */
    PUSH_MSG_FAILED(233001,"发送钉钉消息失败！"),

    /**
     * 钉钉组名不能空
     */
    DING_GROUPNAME_IS_NULL(233002,"钉钉组名[group]不能为空！"),
    /**
     * 钉钉组token不存在
     */
    DING_GROUP_TOKEN_NOT_EXIST(233003,"钉钉组token不存在！请检查组名是否正确，后台是否设置改组名的token"),
    /**
     * 未指定user
     */
    DING_UNSPECIFIED_USER(233004,"未指定具体的消息接受人！！"),
    ;

    private Integer code;
    private String msg;

    DingTalkEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
