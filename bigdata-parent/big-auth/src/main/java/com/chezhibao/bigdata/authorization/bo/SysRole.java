package com.chezhibao.bigdata.authorization.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/30.
 */
@Data
public class SysRole implements Serializable {
    private String code;
    private String name;
    private Integer id;
}
