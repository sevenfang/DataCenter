package com.chezhibao.bigdata.dbms.server.download;

import com.chezhibao.bigdata.dbms.server.download.bo.TaskInfoBO;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/12.
 */
public interface TaskManageService {
    /**
     * 取消任务
     * @param taskInfoBo
     * @return
     */
    Boolean cancel(TaskInfoBO taskInfoBo);

    /**
     * 创建任务
     * @param taskInfoBo
     * @return
     */
    Boolean create(TaskInfoBO taskInfoBo);



    /**
     * 通过userId查询对应的任务信息
     * @param userId
     * @return
     */
    List<LinkedHashMap<String,Object>> getTaskByUserId(Integer userId);
}
