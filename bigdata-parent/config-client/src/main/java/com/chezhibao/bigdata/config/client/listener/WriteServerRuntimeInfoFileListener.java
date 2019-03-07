package com.chezhibao.bigdata.config.client.listener;

import com.chezhibao.bigdata.config.client.config.NacosClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/10.
 */
@Slf4j
public class WriteServerRuntimeInfoFileListener implements ApplicationListener<SpringApplicationEvent>, Ordered {

    private int order = Ordered.LOWEST_PRECEDENCE + 1;

    @Override
    public void onApplicationEvent(SpringApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            try {
//                onApplicationEnvironmentPreparedEvent(
//                        (ApplicationEnvironmentPreparedEvent) event);
            }catch (Exception e){
                log.error("【配置服务】生成服务信息文件出错！！！");
            }
        }
        if (event instanceof ApplicationStartedEvent) {
            try {
                onApplicationStartedEvent(
                        (ApplicationStartedEvent) event);
            }catch (Exception e){
                log.error("【配置服务】生成服务信息文件出错！！！");
            }
        }
    }

    /**
     * pid:
     * name:
     * port:
     * registerServer:
     * version:
     * sentinelServer:
     * nacosDiscover:
     * nacosConfig:
     * zipkinSeerver:
     *
     * @param event
     */
    private void onApplicationStartedEvent(ApplicationStartedEvent event) throws Exception {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();
        String fileName = environment.getProperty("spring.info.file");
        if (StringUtils.isEmpty(fileName)) {
            return;
        }
        List<String> lines = new ArrayList<>();
        String pid = getPid();
        lines.add("PID=" + pid);
        String name = environment.getProperty("project.name");
        lines.add("NAME=" + name);
        String port = environment.getProperty("server.port");
        lines.add("PORT=" + port);
        String registerServer = environment.getProperty("spring.dubbo.registry.address");
        lines.add("REGISTER_SERVER=" + registerServer);
        String version = environment.getProperty("project.version");
        lines.add("VERSION=" + version);
        String sentinelServer = environment.getProperty("spring.cloud.sentinel.transport.dashboard");
        lines.add("SENTINEL_SERVER=" + sentinelServer);
        String nacosDiscover = environment.getProperty(NacosClientConfig.SPRING_CLOUD_NACOS_DISCOVERY_SERVER_ADDR);
        lines.add("NACOS_DISCOVER=" + nacosDiscover);
        String nacosConfig = environment.getProperty(NacosClientConfig.SPRING_CLOUD_NACOS_CONFIG_SERVER_ADDR);
        lines.add("NACOS_CONFIG=" + nacosConfig);
        String zipkinServer = environment.getProperty("spring.zipkin.base-url");
        lines.add("ZIPKIN_SERVER=" + zipkinServer);

        FileUtils.writeLines(new File(fileName), "UTF-8", lines);

    }

    private String getPid() {
        try {
            String jvmName = ManagementFactory.getRuntimeMXBean().getName();
            return jvmName.split("@")[0];
        } catch (Throwable ex) {
            return null;
        }
    }

    @Override
    public int getOrder() {
        return order;
    }
}
