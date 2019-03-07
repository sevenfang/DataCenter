package com.chezhibao.bigdata.config.client.clotho;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.rpc.Invocation;

/**
 * 将方法名，参数类型转换成链路跟踪的日志格式
 * 格式：
 * methodName~ABC, 其中A，B，C分别为方法参数类型简单名称的第一个字母
 * 如 execute(String a, Integer b, List c) 将转换成 execute~SIL
 *
 * @author zhulixin
 */
public class MethodLogNameConverter {

    private static final Logger logger = LoggerFactory.getLogger(MethodLogNameConverter.class);

    /**
     * 转换方法名
     *
     * @param invocation Invocation
     * @return 转换后的值
     */
    static String convert(Invocation invocation) {
        String methodName = invocation.getMethodName();
        Class[] argTypes = invocation.getParameterTypes();
        Object[] argObjects = invocation.getArguments();
        if (methodName == null || argTypes == null) {
            return null;
        }
        if (argTypes.length == 0) {
            return methodName;
        }

        try {
            if (Constants.$INVOKE.equals(methodName) && argTypes.length == 3) {
                if (argObjects == null || argObjects.length != 3) {
                    return null;
                }

                methodName = (String) argObjects[0];
                if (argObjects[1] == null || ((String[]) argObjects[1]).length == 0) {
                    return methodName;
                }

                return getLogMethodName(methodName, (String[]) argObjects[1]);
            }

            return getLogMethodName(methodName, argTypes);
        } catch (Throwable e) {
            logger.error("convert log method name error", e);
            return methodName;
        }
    }


    private static String getLogMethodName(String methodName, String[] argTypes) {
        StringBuilder result = new StringBuilder(methodName);
        result.append("~");
        for (String type : argTypes) {
            int index = type.lastIndexOf('.') + 1;
            result.append((type.charAt(index)));
        }

        return result.toString();
    }

    private static String getLogMethodName(String methodName, Class[] argTypes) {
        StringBuilder result = new StringBuilder(methodName);
        result.append("~");
        for (Class type : argTypes) {
            result.append(type.getSimpleName().charAt(0));
        }

        return result.toString();
    }
}
