package com.chezhibao.bigdata.config.client.service;

import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.config.NacosConfigService;
import org.springframework.core.io.Resource;

import java.util.Properties;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/14.
 */

public interface BigdataNacosConfigService {
    /**
     * Get Configuration
     *
     * @param dataId    Config ID
     * @param timeoutMs read timeout
     * @return config value
     * @throws NacosException NacosException
     */
    String getConfig(String dataId, long timeoutMs) throws NacosException;

    /**
     * Add a listener to the configuration, after the server to modify the configuration, the client will use the
     * incoming listener callback. Recommended asynchronous processing, the application can implement the getExecutor
     * method in the ManagerListener, provide a thread pool of execution. If provided, use the main thread callback, May
     * block other configurations or be blocked by other configurations.
     *
     * @param dataId   Config ID
     * @param listener listener
     * @throws NacosException NacosException
     */
    void addListener(String dataId, Listener listener) throws NacosException;

    /**
     * publish config.
     *
     * @param dataId  Config ID
     * @param content Config Content
     * @return Whether publish
     * @throws NacosException NacosException
     */
    boolean publishConfig(String dataId, String content) throws NacosException;

    /**
     * Remove Config
     *
     * @param dataId Config ID
     * @return whether remove
     * @throws NacosException NacosException
     */
    boolean removeConfig(String dataId) throws NacosException;

    /**
     * Remove Listener
     *
     * @param dataId   Config ID
     * @param listener listener
     */
    void removeListener(String dataId, Listener listener);

    /**
     * server health info
     *
     * @return whether health
     */
    String getServerStatus();


    /**
     * 读取为properties配置
     * @param dataId
     * @param timeoutMs
     * @return
     * @throws NacosException
     */
    Properties getPropConfig(String dataId, String content,long timeoutMs) throws Exception;
    Resource getResource(String dataId, String content, long timeoutMs) throws Exception;

    /**
     * 读取环境的公共配置
     * 配置在dataId=public.yml
     * group=bigdata:env名称
     * 这里添加的配置会被应用中的覆盖
     * @return
     * @throws Exception
     */
    Properties getPublicConfig() throws Exception;


    NacosConfigService getNacosConfigService();
}
