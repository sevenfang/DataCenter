package com.chezhibao.bigdata.dbms.server.exception;

import com.chezhibao.bigdata.common.exception.BigException;
import com.chezhibao.bigdata.dbms.server.enums.DbmsServerEnums;

import java.util.Arrays;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/8.
 */
public class DbmsServerEception extends BigException {
    public DbmsServerEception(Integer code, String message) {
        super(code, message);
    }

    public DbmsServerEception(DbmsServerEnums enums, Exception e) {
        super(enums.getCode(), e.getMessage());
//        super(enums.getCode(), Arrays.toString(e.getStackTrace()));
    }

    public DbmsServerEception(DbmsServerEnums enums) {
        super(enums.getCode(), enums.getMsg());
    }
}
