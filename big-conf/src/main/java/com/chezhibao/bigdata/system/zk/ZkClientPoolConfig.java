package com.chezhibao.bigdata.system.zk;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.util.ObjectUtils;

/**
 * @author WangCongJun
 * @date 2018/5/17
 * Created by WangCongJun on 2018/5/17.
 */
public class ZkClientPoolConfig extends GenericObjectPoolConfig {
    public ZkClientPoolConfig(ZkProperties zkProperties){
        super();
        if(!ObjectUtils.isEmpty(zkProperties.getMaxIdle())){
            this.setMaxIdle(zkProperties.getMaxIdle());
        }
        if(!ObjectUtils.isEmpty(zkProperties.getMaxTotal())){
            this.setMaxTotal(zkProperties.getMaxTotal());
        }
        if(!ObjectUtils.isEmpty(zkProperties.getMinIdle())){
            this.setMinIdle(zkProperties.getMinIdle());
        }

    }
}
