package com.chezhibao.bigdata.common.pojo;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author WangCongJun
 * @date 2018/5/4
 * Created by WangCongJun on 2018/5/4.
 */
@Slf4j
public class BigdataResult implements Serializable {
    // 定义jackson对象

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static BigdataResult build(Integer status, String msg, Object data) {
        return new BigdataResult(status, msg, data);
    }

    public static BigdataResult ok(Object data) {
        return new BigdataResult(data);
    }

    public static BigdataResult ok() {
        return new BigdataResult(null);
    }

    public BigdataResult() {

    }

    public static BigdataResult build(Integer status, String msg) {
        return new BigdataResult(status, msg, null);
    }

    public BigdataResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public BigdataResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

//    public Boolean isOK() {
//        return this.status == 200;
//    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }



    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static BigdataResult format(String json) {
        try {
            return JSON.parseObject(json, BigdataResult.class);
        } catch (Exception e) {
            log.error("出错了",e);
        }
        return null;
    }

}
