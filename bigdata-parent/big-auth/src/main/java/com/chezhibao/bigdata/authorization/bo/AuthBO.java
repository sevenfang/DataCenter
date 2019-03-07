package com.chezhibao.bigdata.authorization.bo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/2.
 */
@Data
public class AuthBO {
    /**
     * 是否需要校验，是否市超级管理员
     */
    private Boolean isAdministrator;

    /**
     * 权鉴所需的参数
     */
    private Object authParams;

    /**
     * 组织ID
     */
    private List<Integer> orgIds;

    /**
     * 城市ID
     */
    private List<Integer> cityIds;
}
