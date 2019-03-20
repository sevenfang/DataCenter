package com.chezhibao.bigdata.cache.enums;


/**
 * @author WangCongJun
 * @date 2018/5/16
 * Created by WangCongJun on 2018/5/16.
 */
public enum BigCacheEnum {
    /**
     * 缓存出错了
     */
    NAMESPACE_IS_NOT_EXIST(410003,"namespace_is_not_exist"),
    /**
     * 缓存出错了
     */
    UN_IMPLEMENT_EXCEPTION(410004,"client_is_not_implement"),
    /**
     * 缓存出错了
     */
    CACHE_VALUE_ERROR(410001,"缓存出错了！"),
    /**
     * 缓存过期时间不能为负数
     */
    EXPIRE_TIME_ERROR(410002,"缓存时间不能为负数！");
    private Integer code;
    private String msg;

    BigCacheEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
