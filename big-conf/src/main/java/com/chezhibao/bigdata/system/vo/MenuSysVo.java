package com.chezhibao.bigdata.system.vo;

import com.chezhibao.bigdata.common.pojo.AppBasicInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author WangCongJun
 * @date 2018/5/21
 * Created by WangCongJun on 2018/5/21.
 */
@Data
public class MenuSysVo implements Serializable {
    private String sysName;
    private List<MenuGroupVo> groups;
    /**
     * 系统状态，1表示在线 0表示不在线
     */
    private Integer status;

    private List<AppBasicInfo> apps;
}
