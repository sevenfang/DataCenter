package com.chezhibao.bigdata.datax.enums;

import lombok.Getter;

/**
 * @author WangCongJun
 * @date 2018/5/7
 * Created by WangCongJun on 2018/5/7.
 */
@Getter
public enum  DataxEnums {
    /**
     * 传入的类型不存在
     */
    TYPE_NO_EXIST(10001,"reader or writer type doesn't exist"),
    /**
     * 查询的job不存在
     */
    JOB_NO_EXIST(10002,"the job is not exist!"),
    /**
     * 查询的job不存在
     */
    MULTI_JOB_ERROR(10003,"查询到多个job异常！")
    ;
    private Integer code;
    private String msg;
    DataxEnums(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }
}
