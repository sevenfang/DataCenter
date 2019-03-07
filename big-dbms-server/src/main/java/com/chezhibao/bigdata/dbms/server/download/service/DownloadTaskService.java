package com.chezhibao.bigdata.dbms.server.download.service;

import com.chezhibao.bigdata.dbms.server.download.TaskManageService;
import com.chezhibao.bigdata.dbms.server.download.bo.TaskInfoBO;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/12.
 */
public interface DownloadTaskService extends TaskManageService {
    /**
     * 获取任务的状态
     * @param taskInfoBO
     * @return
     */
    Integer getTaskStatus(TaskInfoBO taskInfoBO);

    /**
     * 任务完成
     * @param taskInfoBO
     * @return
     */
    Boolean complete(TaskInfoBO taskInfoBO);

    /**
     * 任务完成
     * @param taskName
     * @return
     */
    Boolean complete(String taskName);

    /**
     * 执行任务
     * @param taskInfoBo
     * @return
     */
    Boolean invoke(TaskInfoBO taskInfoBo);

    /**
     * 执行任务
     * @param fileName 其实就是taskName
     * @return
     */
    Boolean invoke(String fileName);

    /**
     * 根据文件路径名称获取
     * @param fileName 其实就是taskName
     * @return
     */
    TaskInfoBO getTaskByFileName(String fileName);

    /**
     * 根据id名称获取
     * @param id
     * @return
     */
    TaskInfoBO getTaskById(Integer id);


}
