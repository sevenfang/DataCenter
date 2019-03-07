package com.chezhibao.bigdata.zipkin.dubbo.dubbo.support;

import com.alibaba.dubbo.rpc.RpcContext;
import com.chezhibao.bigdata.zipkin.dubbo.dubbo.DubboClientNameProvider;
import com.github.kristofa.brave.SpanId;

/**
 *   解析dubbo consumer applicationName
 *   @see  com.chezhibao.bigdata.zipkin.dubbo.dubbo.DubboClientRequestAdapter#addSpanIdToRequest(SpanId spanId)
 *   RpcContext.getContext().setAttachment("clientName", application);

 */
public class DefaultClientNameProvider implements DubboClientNameProvider {
    @Override
    public String resolveClientName(RpcContext rpcContext) {
        String application = RpcContext.getContext().getUrl().getParameter("clientName");
        return application;
    }
}
