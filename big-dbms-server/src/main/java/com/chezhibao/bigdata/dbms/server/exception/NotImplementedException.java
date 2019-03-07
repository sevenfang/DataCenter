package com.chezhibao.bigdata.dbms.server.exception;

import com.chezhibao.bigdata.common.exception.BigException;
import com.chezhibao.bigdata.dbms.server.enums.DbmsServerEnums;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/8.
 */
public class NotImplementedException extends BigException {
    public NotImplementedException(Integer code, String message) {
        super(code, message);
    }
    public NotImplementedException(DbmsServerEnums enums) {
        super(enums.getCode(), enums.getMsg());
    }
}
