package com.chezhibao.bigdata.datax.service;

import com.alibaba.fastjson.JSONObject;
import com.chezhibao.bigdata.datax.pojo.DataxConfig;
import com.chezhibao.bigdata.datax.vo.DataXSearchVO;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/4/26
 * Created by WangCongJun on 2018/4/26.
 */
public interface DataxService {
    /**
     * 存储任务信息
     * @param jobInfo
     * @param readerData
     * @param writerData
     * @return
     */
    String saveTaskInfo(JSONObject jobInfo, JSONObject readerData, JSONObject writerData);

    /**
     * 获取指定分页的任务信息
     * @param page
     * @param size
     * @return
     */
    List<Map<String,Object>> getTasks(Integer page, Integer size);

    List<Map<String,Object>> getTasks(DataXSearchVO dataXSearchVO);

    /**
     * 获取总数
     * @return
     */
    Integer count();
    Integer count(DataXSearchVO dataXSearchVO);

    /**
     * 根据id删除任务
     * @param id
     * @return
     */
    Boolean deleteById(Integer id);

    /**
     * 根据id获取job信息
     * @param id
     * @return
     */
    DataxConfig getJobInfo(Integer id);

    /**
     * 切换任务状态
     * @param id
     * @param status
     * @return
     */
    Boolean switchStatus(Integer id, Boolean status);

    /**
     * 根据获取datax的执行信息
     * @param jobName
     * @return
     */
    DataxConfig getJobInfoByJobName(String jobName);
}
