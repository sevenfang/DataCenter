package com.chezhibao.bigdata.config.client.sentinel;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.chezhibao.bigdata.config.client.config.NacosClientConfig;

import java.util.List;
import java.util.Properties;

/**
 * sentinel 降级处理服务
 * 读取nacos中的规则
 * 并监听配置规则的变化
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/15.
 */
public class SentinelRulerServer {
    private static final String FLOW_RULE =  "flow_rule.json";
    private static final String DEGRADE_RULE =  "degrade_rule.json";
    private static final String SYSTEM_RULE =  "system_rule.json";
    private  String namespace;

    public SentinelRulerServer(String namespace) {
        this.namespace = namespace;
    }

    public void loadRulers(){
        Properties properties = NacosClientConfig.getNacosProp();
        properties.put(PropertyKeyConst.NAMESPACE,namespace);
        String group = NacosClientConfig.getGroup();
        loadFlowRules(properties, group);
        loadDegradeRules(properties, group);
        loadSystemRules(properties, group);
    }

    private void loadFlowRules(Properties properties ,String groupId){
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(properties, groupId, FLOW_RULE,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }
    private void loadDegradeRules(Properties properties,String groupId){
        ReadableDataSource<String, List<DegradeRule>> flowRuleDataSource = new NacosDataSource<>(properties, groupId, DEGRADE_RULE,
                source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {}));
        DegradeRuleManager.register2Property(flowRuleDataSource.getProperty());
    }
    private void loadSystemRules(Properties properties,String groupId){
        ReadableDataSource<String, List<SystemRule>> flowRuleDataSource = new NacosDataSource<>(properties, groupId, SYSTEM_RULE,
                source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {}));
        SystemRuleManager.register2Property(flowRuleDataSource.getProperty());
    }
}
