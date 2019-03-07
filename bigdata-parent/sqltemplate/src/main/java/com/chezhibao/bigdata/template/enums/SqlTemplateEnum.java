package com.chezhibao.bigdata.template.enums;

import lombok.Getter;

/**
 * @author WangCongJun
 * @date 2018/4/26
 * Created by WangCongJun on 2018/4/26.
 */
@Getter
public enum SqlTemplateEnum {
    SQLID_FORMAT_ERROR(30001,"sqlID的格式不对，应该是文件名.id"),
    SQL_XMLFILE_NOT_EXIST(30002,"sqlID对应的xml文件不存在"),
    SQLID_NOT_EXIST(30003,"sqlID不存在"),
    READ_SQL_TEMPLATE_ERROR(30004,"读取sql模板文件出错"),
    ;
    private Integer code;
    private String msg;
    SqlTemplateEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }
}
