package com.chezhibao.bigdata.dbms.server.download.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/12.
 */
@ConfigurationProperties(prefix = "download.task")
@Setter
@Getter
@Component
public class TaskConfig {
    /**
     * 核心工作线程
     */
    private Integer coreSize;
    /**
     * 最大工作线程
     */
    private Integer maxSize;

    /**
     * 最大等待数
     */
    private Integer maxWait;
    /**
     * 文件保存路径
     */
    private String path;

    /**
     * 只是文件的路径  例如 /opt/app/..../testFile
     * 没有后缀名 需要自己加下
     * @param name
     * @return
     */
    public String getFilePath(String name){
        if (!path.endsWith("/")) {
            path = path+"/";
        }
        return path + name;
    }

    public String getName(String fileName){
        path=path.endsWith("/")?path:path+"/";
        return fileName.replace(path,"");
    }

}
