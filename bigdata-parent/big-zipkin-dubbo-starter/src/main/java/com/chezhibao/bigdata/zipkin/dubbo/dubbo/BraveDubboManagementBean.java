package com.chezhibao.bigdata.zipkin.dubbo.dubbo;

import com.github.kristofa.brave.Brave;

/**
 * Created by chenjg on 16/7/25.
 */
public class BraveDubboManagementBean {

    public Brave brave;

    public BraveDubboManagementBean(Brave brave) {
        this.brave = brave;
        BraveConsumerFilter.setBrave(brave);
        BraveProviderFilter.setBrave(brave);
    }

    public void setBrave(Brave brave) {
        this.brave = brave;
        BraveConsumerFilter.setBrave(brave);
        BraveProviderFilter.setBrave(brave);
    }
}
