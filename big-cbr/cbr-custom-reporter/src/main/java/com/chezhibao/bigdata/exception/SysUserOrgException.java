package com.chezhibao.bigdata.exception;

import com.chezhibao.bigdata.common.exception.BigException;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/5/31.
 */
public class SysUserOrgException extends BigException {
    public SysUserOrgException(Integer code, String message) {
        super(code, message);
    }
}
