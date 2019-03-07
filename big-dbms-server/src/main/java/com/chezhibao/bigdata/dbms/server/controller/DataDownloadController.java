package com.chezhibao.bigdata.dbms.server.controller;

import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.dbms.server.auth.annotation.AuthManage;
import com.chezhibao.bigdata.dbms.server.download.bo.TaskInfoBO;
import com.chezhibao.bigdata.dbms.server.download.service.DataFileService;
import com.chezhibao.bigdata.dbms.server.download.service.DownloadTaskService;
import com.chezhibao.bigdata.dbms.server.download.service.TaskQueueService;
import com.chezhibao.bigdata.dbms.server.vo.DownloadTaskVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/13.
 */
@RestController
@RequestMapping("/dbms/server")
@Slf4j
public class DataDownloadController {

    @Autowired
    private TaskQueueService taskQueueService;

    @Autowired
    private DownloadTaskService downloadTaskService;

    @Autowired
    private DataFileService dataFileService;

    @PostMapping("/task")
    @AuthManage
    public BigdataResult createTask(@RequestBody DownloadTaskVO downloadTaskVO,@CookieValue("LOGIN_USER_ID") Integer userId){
        DownloadTaskVO.Operation operation = downloadTaskVO.getOperation();
        TaskInfoBO taskInfoBO = downloadTaskVO.getTaskInfoBO();
        taskInfoBO.setUserId(userId);
        switch (operation){
            case CREATE:
                TaskInfoBO taskByFileName = downloadTaskService.getTaskByFileName(taskInfoBO.getTaskName());
                if(taskByFileName!=null){
                    //任务名称存在
                    return BigdataResult.build(300,"任务名称存在！！");
                }
                log.info("【数据查询服务】创建新任务:{}",taskInfoBO);
                taskInfoBO.setStatus(0);
                taskQueueService.create(taskInfoBO);
                break;
            case DELETE:
                taskQueueService.cancel(taskInfoBO);
                log.info("【数据查询服务】删除任务:{}",taskInfoBO);
                break;
            case UPDATE:
                log.info("【数据查询服务】更新任务:{}",taskInfoBO);
                break;
            case DOWNLOAD:
                dataFileService.download(taskInfoBO);
                break;
                default:
        }
        return BigdataResult.ok();
    }
    @GetMapping("/task/{taskName}")
    @AuthManage
    public BigdataResult checkParam(@PathVariable String taskName) throws Exception{
        TaskInfoBO taskByFileName = downloadTaskService.getTaskByFileName(taskName);
        if(taskByFileName!=null){
            return BigdataResult.build(300,"");
        }
        return BigdataResult.ok();
    }

    @GetMapping("/task/info")
    @AuthManage
    public BigdataResult getTasksByUserId(@CookieValue("LOGIN_USER_ID") Integer userId){
        List<LinkedHashMap<String, Object>> taskByUserId = downloadTaskService.getTaskByUserId(userId);
        return BigdataResult.ok(taskByUserId);
    }
    @PostMapping("/task/delete/{id}")
    @AuthManage
    public BigdataResult deleteTask( @PathVariable Integer id,@CookieValue("LOGIN_USER_ID") Integer userId){

        TaskInfoBO taskById = downloadTaskService.getTaskById(id);
        if(taskById==null){
            return BigdataResult.build(3002,id+"不存在！");
        }
        dataFileService.deleteFile(taskById.getTaskName());
        TaskInfoBO taskInfoBO = new TaskInfoBO();
        taskInfoBO.setId(id);
        taskInfoBO.setUserId(userId);


        log.info("【数据查询服务】删除任务参数：{}",taskInfoBO);
        Boolean cancel = downloadTaskService.cancel(taskInfoBO);


        return BigdataResult.ok(cancel);
    }

}
