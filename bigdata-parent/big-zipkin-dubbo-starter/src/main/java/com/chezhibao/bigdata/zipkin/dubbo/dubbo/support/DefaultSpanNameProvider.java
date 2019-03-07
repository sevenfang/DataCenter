package com.chezhibao.bigdata.zipkin.dubbo.dubbo.support;

import com.alibaba.dubbo.rpc.RpcContext;
import com.chezhibao.bigdata.zipkin.dubbo.dubbo.DubboSpanNameProvider;

/**
 * Created by chenjg on 16/8/22.
 */
public class DefaultSpanNameProvider implements DubboSpanNameProvider {
    @Override
    public String resolveSpanName(RpcContext rpcContext) {
        String className = rpcContext.getUrl().getPath();
        String simpleName = className.substring(className.lastIndexOf(".")+1);
        return simpleName+"."+rpcContext.getMethodName();

    }
}
