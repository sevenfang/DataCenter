package com.chezhibao.bigdata.gateway.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.gateway.auth.exception.AuthException;
import com.chezhibao.bigdata.gateway.auth.exception.LoginException;
import com.chezhibao.bigdata.gateway.auth.service.AuthService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;


/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/13.
 */
@Component
@Scope("prototype")
@Slf4j
public class GatewayHttpServer extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Autowired
    private ApiExecutor apiExecutor;

    @Autowired
    private AuthService authService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request)  {
        BigdataResult result;
        //权限校验
        try {
            if(!request.uri().startsWith("/test")){
                authService.authentic(request);
            }
            //执行服务调用
            result  = apiExecutor.execute(request);
        }catch (LoginException login){
            result = BigdataResult.build(4004,"未登陆");
        }catch (AuthException auth){
            result = BigdataResult.build(4005,"没有权限");
        }catch (Exception e) {
            result = BigdataResult.build(4006,"发生异常");
            log.error("【网关系统|权限服务】权鉴异常",e);
        }
        String s = JSON.toJSONString(result, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteNullNumberAsZero);
        ByteBuf buffer = Unpooled.copiedBuffer(s, CharsetUtil.UTF_8);
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buffer);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                .set(HttpHeaderNames.CONTENT_LENGTH, buffer.readableBytes());
        ctx.writeAndFlush(response);
    }


}
