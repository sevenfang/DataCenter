package com.chezhibao.bigdata.system.enums;

import lombok.Getter;

/**
 * @author WangCongJun
 * @date 2018/5/18
 * Created by WangCongJun on 2018/5/18.
 */
@Getter
public enum  SystemConfigEnums {
    /**
     * 在写入新的节点时，此节点已经存在与zk上了
     */
    ZNODE_IS_EXIST_ERROR(120001,"zk节点已经存在！"),
    /**
     * 在更新节点时，此节点已经不在与zk上了
     */
    ZNODE_NOT_EXIST_ERROR(120002,"zk节点不存在！"),
    /**
     * 在更新节点时，此节点存在子节点
     */
    ZNODE_HAS_CHILDREN_ERROR(120003,"zk节点不可更新,此节点为父节点不可写入数据！"),
    /**
     * 在写入新的节点时，此节点已经存在与zk上了
     */
    ZNODE_HAS_NO_CHILDREN_ERROR(120004,"zk节点子节点不存在！"),
    /**
     * 在写入新的节点时，此节点已经存在与zk上了
     */
    GROUP_HAS_NO_PATHINFO(120005,"分组节点下没有分组路劲数据！"),
    ;

    private Integer code;
    private String msg;

     SystemConfigEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
