package com.chezhibao.bigdata.system.service;

import com.chezhibao.bigdata.system.pojo.ZkNode;
import com.chezhibao.bigdata.system.vo.SysConfigVo;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author WangCongJun
 * @date 2018/5/18
 * Created by WangCongJun on 2018/5/18.
 */
public interface SystemConfigServer {

    /**
     * 获取zk上的应用系统配置信息
     * @param appName
     * @return
     */
    Properties getPropFromZK(String appName);

    /**
     * 默认不是动态参数
     * 存储数据到ZK节点
     * @param path
     * @param value
     */
    void put(String path, String value);
    /**
     * 存储数据到ZK节点
     * 用对象来操作
     * @param sysConfigVo
     */
    void put(SysConfigVo sysConfigVo);

    /**
     * ZK节点是否存在
     * @param path
     */
    Boolean exist(String path);

    /**
     * 修改ZK节点数据
     * @param path
     * @param value
     */
    void updateNode(String path, String value);

    /**
     * 删除ZK节点数据
     * @param path
     */
    void delNode(String path);

    /**
     * 查找节点是否有空节点去除空节点
     * @param path
     */
    void delEmptyNode(String path);

    /**
     * 增加ZK节点
     * @param path
     */
    void createNode(String path, Boolean isPersist);


    /**
     * 获取该节点下的子节点
     * @param path
     * @return
     */
    List<ZkNode> getChildren(String path);

    /**
     * 获取该节点下的子节点
     * @param path
     * @return
     */
    Integer countChildren(String path);

    /**
     * 获取该节点系统和分组信息
     * @param path 部门路径
     * @return
     */
    List<Map<String, Map<String,String>>> getSysMenu(String path);

    /**
     * 向节点写入数据
     * @param path
     * @param data
     */
    void writeDate(String path,Object data);
}
