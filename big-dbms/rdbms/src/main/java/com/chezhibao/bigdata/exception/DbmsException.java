package com.chezhibao.bigdata.exception;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/17.
 */
public class DbmsException extends RuntimeException {
    private String msg;
    private Integer code;

    public DbmsException(  Integer code,String msg,Throwable cause) {
        super(cause);
        this.msg = msg;
        this.code = code;
    }

    public DbmsException(Integer code , String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }
}
