package com.chezhibao.bigdata.system.zk;

import com.chezhibao.bigdata.system.constants.Constants;
import com.chezhibao.bigdata.system.pojo.ZkNode;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.Properties;

/**
 * @author WangCongJun
 * @date 2018/5/17
 * Created by WangCongJun on 2018/5/17.
 */
@Slf4j
public class ZkUtils {

    public static Properties loopTraversalgetZnode(ZkClient client, String path) {
        Properties properties = new Properties();

        if (!client.exists(path)) {
            log.error("【配置系统】循环遍历{}不存在！", path);
            return properties;
        }
        int i = client.countChildren(path);
        if (i == 0) {
            log.error("【配置系统】循环遍历{}子节点为空！", path);
            return properties;
        }
        loopZnode(properties, client, path,path);
        log.debug("【配置系统】循环遍历{};结果：{}！",path, properties);
        return properties;
    }


    static void loopZnode(Properties properties, ZkClient client, String path,String rowPath) {
        int i = client.countChildren(path);
        if (i == 0) {
            String value = client.readData(path).toString();
            if(Constants.SLASH.equals(rowPath)) {
                path = path.substring(1).replaceAll("/", "\\.");
            }else{
                path = path.substring(rowPath.length()+1).replaceAll("/", "\\.");
            }
            properties.setProperty(path, value);
        } else {
            List<String> children = client.getChildren(path);
            for (String cp : children) {
                String p;
                if (Constants.SLASH.equals(path)) {
                    p = Constants.SLASH + cp;
                } else {
                    p = path + "/" + cp;
                }

                loopZnode(properties, client, p,rowPath);
            }
        }

    }

    /**
     * 获取该节点的路径
     * @param zkNode
     * @return
     */
    public static String getCurrentPath(ZkNode zkNode){
        log.debug("【系统管理】获取路径zknode：{}",zkNode);
        String parentPath = zkNode.getParentPath();
        String name = zkNode.getName();
        if(!parentPath.endsWith(Constants.SLASH)){
            parentPath = parentPath+Constants.SLASH;
        }
        if(!parentPath.startsWith(Constants.SLASH)){
            parentPath = Constants.SLASH+parentPath;
        }
        String path = parentPath + name;
        log.debug("【系统管理】获取路径结果：{}",path);
        return path;
    }

}
