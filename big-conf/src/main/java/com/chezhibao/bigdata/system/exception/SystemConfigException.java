package com.chezhibao.bigdata.system.exception;

import com.chezhibao.bigdata.common.exception.BigException;
import com.chezhibao.bigdata.system.enums.SystemConfigEnums;

/**
 * @author WangCongJun
 * @date 2018/5/18
 * Created by WangCongJun on 2018/5/18.
 */
public class SystemConfigException extends BigException {
    public SystemConfigException(Integer code, String message) {
        super(code, message);
    }

    public SystemConfigException(SystemConfigEnums systemConfigEnums) {
        this(systemConfigEnums.getCode(),systemConfigEnums.getMsg());
    }
}
