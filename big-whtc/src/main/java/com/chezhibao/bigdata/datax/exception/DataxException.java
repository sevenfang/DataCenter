package com.chezhibao.bigdata.datax.exception;

import com.chezhibao.bigdata.common.exception.BigException;
import com.chezhibao.bigdata.datax.enums.DataxEnums;
import lombok.Data;

/**
 * @author WangCongJun
 * @date 2018/5/7
 * Created by WangCongJun on 2018/5/7.
 */
@Data
public class DataxException extends BigException {

    private Integer code;

    public DataxException(DataxEnums dataxEnums){
        super(dataxEnums.getCode(),dataxEnums.getMsg());
    }

    public DataxException(Integer code, String message) {
        super(code, message);
    }
}
