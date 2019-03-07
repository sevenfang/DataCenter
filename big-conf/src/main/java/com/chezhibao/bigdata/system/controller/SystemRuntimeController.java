package com.chezhibao.bigdata.system.controller;

import com.chezhibao.bigdata.common.pojo.AppBasicInfo;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.system.constants.Constants;
import com.chezhibao.bigdata.system.pojo.ZkNode;
import com.chezhibao.bigdata.system.service.SystemConfigServer;
import com.chezhibao.bigdata.system.service.SystemRuntimeStatusService;
import com.chezhibao.bigdata.system.vo.SysInfoVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/8.
 */
@RestController
@RequestMapping("/sys")
public class SystemRuntimeController {
    private SystemRuntimeStatusService systemRuntimeStatusService;
    private SystemConfigServer systemConfigServer;

    public SystemRuntimeController(SystemRuntimeStatusService systemRuntimeStatusService,
                                   SystemConfigServer systemConfigServer) {
        this.systemRuntimeStatusService = systemRuntimeStatusService;
        this.systemConfigServer = systemConfigServer;
    }

    /**
     * 获取所有的系统的名称
     * @return
     */
    @RequestMapping("/sys/name")
    public BigdataResult getSystems(){
        List<ZkNode> childrenName = systemConfigServer.getChildren(Constants.BIGDATACONFIGMENU);
        List<SysInfoVO> sysInfoVOS = new ArrayList<>();
        SysInfoVO sysInfoVO;
        for (ZkNode sysNode : childrenName) {
            sysInfoVO = new SysInfoVO();
            sysInfoVO.setSysName(sysNode.getName());
            sysInfoVOS.add(sysInfoVO);
        }
        return BigdataResult.ok(sysInfoVOS);
    }

    /**
     * 根据系统名称获取系统的运行信息
     * @param sysName
     * @return
     */
    @RequestMapping("/sys/info/{sysName}")
    public BigdataResult getSystemRuntimeInfo(@PathVariable("sysName") String sysName){
        List<AppBasicInfo> appBasicInfos = systemRuntimeStatusService.getSystemInfoBySystemName(sysName);
        return BigdataResult.ok(appBasicInfos);
    }
}
