package com.chezhibao.bigdata.system.service;

import com.chezhibao.bigdata.system.pojo.ZkNode;
import com.chezhibao.bigdata.system.vo.MenuSysVo;

import java.util.Map;

/**
 * zk参数服务
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/23.
 */
public interface ZkParamsService {
    /**
     * 启动所有的zk动态参数监听功能
     * @return
     */
    Boolean initListener();

    Map<String, MenuSysVo> getSysParamMenu();
}
