package com.chezhibao.bigdata.cache.enums;

import lombok.Getter;

/**
 * @author WangCongJun
 * @date 2018/5/16
 * Created by WangCongJun on 2018/5/16.
 */
@Getter
public enum BigCacheEnum {
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
}
