package com.chezhibao.bigdata.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.chezhibao.bigdata.common.constan.DynamicConstant;
import com.chezhibao.bigdata.common.pojo.AppBasicInfo;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.system.constants.Constants;
import com.chezhibao.bigdata.system.pojo.ZkNode;
import com.chezhibao.bigdata.system.service.SystemConfigServer;
import com.chezhibao.bigdata.system.service.SystemRuntimeStatusService;
import com.chezhibao.bigdata.system.service.ZkParamsService;
import com.chezhibao.bigdata.system.vo.MenuGroupVo;
import com.chezhibao.bigdata.system.vo.MenuSysVo;
import com.chezhibao.bigdata.system.vo.SysConfigVo;
import com.chezhibao.bigdata.system.zk.ZkProperties;
import com.chezhibao.bigdata.system.zk.ZkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author WangCongJun
 * @date 2018/5/18
 * Created by WangCongJun on 2018/5/18.
 */
@RestController
@RequestMapping("/sys")
@Slf4j
public class SystemConfigController {

    @Autowired
    private SystemConfigServer systemConfigServer;

    @Autowired
    private ZkParamsService zkParamsService;

    @Autowired
    private SystemRuntimeStatusService systemRuntimeStatusService;

    @Autowired
    private ZkProperties zkProperties;

    /**
     * 根据系统名称获取对应的配置
     *
     * @param sysName
     * @return
     */
    @RequestMapping("/config/{sysName}")
    public BigdataResult getSysConfig(@PathVariable("sysName") String sysName) {
        Properties propFromZK = systemConfigServer.getPropFromZK(sysName);
        propFromZK.put("zk.servers", zkProperties.getServers());
        String pidFile = "/opt/app/bigdata/pid/" + sysName;
        propFromZK.put("spring.pid.file", pidFile);
        return BigdataResult.ok(propFromZK);
    }

    /**
     * 根据配置的属性名称删除属性
     *
     * @param sysConfigVo
     * @return
     */
    @DeleteMapping("/config/menu/param")
    public BigdataResult delParamByName(SysConfigVo sysConfigVo) {
        String sysName = sysConfigVo.getSysName();
        String groupName = sysConfigVo.getGroupName();
        String paramName = sysConfigVo.getParamName();
        log.debug("【系统配置】delParamByName方法入参：{}", sysConfigVo);
        String s = sysName + "." + groupName;
        String groupPath = s.replaceAll("\\.", "/");
        //遍历删除数据节点
        int i = paramName.length();
        paramName = paramName.replaceAll("\\.", "/");
        while (i > 0) {
            paramName = paramName.substring(0, i);
            String paramPath = Constants.BIGDATACONFIGPATH + "/" + groupPath + "/" + paramName;
            log.debug("【系统配置】删除属性节点路径：{}", paramPath);
            Integer children = systemConfigServer.countChildren(paramPath);
            //如果没有子节点就删除
            if (children == 0) {
                systemConfigServer.delNode(paramPath);
            }
            i = paramName.lastIndexOf("/");

        }
        return BigdataResult.ok();
    }

    /**
     * 删除菜单
     *
     * @param sysConfigVo
     * @return
     */
    @DeleteMapping("/config/menu")
    public BigdataResult delGroupByName(SysConfigVo sysConfigVo) {
        String sysName = sysConfigVo.getSysName();
        String groupName = sysConfigVo.getGroupName();
        log.debug("【系统配置】delParamByName方法入参：{}", sysConfigVo);
        String s = sysName;
        if (!StringUtils.isEmpty(groupName)) {
            s = sysName + "/" + groupName;
        }
        String menuPath = s.replaceAll("\\.", "/");

        String paramGroupPath = Constants.BIGDATACONFIGPATH + "/" + menuPath;
        log.debug("【系统配置】删除配置节点分组路径：{}", paramGroupPath);
        systemConfigServer.delNode(paramGroupPath);
        systemConfigServer.delEmptyNode(paramGroupPath);
        String paramMenuPath = Constants.BIGDATACONFIGMENU + "/" + s;
        log.debug("【系统配置】删除菜单节点分组路径：{}", paramMenuPath);
        systemConfigServer.delNode(paramMenuPath);
        systemConfigServer.delEmptyNode(paramMenuPath);
        return BigdataResult.ok();
    }

