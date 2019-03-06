package com.chezhibao.bigdata.bo;

import lombok.Data;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/9.
 */
@Data
public class SysBO {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 所有管理的组织ID
     */
    private List<Integer> orgIds;
}
