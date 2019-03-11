/*
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.chezhibao.bigdata.gateway.adapter.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chezhibao.bigdata.gateway.exception.ApiException;
import com.chezhibao.bigdata.gateway.pojo.DubboParam;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * dubbo proxy service is  use GenericService.
 *
 * @author xiaoyu(Myth)
 */
@Service
@SuppressWarnings("all")
public class DubboProxyService {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private RegistryServeice RegistryServeice;

    /**
     * logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DubboProxyService.class);

    /**
     * Generic invoker object.
     *
     * @param paramMap            the param map
     * @param dubboSelectorHandle the dubbo selector handle
     * @param dubboRuleHandle     the dubbo rule handle
     * @return the object
     * @throws SoulException the soul exception
     */
    public Object genericInvoker(final DubboRequest dubboRequest) {

        ReferenceConfig<GenericService> reference = buildReferenceConfig(dubboRequest);

        ReferenceConfigCache referenceConfigCache = ReferenceConfigCache.getCache();
        GenericService genericService = null;

        try {
            genericService = referenceConfigCache.get(reference);
        } catch (NullPointerException ex) {
            referenceConfigCache.destroy(reference);
            ex.printStackTrace();
        }

        // 用Map表示POJO参数，如果返回值为POJO也将自动转成Map
        final String method = dubboRequest.getMethod();
        Pair<String[],Object[]> pair = buildParameter(dubboRequest);
        try {
            // 基本类型以及Date,List,Map等不需要转换，直接调用
            return genericService.$invoke(method, pair.getLeft(), pair.getRight());
        } catch (GenericException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private Pair<String[],Object[]> buildParameter(final DubboRequest dubboRequest) {
        String params = dubboRequest.getDubboParams();
        List<DubboParam> dubboParams = new ArrayList<>(0);
        if(!StringUtils.isEmpty(params)){
            dubboParams = JSON.parseObject(params,new TypeReference<List<DubboParam>>(){});
        }

        int len = dubboParams.size();
        if(len == 0){
            return new ImmutablePair<>(new String[]{},new Object[]{});
        }
        String [] calssName = new String[len];
        Object [] values = new Object[len];
        Map<String, Object> paramValues = dubboRequest.getParamValues();
        for(int i = 0 ; i < len ; i++){
            DubboParam dubboParam = dubboParams.get(i);
            String name = dubboParam.getName();
            Object o = paramValues.get(name);
            if(dubboParam.getRequired() && o == null){
                throw new ApiException(name + " value is required!!");
            }
            calssName[i] = dubboParam.getClassName();
            values[i] = o;
        }
        return new ImmutablePair<>(calssName,values);
    }

    private ReferenceConfig<GenericService> buildReferenceConfig( final DubboRequest dubboRequest) {

        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();

        reference.setGeneric(true);

        final ApplicationConfig applicationConfig = applicationService.getRegistryConfig(dubboRequest.getAppId());
        final RegistryConfig registryConfig = RegistryServeice.getRegistryConfig(dubboRequest.getRegId());

        reference.setApplication(applicationConfig);

        reference.setRegistry(registryConfig);

        reference.setInterface(dubboRequest.getInterfaceName());

        if(dubboRequest.getTimeout()!=null){
            reference.setTimeout(dubboRequest.getTimeout());
        }
        if(dubboRequest.getRetries()!=null){
            reference.setRetries(dubboRequest.getRetries());
        }
        if(dubboRequest.getLoadbalance()!=null){
            reference.setLoadbalance(dubboRequest.getLoadbalance());
        }
        if(dubboRequest.getProtocol()!=null){
            reference.setProtocol(dubboRequest.getProtocol());
        }
        return reference;
    }

    /**
     * @author WangCongJun
     * Created by WangCongJun on 2019/2/20.
     */
    @Data
    public static class DubboRequest {
        /**
         * 唯一标识
         */
        private Integer id;

        /**
         * http访问地址
         */
        private String uri;
        /**
         * 应用ID
         */
        private Integer appId;
        /**
         * 注册中心ID
         */
        private Integer regId;
        /**
         * 应用名称
         */
        private String appName;

        /**
         * dubbo 请求参数
         */
        private String dubboParams;
        /**
         * The constant CLASS_PARAMS.
         */
        private Map<String,Object> paramValues;

        /**
         * The constant INTERFACE_NAME.
         */
        private String interfaceName;

        /**
         * The constant METHOD.
         */
        private String method;

        /**
         * The constant TIMEOUT.
         */
        private Integer timeout;

        /**
         * The constant VERSION.
         */
        private String version;

        /**
         * The constant GROUP.
         */
        private String group;

        /**
         * The constant RETRIES.
         */
        private Integer retries;

        /**
         * The constant LOADBALANCE.
         */
        private String loadbalance;

        /**
         * The constant protocol.
         */
        private String protocol;

        private Date createdTime;

        private Date updatedTime;
        /**
         * 创建人
         */
        private Integer useId;
    }

}
