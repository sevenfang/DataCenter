package com.chezhibao.bigdata.dbms.server.vo;

import lombok.Data;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/24.
 */
@Data
public class DBItemVO {
    private Integer id;
    private String key;
    private Object value;
}
