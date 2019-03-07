package com.chezhibao.bigdata.template.exception;

import com.chezhibao.bigdata.template.enums.SqlTemplateEnum;
import lombok.Data;

/**
 * @author WangCongJun
 * @date 2018/4/26
 * Created by WangCongJun on 2018/4/26.
 */
@Data
public class SqlTemplateException extends RuntimeException {
    private Integer code;
    private String msg;
    public SqlTemplateException(Exception e){
        this.msg=e.getMessage();
       this.code=30000;
    }
    public SqlTemplateException(SqlTemplateEnum sqlTemplateEnum){
        this.code=sqlTemplateEnum.getCode();
        this.msg=sqlTemplateEnum.getMsg();
    }
}
