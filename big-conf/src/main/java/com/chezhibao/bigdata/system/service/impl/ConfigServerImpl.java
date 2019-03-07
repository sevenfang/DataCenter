package com.chezhibao.bigdata.system.service.impl;

import com.chezhibao.bigdata.common.constan.DynamicConstant;
import com.chezhibao.bigdata.system.constants.Constants;
import com.chezhibao.bigdata.system.enums.SystemConfigEnums;
import com.chezhibao.bigdata.system.exception.SystemConfigException;
import com.chezhibao.bigdata.system.listener.DynamicParameterListener;
import com.chezhibao.bigdata.system.pojo.ZkNode;
import com.chezhibao.bigdata.system.service.SystemConfigServer;
import com.chezhibao.bigdata.system.service.SystemRuntimeStatusService;
import com.chezhibao.bigdata.system.vo.SysConfigVo;
import com.chezhibao.bigdata.system.zk.ZkProperties;
import com.chezhibao.bigdata.system.zk.ZkUtils;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author WangCongJun
 * @date 2018/5/17
 * Created by WangCongJun on 2018/5/17.
 */
@Component
@Slf4j
public class ConfigServerImpl implements SystemConfigServer {

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SystemRuntimeStatusService systemRuntimeStatusService;


    @Override
    public Properties getPropFromZK(String appName) {
        String path = Constants.BIGDATACONFIGPATH + "/" + appName;
        if (StringUtils.isEmpty(path)) {
            path = Constants.SLASH;
        } else {
            if (!path.startsWith(Constants.SLASH)) {
                path = Constants.SLASH + path;
            }
        }
        path = path.replaceAll("//", "/");
        Properties properties = ZkUtils.loopTraversalgetZnode(zkClient, path);

        return properties;
    }

    @Override
    public void put(SysConfigVo vo) {
        String  path,  value;
        Boolean isDynamic=vo.getDynamic();
        path = vo.getParamPath();
        value = vo.getParamValue();
        if (zkClient.exists(path)) {
            throw new SystemConfigException(SystemConfigEnums.ZNODE_IS_EXIST_ERROR);
        }
        //判断是否是动态参数，是则加上【dynamic】
        value = isDynamic ? DynamicConstant.DYNAMIC_PREFIX + value : value;
        if (isDynamic) {
            //是动态参数就加上监听
            DynamicParameterListener listener = new DynamicParameterListener(vo);
            listener.setSystemRuntimeStatusService(systemRuntimeStatusService);
            listener.setRestTemplate(restTemplate);
            zkClient.subscribeDataChanges(path, listener);
        }
        zkClient.createPersistent(path, true);
        zkClient.writeData(path, value);
    }

    @Override
    public void put(String path, String value) {
        if (zkClient.exists(path)) {
            throw new SystemConfigException(SystemConfigEnums.ZNODE_IS_EXIST_ERROR);
        }
        zkClient.createPersistent(path, true);
        zkClient.writeData(path, value);
    }

    @Override
    public Boolean exist(String path) {
        return zkClient.exists(path);
    }

    @Override
    public void updateNode(String path, String value) {
        if (!zkClient.exists(path)) {
            throw new SystemConfigException(SystemConfigEnums.ZNODE_NOT_EXIST_ERROR);
        }
        if (zkClient.countChildren(path) > 0) {
            throw new SystemConfigException(SystemConfigEnums.ZNODE_HAS_CHILDREN_ERROR);
        }

        zkClient.writeData(path, value);
    }

    @Override
    public void delNode(String path) {
        log.debug("【系统管理】删除的zk路径：{}", path);
        if (!zkClient.exists(path)) {
            throw new SystemConfigException(SystemConfigEnums.ZNODE_NOT_EXIST_ERROR);
        }
        if (zkClient.countChildren(path) > 0) {
            zkClient.deleteRecursive(path);
        } else {
            zkClient.delete(path);
        }
    }

    @Override
    public void delEmptyNode(String path) {
        //去除空节点
        int index = path.lastIndexOf("/");
        while (index > 0) {
            path = path.substring(0, index);
            List<String> children = zkClient.getChildren(path);
            if (children.size() > 0) {
                log.debug("【系统配置】此节点{}不为空！", path);
                break;
            }
            zkClient.delete(path);
            index = path.lastIndexOf("/");
        }
    }

    @Override
    public void createNode(String path, Boolean isPersist) {
        log.debug("【系统配置】创建节点的路径：{}", path);
        if (!zkClient.exists(path)) {
            if (isPersist) {
                zkClient.createPersistent(path, true);
            } else {
                zkClient.createEphemeral(path);
            }
        }
    }

    @Override
    public Integer countChildren(String path) {
        return zkClient.countChildren(path);
    }

    @Override
    public List<ZkNode> getChildren(String path) {
        int i = zkClient.countChildren(path);
        List<ZkNode> zkNodes = new ArrayList<>();
        if (i == 0) {
            log.info("【系统配置】获取子节点为空：{}", path);
            return zkNodes;
        }

        List<String> children = zkClient.getChildren(path);
        ZkNode zkNode;
        for (String name : children) {
            zkNode = new ZkNode();
            zkNode.setName(name);
            zkNode.setParentPath(path);
            Object o = zkClient.readData(path + "/" + name);
            if (!ObjectUtils.isEmpty(o)) {
                zkNode.setValue(o.toString());
            }

            zkNodes.add(zkNode);
        }
        log.debug("【系统配置】系统和分组信息", zkNodes);
        return zkNodes;
    }

    @Override
    public List<Map<String, Map<String, String>>> getSysMenu(String path) {

        return null;
    }

    @Override
    public void writeDate(String path, Object data) {
        zkClient.writeData(path, data);
    }
}
