package com.chezhibao.bigdata.dbms.server.download.dao;

import com.chezhibao.bigdata.dbms.server.download.bo.TaskInfoBO;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/12.
 */
public interface DownloadTaskDao {
    /**
     * 删除指定的任务
     * @param id
     * @return
     */
    Boolean delete(Integer id);

    /**
     * 存储任务
     * @param taskInfoBO
     * @return
     */
    Boolean save(TaskInfoBO taskInfoBO);

    /**
     * 查询任务
     * @param taskInfoBO
     * @return
     */
    List<LinkedHashMap<String,Object>> query(TaskInfoBO taskInfoBO);

    /**
     * 通过Id更新任务
     * @param taskInfoBO
     * @return
     */
    Boolean updateTask(TaskInfoBO taskInfoBO);
}
