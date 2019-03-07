package com.chezhibao.bigdata.zipkin.dubbo.dubbo;

import com.github.kristofa.brave.*;
import com.github.kristofa.brave.http.HttpSpanCollector;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.FactoryBean;

import java.util.logging.Logger;

/**
 * Created by jack-cooper on 2017/2/20.
 */
public class BraveFactoryBean implements FactoryBean<Brave> {
    private static final Logger LOGGER = Logger.getLogger(BraveFactoryBean.class.getName());
    /**服务名*/
    private String serviceName;
    /**zipkin服务器ip及端口，不配置默认打印日志*/
    private String zipkinHost;
    /**采样率 0~1 之间*/
    private float rate = 1.0f;
    /**单例模式*/
    private Brave instance;

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getZipkinHost() {
        return zipkinHost;
    }

    public String getRate() {
        return String.valueOf(rate);
    }

    public void setRate(String rate) {
        this.rate = Float.parseFloat(rate);
    }

    public void setZipkinHost(String zipkinHost) {
        this.zipkinHost = zipkinHost;
    }

    private void createInstance() {
        if (this.serviceName == null) {
            throw new BeanInitializationException("Property serviceName must be set.");
        }
        Brave.Builder builder = new Brave.Builder(this.serviceName);
        if (this.zipkinHost != null && !"".equals(this.zipkinHost)) {
            builder.spanCollector(HttpSpanCollector.create(this.zipkinHost, new EmptySpanCollectorMetricsHandler())).traceSampler(Sampler.create(rate)).build();
            LOGGER.info("brave dubbo config collect whith httpSpanColler , rate is "+ rate);
        }else{
            builder.spanCollector(new LoggingSpanCollector()).traceSampler(Sampler.create(rate)).build();
            LOGGER.info("brave dubbo config collect whith loggingSpanColletor , rate is "+ rate);
        }
        this.instance = builder.build();
    }



    @Override
    public Brave getObject() throws Exception {
        if (this.instance == null) {
            this.createInstance();
        }
        return this.instance;
    }

    @Override
    public Class<?> getObjectType() {
        return Brave.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
