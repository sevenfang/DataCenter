package com.chezhibao.bigdata.system.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author WangCongJun
 * @date 2018/5/21
 * Created by WangCongJun on 2018/5/21.
 */
@Data
public class ZkNode implements Serializable {
    /**
     * 节点名称
     */
    private String name;
    /**
     * 父路径
     */
    private String parentPath;
    /**
     * 节点值
     */
    private String value;
}
