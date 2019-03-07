package com.chezhibao.bigdata.dbms.server.download.service.impl;

import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.dbms.server.download.bo.TaskInfoBO;
import com.chezhibao.bigdata.dbms.server.download.config.TaskConfig;
import com.chezhibao.bigdata.dbms.server.download.dao.DownloadTaskDao;
import com.chezhibao.bigdata.dbms.server.download.service.DownloadTaskService;
import com.chezhibao.bigdata.dbms.server.enums.TaskStatusEnum;
import com.chezhibao.bigdata.dbms.server.utils.DBDataUtils;
import com.chezhibao.bigdata.template.ParamsBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/12.
 */
@Service
@Slf4j
public class DownloadTaskServiceImpl implements DownloadTaskService {
    private DownloadTaskDao  downloadTaskDao;
    private TaskConfig taskConfig;

    public DownloadTaskServiceImpl(DownloadTaskDao downloadTaskDao,
                                   TaskConfig taskConfig) {
        this.downloadTaskDao = downloadTaskDao;
        this.taskConfig = taskConfig;
    }

    @Override
    public Integer getTaskStatus(TaskInfoBO taskInfoBO) {
        Integer status = null;
        List<LinkedHashMap<String, Object>> query = downloadTaskDao.query(taskInfoBO);
        if(!ObjectUtils.isEmpty(query)) {
            LinkedHashMap<String, Object> stringObjectLinkedHashMap = query.get(0);
            Object s = stringObjectLinkedHashMap.get("status");
            if (s != null) {
                status=Integer.parseInt(s+"");
            }
        }
        return status;
    }

    @Override
    public Boolean complete(TaskInfoBO taskInfoBO) {
        Date date = new Date();
        taskInfoBO.setUpdatedTime(date);
        taskInfoBO.setStatus(TaskStatusEnum.COMPLETE.getCode());
        downloadTaskDao.updateTask(taskInfoBO);
        return true;
    }

    @Override
    public Boolean complete(String taskName) {
        TaskInfoBO taskInfoBO = new TaskInfoBO();
        taskInfoBO.setTaskName(taskName);
        return complete(taskInfoBO);
    }

    @Override
    public Boolean cancel(TaskInfoBO taskInfoBo) {
        log.info("【数据查询服务】删除任务：{}",taskInfoBo);
        List<LinkedHashMap<String, Object>> query = downloadTaskDao.query(taskInfoBo);
        if(ObjectUtils.isEmpty(query)){
            return true;
        }
        Boolean delete = downloadTaskDao.delete(taskInfoBo.getId());
        //删除对应的文件
        String s = taskConfig.getFilePath(taskInfoBo.getTaskName()) + ".csv";
        FileUtils.deleteQuietly(new File(s));
        return delete;
    }


    @Override
    public Boolean create(TaskInfoBO taskInfoBo) {
        Date date = new Date();
        taskInfoBo.setUpdatedTime(date);
        taskInfoBo.setCreatedTime(date);
        String sql = taskInfoBo.getSql();
        taskInfoBo.setSql(DBDataUtils.encode(sql));
        taskInfoBo.setStatus(TaskStatusEnum.CREATE.getCode());
        return  downloadTaskDao.save(taskInfoBo);
    }

    @Override
    public Boolean invoke(TaskInfoBO taskInfoBo) {
        Date date = new Date();
        taskInfoBo.setUpdatedTime(date);
        taskInfoBo.setStatus(TaskStatusEnum.INVOKE.getCode());
        return downloadTaskDao.updateTask(taskInfoBo);
    }

    @Override
    public Boolean invoke(String fileName) {
        TaskInfoBO taskInfoBo = getTaskByFileName(fileName);
        return invoke(taskInfoBo);
    }

    @Override
    public TaskInfoBO getTaskByFileName(String fileName) {
        TaskInfoBO taskInfoBo = new TaskInfoBO();
        taskInfoBo.setTaskName(fileName);
        List<LinkedHashMap<String, Object>> query = downloadTaskDao.query(taskInfoBo);
        if(ObjectUtils.isEmpty(query)){
            return null;
        }
        TaskInfoBO taskInfoBO = ObjectCommonUtils.map2Object(query.get(0), TaskInfoBO.class);
        taskInfoBO.setSql(DBDataUtils.decode(taskInfoBO.getSql()));
        return taskInfoBO;
    }

    @Override
    public List<LinkedHashMap<String, Object>> getTaskByUserId(Integer userId) {
        TaskInfoBO taskInfoBo = new TaskInfoBO();
        taskInfoBo.setUserId(userId);
        List<LinkedHashMap<String, Object>> query = downloadTaskDao.query(taskInfoBo);
        for(Map<String,Object> m: query){
            Object sql = m.get("sql");
            if(sql!=null){
                m.put("sql",DBDataUtils.decode(sql+""));
            }
        }
        return query;
    }

    @Override
    public TaskInfoBO getTaskById(Integer id) {
        TaskInfoBO taskInfoBo = new TaskInfoBO();
        taskInfoBo.setId(id);
        List<LinkedHashMap<String, Object>> query = downloadTaskDao.query(taskInfoBo);
        if(ObjectUtils.isEmpty(query)){
            return null;
        }
        return ObjectCommonUtils.map2Object(query.get(0),TaskInfoBO.class);
    }
}
