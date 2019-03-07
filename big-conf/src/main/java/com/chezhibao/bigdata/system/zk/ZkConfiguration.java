package com.chezhibao.bigdata.system.zk;

import com.chezhibao.bigdata.system.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;

/**
 * @author WangCongJun
 * @date 2018/5/17
 * Created by WangCongJun on 2018/5/17.
 */
@Configuration
@Slf4j
public class ZkConfiguration {

//    @Bean("zkPoll")
    public GenericObjectPool<ZkClient> zkClientPool(ZkProperties zkProperties){
        ZkClientPoolConfig zkClientPoolConfig = new ZkClientPoolConfig(zkProperties);
        ZkClientFactory zkClientFactory = new ZkClientFactory();
        zkClientFactory.setZkProperties(zkProperties);
        return new GenericObjectPool<>(zkClientFactory,zkClientPoolConfig);
    }

//    @Bean(value = "zkClient")
//    @Scope("prototype")
//    @Resource(name = "zkPoll")
    public ZkClient zkClient( GenericObjectPool<ZkClient> zkClientPool){
        try {
            return zkClientPool.borrowObject();
        }catch (Exception e){
            return null;
        }

    }

    @Bean
    public ZkClient zkClient(ZkProperties zkProperties) throws Exception{
        ZkClientFactory zkClientFactory = new ZkClientFactory();
        zkClientFactory.setZkProperties(zkProperties);
        ZkClient zkClient = zkClientFactory.create();
        log.info("【系统管理】初始化zk目录......");
        if(!zkClient.exists(Constants.BIGDATACONFIGPATH)) {
            zkClient.createPersistent(Constants.BIGDATACONFIGPATH, true);
        }
        if(!zkClient.exists(Constants.BIGDATACONFIGMENU)) {
            zkClient.createPersistent(Constants.BIGDATACONFIGMENU, true);
        }
        return zkClient;
    }
}