    /**
     * 获取菜单分组的详细配置信息
     *
     * @param groupPath
     * @return
     */
    @RequestMapping("/config/menu/group")
    public BigdataResult getGroupConfig(String groupPath) {
        //去除开头的/
        int i = groupPath.substring(1).indexOf("/") + 2;
        Properties propFromZK = systemConfigServer.getPropFromZK(groupPath);
        List<SysConfigVo> sysConfigVos = new ArrayList<>();
        SysConfigVo sysConfigVo;
        for (Object o : propFromZK.keySet()) {
            sysConfigVo = new SysConfigVo();
            //拼装属性的名称 将/转化为.
            String groupName = groupPath.substring(i);
            log.debug("【系统配置】分组名称：{}", groupName);
            sysConfigVo.setParamName(o.toString());
            //获取的参数值
            String value = propFromZK.getProperty(o.toString());
            //判断是否是动态参数
            boolean dynamic = value.startsWith(DynamicConstant.DYNAMIC_PREFIX);
            sysConfigVo.setDynamic(dynamic);
            //处理动态参数值
            value= dynamic?value.substring(DynamicConstant.DYNAMIC_PREFIX.length()):value;
            sysConfigVo.setParamValue(value);
            sysConfigVos.add(sysConfigVo);
            sysConfigVo.setGroupName(groupName.replaceAll("/", "\\."));
        }
        return BigdataResult.ok(sysConfigVos);
    }

    /**
     * 保存系统配置信息
     *
     * @param vo
     * @return
     */
    @PostMapping("/config/menu")
    public BigdataResult saveGroupNode(@RequestBody SysConfigVo vo) {
        String sysName = vo.getSysName().trim();
        String groupName = vo.getGroupName().trim();
        String path = vo.getGroupPath().trim();
        String paramName = vo.getParamName().trim();
        log.debug("【系统配置】添加配置：{}", vo);
        String sysMenuPath = Constants.BIGDATACONFIGMENU + "/" + sysName;
        if (!systemConfigServer.exist(sysMenuPath)) {
            log.debug("【系统配置】创建菜单系统路径：{}", sysMenuPath);
            systemConfigServer.createNode(sysMenuPath, true);
        }
        if (!StringUtils.isEmpty(groupName)) {
            //添加菜单
            String s = Constants.BIGDATACONFIGMENU + "/" + sysName + "/" + groupName;
            if (!systemConfigServer.exist(s)) {
                log.debug("【系统配置】创建菜单分组路径：{}", s);
                systemConfigServer.put(s, path);
            }
            //生成正真存放配置数据的节点
            String groupDataPath = Constants.BIGDATACONFIGPATH + path;
            if (!systemConfigServer.exist(groupDataPath)) {
                log.debug("【系统配置】创建配置前缀路径：{}", groupDataPath);
                systemConfigServer.createNode(groupDataPath, true);
            }
            if (!StringUtils.isEmpty(paramName)) {
                //存储数据到zk
                log.debug("【系统配置】将配置值{}写入到配置路径：{},是否是动态参数：{}", vo.getParamValue(), vo.getParamPath(),vo.getDynamic());
                systemConfigServer.put(vo);
            }
        }

        return BigdataResult.ok();
    }

    /**
     * 修改系统配置信息
     *
     * @param vo
     * @return
     */
    @PostMapping("/config/param")
    public BigdataResult modifyParamValue(@RequestBody SysConfigVo vo) {
        String paramValue = vo.getParamValue().trim();
        //判断是否是动态参数
        paramValue = vo.getDynamic()?DynamicConstant.DYNAMIC_PREFIX+paramValue: paramValue;
        String paramPath = vo.getParamPath();
        log.debug("【系统配置】添加配置：{}", vo);

        if (!systemConfigServer.exist(paramPath)) {
            throw new RuntimeException("【系统配置】系统配置节点" + paramPath + "不存在");
        }

        if (!StringUtils.isEmpty(paramValue)) {
            //存储数据到zk
            log.debug("【系统配置】将配置值{}写入到配置路径：{}", vo.getParamValue(), paramPath);
            systemConfigServer.writeDate(paramPath, paramValue);
        }


        return BigdataResult.ok();
    }


    /**
     * 获取系统配置菜单目录
     *
     * @return
     */
    @RequestMapping("/config/menu")
    public BigdataResult getSysMenu() {
        Map<String, MenuSysVo> menuSysVos = zkParamsService.getSysParamMenu();
        return BigdataResult.ok(menuSysVos);
    }


}
