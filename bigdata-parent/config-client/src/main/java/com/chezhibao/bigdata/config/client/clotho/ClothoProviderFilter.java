package com.chezhibao.bigdata.config.client.clotho;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.*;
import com.chezhibao.clotho.Clotho;
import com.chezhibao.clotho.ClothoRpcContext;

/**
 * 链路跟踪服务端拦截
 */
@Activate(group = Constants.PROVIDER, order = -8000)
public class ClothoProviderFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ClothoProviderFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String protocol = invoker.getUrl().getProtocol();
        // 如果是本地调用， 不进行链路埋点
        if (Constants.LOCAL_PROTOCOL.equals(protocol)) {
            return invoker.invoke(invocation);
        }

        ClothoRpcContext oldContext = Clotho.getRpcContext();
        try {
            Clotho.setRpcContext(invocation.getAttachments());
            Clotho.rpcServerRecv(invoker.getInterface().getName(), MethodLogNameConverter.convert(invocation));

            Clotho.remoteIp(RpcContext.getContext().getRemoteHost());
            String requestSize = invocation.getAttachment(Constants.INPUT_KEY);
            if (StringUtils.isNotEmpty(requestSize)) {
                try {
                    Clotho.requestSize(Long.parseLong(requestSize));
                } catch (Exception e) {
                    logger.error("clotho request size error", e);
                }
            }
            Result result = invoker.invoke(invocation);
            if (result.hasException()) {
                // 业务异常
                Clotho.rpcServerSend(Clotho.RPC_RESULT_FAILED, Clotho.TYPE_CSF_SERVER, result.getException().getClass().getSimpleName());
            } else {
                Clotho.rpcServerSend(Clotho.RPC_RESULT_SUCCESS, Clotho.TYPE_CSF_SERVER);
            }
            return result;
        } catch (RpcException e) {
            Clotho.rpcServerSend(Clotho.RPC_RESULT_RPC_ERROR, Clotho.TYPE_CSF_SERVER);
            throw e;
        } finally {
            Clotho.setRpcContext(oldContext);
        }
    }
}
