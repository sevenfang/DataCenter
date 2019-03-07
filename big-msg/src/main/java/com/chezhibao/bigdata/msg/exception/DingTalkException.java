package com.chezhibao.bigdata.msg.exception;

import com.chezhibao.bigdata.common.exception.BigException;
import com.chezhibao.bigdata.msg.enums.DingTalkEnums;
import lombok.Data;

/**
 * @author WangCongJun
 * @date 2018/5/9
 * Created by WangCongJun on 2018/5/9.
 */
@Data
public class DingTalkException extends BigException {
    private DingTalkException(String message, Integer code) {
        super(code,message);
    }

    private DingTalkException(DingTalkEnums dingTalkEnums) {
        super(dingTalkEnums.getCode(),dingTalkEnums.getMsg());
    }

    public static DingTalkException newInstance(String message, Integer code){
        return new DingTalkException(message,code);
    }

    public static DingTalkException newInstance(DingTalkEnums dingTalkEnums){
        return new DingTalkException(dingTalkEnums);
    }
}
