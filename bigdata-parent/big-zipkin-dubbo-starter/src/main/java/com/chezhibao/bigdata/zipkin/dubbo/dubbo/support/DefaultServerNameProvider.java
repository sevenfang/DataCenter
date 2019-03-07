package com.chezhibao.bigdata.zipkin.dubbo.dubbo.support;

import com.alibaba.dubbo.rpc.RpcContext;
import com.chezhibao.bigdata.zipkin.dubbo.dubbo.DubboServerNameProvider;

/**
 * Created by chenjg on 16/8/22.
 */

/**
 *   解析dubbo Provider applicationName
 */
public class DefaultServerNameProvider implements DubboServerNameProvider {
    @Override
    public String resolveServerName(RpcContext rpcContext) {
         String application = RpcContext.getContext().getUrl().getParameter("application");
         return application;
    }
}
