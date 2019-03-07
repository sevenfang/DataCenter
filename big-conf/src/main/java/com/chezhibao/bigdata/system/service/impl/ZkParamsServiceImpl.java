package com.chezhibao.bigdata.system.service.impl;

import com.chezhibao.bigdata.ConfigServerLogEnum;
import com.chezhibao.bigdata.common.constan.DynamicConstant;
import com.chezhibao.bigdata.common.log.LoggerUtils;
import com.chezhibao.bigdata.common.pojo.AppBasicInfo;
import com.chezhibao.bigdata.system.constants.Constants;
import com.chezhibao.bigdata.system.listener.DynamicParameterListener;
import com.chezhibao.bigdata.system.pojo.ZkNode;
import com.chezhibao.bigdata.system.service.SystemConfigServer;
import com.chezhibao.bigdata.system.service.SystemRuntimeStatusService;
import com.chezhibao.bigdata.system.service.ZkParamsService;
import com.chezhibao.bigdata.system.vo.MenuGroupVo;
import com.chezhibao.bigdata.system.vo.MenuSysVo;
import com.chezhibao.bigdata.system.vo.SysConfigVo;
import com.chezhibao.bigdata.system.zk.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/23.
 */
@Service
public class ZkParamsServiceImpl implements ZkParamsService {

    private static final Logger DYNAMIC_LOGGER = LoggerUtils.Logger(ConfigServerLogEnum.DYNAMIC_PARAM_LOG);

    @Autowired
    private ZkClient zkClient;
    @Autowired
    private SystemConfigServer systemConfigServer;

    @Autowired
    private SystemRuntimeStatusService systemRuntimeStatusService;

    @Override
    public Map<String, MenuSysVo> getSysParamMenu() {
        Map<String, MenuSysVo> menuSysVos = new HashMap<>();
        List<MenuGroupVo> menuGroupVos;
        //获取菜单信息
        systemConfigServer.createNode(Constants.BIGDATACONFIGMENU, true);
        List<ZkNode> childrenName = systemConfigServer.getChildren(Constants.BIGDATACONFIGMENU);
        MenuSysVo menuSysVo;
        MenuGroupVo menuGroupVo;
        for (ZkNode sysNode : childrenName) {
            menuSysVo = new MenuSysVo();
            menuSysVo.setSysName(sysNode.getName());
            //获取分组列表
            List<ZkNode> groupList = systemConfigServer.getChildren(ZkUtils.getCurrentPath(sysNode));
            menuGroupVos = new ArrayList<>();
            for (ZkNode groupNode : groupList) {
                menuGroupVo = new MenuGroupVo();
                menuGroupVo.setGroupName(groupNode.getName());
                menuGroupVo.setSysName(sysNode.getName());
                menuGroupVo.setPath(groupNode.getValue());
                menuGroupVos.add(menuGroupVo);
            }
            menuSysVo.setGroups(menuGroupVos);
            //获取系统的所有启动实例的信息
            List<AppBasicInfo> apps = systemRuntimeStatusService.getSystemInfoBySystemName(sysNode.getName());
            if (apps != null) {
                menuSysVo.setStatus(1);
            } else {
                menuSysVo.setStatus(0);
            }
            menuSysVo.setApps(apps);

            menuSysVos.put(sysNode.getName(), menuSysVo);

        }
        return menuSysVos;
    }

    @Override
    public Boolean initListener() {
        //获取所有的系统参数
        Map<String, MenuSysVo> sysParamMenu = getSysParamMenu();
        //遍历所有的系统参数
        for (MenuSysVo menuSysVo : sysParamMenu.values()) {
            List<MenuGroupVo> groups = menuSysVo.getGroups();
            //遍历每个系统下的组
            for (MenuGroupVo menuGroupVo : groups) {
                //获取参数的配置组的地址
                String groupName = menuGroupVo.getGroupName();
                //遍历组下的所有参数
                Properties propFromZK = systemConfigServer.getPropFromZK(menuGroupVo.getPath());
                for (Map.Entry<Object, Object> e : propFromZK.entrySet()) {
                    String name = e.getKey() + "";
                    //判断是否是动态参数
                    String value = e.getValue() + "";
                    if (value.startsWith(DynamicConstant.DYNAMIC_PREFIX)) {
                        DYNAMIC_LOGGER.info("【配置客户端】初始化动态参数{}配置", groupName + "." + name);
                        //是动态参数加上监听
                        //组装一个SysConfigVo的对象
                        SysConfigVo vo = new SysConfigVo();
                        vo.setDynamic(true);
                        vo.setGroupName(groupName);
                        vo.setParamValue(value.substring(DynamicConstant.DYNAMIC_PREFIX.length()));
                        vo.setParamName(name);
                        vo.setSysName(menuGroupVo.getSysName());
                        DYNAMIC_LOGGER.info("【配置客户端】初始化动态参数SysConfigVo:{}", vo);
                        //监听路径
                        String path = vo.getParamPath();
                        DYNAMIC_LOGGER.info("【配置客户端】初始化动态参数监听路径{}", path);
                        //删除再添加  不然没有效果  后期寻找好的解决办法
                        zkClient.delete(path);
                        systemConfigServer.put(vo);
                    }

                }
            }
        }
        return true;
    }
}
