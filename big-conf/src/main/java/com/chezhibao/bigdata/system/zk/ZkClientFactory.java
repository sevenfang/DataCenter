package com.chezhibao.bigdata.system.zk;

import lombok.Data;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author WangCongJun
 * @date 2018/5/17
 * Created by WangCongJun on 2018/5/17.
 */
@Data
public class ZkClientFactory extends BasePooledObjectFactory<ZkClient> {

    private ZkProperties zkProperties;

    @Override
    public ZkClient create() throws Exception {
        String servers = zkProperties.getServers();
        if(StringUtils.isEmpty(servers)){
            servers="127.0.0.1:2181";
        }
        Integer sessionTimeout = zkProperties.getSessionTimeout();
        if(ObjectUtils.isEmpty(sessionTimeout)){
            sessionTimeout = Integer.MAX_VALUE;
        }
        Integer connectionTimeout = zkProperties.getConnectionTimeout();
        if(ObjectUtils.isEmpty(connectionTimeout)){
            connectionTimeout=30000;
        }
        return new ZkClient(servers, sessionTimeout, connectionTimeout, new ZkStringSerializer());
    }

    @Override
    public PooledObject<ZkClient> wrap(ZkClient zkClient) {
        return new DefaultPooledObject<>(zkClient);
    }
}
