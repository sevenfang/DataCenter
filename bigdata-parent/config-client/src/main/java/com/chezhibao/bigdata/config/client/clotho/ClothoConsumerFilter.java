package com.chezhibao.bigdata.config.client.clotho;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.remoting.TimeoutException;
import com.alibaba.dubbo.rpc.*;
import com.chezhibao.clotho.Clotho;
import com.chezhibao.clotho.ClothoRpcContext;

/**
 * 链路跟踪客户端拦截
 */
@Activate(group = Constants.CONSUMER, order = -8000)
public class ClothoConsumerFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(ClothoConsumerFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String protocol = invoker.getUrl().getProtocol();
        // 如果是本地调用， 不进行链路埋点
        if (Constants.LOCAL_PROTOCOL.equals(protocol)) {
            return invoker.invoke(invocation);
        }
        // 获取当前的Context
        ClothoRpcContext oldContext = Clotho.getRpcContext();

        String interfaceName = invoker.getInterface().getName();
        String methodName = invocation.getMethodName();
        try {
            // 开始调用
            Clotho.startRpc(interfaceName, MethodLogNameConverter.convert(invocation));
            Clotho.remoteIp(RpcContext.getContext().getRemoteHost());
            ((RpcInvocation) invocation).addAttachments(Clotho.currentRpcContext());
            // 发送client请求
            Clotho.rpcClientSend();
            Result result = invoker.invoke(invocation);
            if (result != null) {
                // 必须放在 Clotho#rpcClientRecv前面， 在rpcClientRecv会把context恢复成父的context
                String responseSize = result.getAttachment(Constants.OUTPUT_KEY);
                if (StringUtils.isNotEmpty(responseSize)) {
                    try {
                        Clotho.responseSize(Long.parseLong(responseSize));
                    } catch (Exception e) {
                        logger.error("clotho response size error", e);
                    }
                }

                if (Clotho.isLogDumpEnabled()) {
                    Clotho.dump("csfClient", "call#" + interfaceName + "#" + methodName,
                            result.hasException() ? result.getException() : result.getValue(), invocation.getArguments());
                }

                if (result.hasException()) {
                    // 业务异常
                    Clotho.rpcClientRecv(Clotho.RPC_RESULT_FAILED, Clotho.TYPE_CSF_CLIENT, result.getException().getClass().getSimpleName());
                } else {
                    Clotho.rpcClientRecv(Clotho.RPC_RESULT_SUCCESS);
                }
            }
            return result;
        } catch (RpcException e) {
            if (Clotho.isLogDumpEnabled()) {
                Clotho.dump("csfClient", "call#" + interfaceName + "#" + methodName, e, invocation.getArguments());
            }

            if (e.getCause() != null && e.getCause() instanceof TimeoutException) {
                Clotho.rpcClientRecv(Clotho.RPC_RESULT_TIMEOUT);
            } else {
                Clotho.rpcClientRecv(Clotho.RPC_RESULT_RPC_ERROR);
            }
            throw e;
        } finally {
            Clotho.setRpcContext(oldContext);
        }
    }
}
