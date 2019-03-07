package com.chezhibao.bigdata.dbms.server.enums;

import lombok.Getter;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/8.
 */
@Getter
public enum DbmsServerEnums {
    /**
     * sql执行时出现异常
     */
    SQL_HANDLER_EXCEPTION(4001,"sql执行异常！")
    ,DELETE_NOT_IMPLEMENTED_EXCEPTION(4012,"delete方法没有实现")
    ,UPDATE_NOT_IMPLEMENTED_EXCEPTION(4013,"update方法没有实现")
    ,INSERT_NOT_IMPLEMENTED_EXCEPTION(4014,"insert方法没有实现")
    ,UNKNOWN_SQL_OPTION_EXCEPTION(4015,"未知数据库操作方法")
    ,DOWNLOAD_EXCEPTION(4025,"下载失败！")
    ,;

    private String msg;
    private Integer code;

    DbmsServerEnums( Integer code,String msg) {
        this.msg = msg;
        this.code = code;
    }
}
