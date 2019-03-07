package com.chezhibao.bigdata.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.common.pojo.AppBasicInfo;
import com.chezhibao.bigdata.system.constants.Constants;
import com.chezhibao.bigdata.system.pojo.ZkNode;
import com.chezhibao.bigdata.system.service.SystemConfigServer;
import com.chezhibao.bigdata.system.service.SystemRuntimeStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/22.
 */
@Service
@Slf4j
public class SystemRuntimeStatusServiceImpl implements SystemRuntimeStatusService {

    private SystemConfigServer systemConfigServer;

    public SystemRuntimeStatusServiceImpl(SystemConfigServer systemConfigServer) {
        this.systemConfigServer = systemConfigServer;
    }

    @Override
    public List<AppBasicInfo> getSystemInfoBySystemName(String sysName) {
        String sysPath = Constants.BIGDATSYSTEMSTATUS + "/" + sysName;
        if(!systemConfigServer.exist(sysPath)){
            //如果该系统节点不存在则返回null
            return null;
        }
        List<ZkNode> children = systemConfigServer.getChildren(sysPath);
        List<AppBasicInfo> apps = new ArrayList<>();
        for (ZkNode z : children) {
            String value = z.getValue();
            value = value.substring(value.indexOf("{"));
            log.debug("【系统配置】系统实例信息：{}", value);
            if (value.startsWith("{{")){
                value = value.substring(1);
            }
            AppBasicInfo appBasicInfo = JSON.parseObject(value, AppBasicInfo.class);
            //判断子节点的是否存在 存在表示系统在线  不存在表示系统不在线
            List<ZkNode> onlineStaus = systemConfigServer.getChildren(z.getParentPath() + "/" + z.getName());
            //将子节点的数量（1表示online 2表示 offline）添加到系统信息中
            appBasicInfo.setStatus(onlineStaus.size());
            apps.add(appBasicInfo);
        }
        return apps;
    }
}
