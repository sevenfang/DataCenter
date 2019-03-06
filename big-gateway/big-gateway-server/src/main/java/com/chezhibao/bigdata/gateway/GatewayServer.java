package com.chezhibao.bigdata.gateway;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.gateway.adapter.dubbo.ApplicationService;
import com.chezhibao.bigdata.gateway.adapter.dubbo.DubboProxyService;
import com.chezhibao.bigdata.gateway.adapter.dubbo.RegistryServeice;
import com.chezhibao.bigdata.gateway.core.ApiExecutor;
import com.chezhibao.bigdata.gateway.core.GatewayChannelInitialHandler;
import com.chezhibao.bigdata.gateway.core.config.GatewayConfig;
import com.chezhibao.bigdata.gateway.core.factory.ApiFactory;
import com.chezhibao.bigdata.gateway.pojo.ApiInfo;
import com.chezhibao.bigdata.gateway.core.pojo.ApiInfoBO;
import com.chezhibao.bigdata.gateway.util.SpringApplicationContextUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/22.
 */
@Component
@Slf4j
public class GatewayServer implements ApplicationContextAware {

    public static ApplicationContext CONTEXT;
    @Autowired
    private GatewayConfig gatewayConfig;

    @Autowired
    private GatewayChannelInitialHandler channelInitialHandler;

    private NioEventLoopGroup parentGroup = new NioEventLoopGroup();
    private NioEventLoopGroup childGroup = new NioEventLoopGroup();

    @PostConstruct
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String appName = "gateway";
                String registerServer = gatewayConfig.getRegistryAddr();
                if(StringUtils.isEmpty(registerServer)){
                    Environment env = CONTEXT.getEnvironment();
                    registerServer = env.getProperty("spring.dubbo.registry.address");
                }
                ApplicationService.APPLICATION_CONFIG_MAP.put(-1, new ApplicationConfig(appName));
                RegistryServeice.REGISTRY_CONFIG_MAP.put(-1, new RegistryConfig(registerServer));
                try {
                    ServerBootstrap bootstrap = new ServerBootstrap();
                    bootstrap.group(parentGroup, childGroup).channel(NioServerSocketChannel.class)
                            .childHandler(channelInitialHandler);
                    ChannelFuture sync = bootstrap.bind(gatewayConfig.getPort()).sync();
                    sync.channel().closeFuture().sync();
                } catch (Exception e) {
                    parentGroup.shutdownGracefully();
                    childGroup.shutdownGracefully();
                }
            }
        }).start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
    }

    @PreDestroy
    public void close() {
        log.info("关闭服务器....");
        //优雅退出
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }
}
