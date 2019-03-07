package com.chezhibao.bigdata.dbms.server.download.service.impl;

import com.chezhibao.bigdata.dbms.server.download.DataExportService;
import com.chezhibao.bigdata.dbms.server.download.bo.TaskInfoBO;
import com.chezhibao.bigdata.dbms.server.download.config.TaskConfig;
import com.chezhibao.bigdata.dbms.server.download.export.PrestoDataExportService;
import com.chezhibao.bigdata.dbms.server.download.service.DownloadTaskService;
import com.chezhibao.bigdata.dbms.server.download.service.TaskQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/12.
 */
@Service
@Slf4j
public class TaskQueueServiceImpl implements TaskQueueService {
    private DownloadTaskService downloadTaskService;
    private ThreadPoolExecutor taskPool;
    private ArrayBlockingQueue<Runnable> taskQueue;
    private TaskConfig taskConfig;
    private PrestoDataExportService prestoDataExportService;

    public TaskQueueServiceImpl(DownloadTaskService downloadTaskService,
                                TaskConfig taskConfig,
                                PrestoDataExportService prestoDataExportService) {
        this.downloadTaskService = downloadTaskService;
        this.taskConfig = taskConfig;
        this.prestoDataExportService = prestoDataExportService;
        //检查路径是否创建
        String path = taskConfig.getPath();
        File file = new File(path);
        if(!file.exists()){
            log.info("【数据查询服务】正在创建download文件夹！！！");
            boolean mkdirs = file.mkdirs();
        }
        taskQueue = new ArrayBlockingQueue<>(taskConfig.getMaxWait());
        taskPool = new ThreadPoolExecutor(  taskConfig.getCoreSize(),
                                            taskConfig.getMaxSize(),
                                            0L,
                                            TimeUnit.MILLISECONDS,
                                            taskQueue,
                                            new TaskThreadFactory());
    }

    @Override
    public Boolean cancel(TaskInfoBO taskInfoBo) {
        Boolean result = true;
        //1、删除数据中的任务
        Boolean cancel = downloadTaskService.cancel(taskInfoBo);
        if(!cancel){
            log.error("【数据查询服务】删除数据库中的任务失败！！{}",taskInfoBo);
            result=false;
        }
        //2、删除队列中的任务
        boolean remove = taskQueue.remove(taskInfoBo);
        if(!remove){
            log.error("【数据查询服务】删除等待队列中的任务失败！！{}",taskInfoBo);
            result=false;
        }
        //3、取消正在运行的任务
        PrestoDataExportService.STATEMENT_CONCURRENT_HASH_MAP.remove(taskInfoBo.getTaskName());
        return result;
    }

    @Override
    public Boolean create(TaskInfoBO taskInfoBo) {
        if (taskQueue.size() == taskConfig.getMaxWait()) {
            log.error("【数据服务平台】下载服务队列已满！！");
            return false;
        }
        //任务信息先入库
        Boolean aBoolean = downloadTaskService.create(taskInfoBo);
        if(!aBoolean){
            log.error("【数据查询服务】任务入库失败！{}",taskInfoBo);
            return false;
        }
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.setDataExportService(prestoDataExportService);
        downloadTask.setDownloadTaskService(downloadTaskService);
        downloadTask.setFileName(taskInfoBo.getTaskName());
        //添加任务到队列
        taskPool.execute(downloadTask);
        return true;
    }

    @Override
    public List<LinkedHashMap<String, Object>> getTaskByUserId(Integer userId) {
        return null;
    }


    /**
     * 任务创建工厂
     */
    class TaskThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {

            if (r instanceof DownloadTask) {
                DownloadTask t = (DownloadTask) r;
                String name = taskConfig.getName(t.getFileName());
                return new Thread(t, name);
            }
            return new Thread(r);
        }
    }

    public class DownloadTask implements Runnable {

        private String fileName;

        private DataExportService dataExportService;

        private DownloadTaskService downloadTaskService;

        @Override
        public void run() {
            downloadTaskService.invoke(fileName);
            dataExportService.export(fileName);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof DownloadTask) {
                DownloadTask that = (DownloadTask) obj;
                return this.fileName.equals(that.fileName);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return (this.fileName.hashCode() + 10) * 9;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public void setDataExportService(DataExportService dataExportService) {
            this.dataExportService = dataExportService;
        }

        public void setDownloadTaskService(DownloadTaskService downloadTaskService) {
            this.downloadTaskService = downloadTaskService;
        }

        public String getFileName() {
            return fileName;
        }

        public DataExportService getDataExportService() {
            return dataExportService;
        }

        public DownloadTaskService getDownloadTaskService() {
            return downloadTaskService;
        }
    }
}
