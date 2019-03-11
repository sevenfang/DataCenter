package com.chezhibao.bigdata.gateway.exception;

import lombok.Data;

/**
 * @author WangCongJun
 * @date 2018/5/9
 * Created by WangCongJun on 2018/5/9.
 */
@Data
public class BigException extends RuntimeException {
    private Integer code;
    public BigException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
