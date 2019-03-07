package com.chezhibao.bigdata.datax.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.chezhibao.bigdata.common.message.MessageObject;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.datax.constant.Constant;
import com.chezhibao.bigdata.datax.builder.JobBuilder;
import com.chezhibao.bigdata.datax.dao.DataxDao;
import com.chezhibao.bigdata.datax.enums.DataxEnums;
import com.chezhibao.bigdata.datax.exception.DataxException;
import com.chezhibao.bigdata.datax.pojo.DataxConfig;
import com.chezhibao.bigdata.datax.service.DataxService;
import com.chezhibao.bigdata.datax.vo.DataXSearchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/4/26
 * Created by WangCongJun on 2018/4/26.
 */
@Service
@Slf4j
public class DataxServiceImpl implements DataxService {
    @Resource(name = "dataxDaoImpl")
    private DataxDao dataxDao;

    @Autowired
    private JobBuilder jobBuilder;

    @Override
    public String saveTaskInfo(JSONObject jobInfo, JSONObject readerData, JSONObject writerData) {
        String jobName = jobInfo.getString(Constant.JOBNAME);
        Map<String,Object> params = new HashMap<>();
        params.put("jobName",jobName);
        List<Map<String, Object>> jobInfo1 = dataxDao.getJobInfo(params);
        Integer id = jobInfo.getInteger(Constant.ID);
        //如果是创建任务，则任务名不能存在
        if(StringUtils.isEmpty(id)){
            if(!ObjectUtils.isEmpty(jobInfo1)){
                return "任务名称已经存在！！";
            }
        }
        String jobDesc = jobInfo.getString(Constant.JOBDESC);
        String inputType = jobInfo.getString("input_type");
        log.debug("【DATAX任务存储】数据输入类型：{}",inputType);

        String outputType = jobInfo.getString("output_type");
        log.debug("【DATAX任务存储】数据输出类型：{}",outputType);
        //创建任务
        MessageObject build = jobBuilder.build(readerData, writerData);
        String data = build.toString();
        log.debug("【DATAX任务存储】任务信息：{}",data);
        DataxConfig dataxConfig = new DataxConfig();
        dataxConfig.setId(id);
        dataxConfig.setJobDesc(jobDesc);
        dataxConfig.setJobName(jobName);
        dataxConfig.setData(data);
        Date date = new Date();
        dataxConfig.setCreatedTime(date);
        dataxConfig.setUpdatedTime(date);
        dataxDao.saveOrUpdate(dataxConfig);
        return Constant.SUCCESS;
    }


    @Override
    public List<Map<String, Object>> getTasks(Integer page, Integer size) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("page",page);
        params.put("size",size);
        return dataxDao.getJobInfo(params);
    }

    @Override
    public List<Map<String, Object>> getTasks(DataXSearchVO dataXSearchVO) {
        Map<String, Object> map = ObjectCommonUtils.object2Map(dataXSearchVO);
        return dataxDao.getJobInfo(map);
    }

    @Override
    public Integer count() {
        Integer count = dataxDao.count(new HashMap<String,Object>());
        return count;
    }

    @Override
    public Integer count(DataXSearchVO dataXSearchVO) {
        Map<String, Object> params = ObjectCommonUtils.object2Map(dataXSearchVO);
        Integer count = dataxDao.count(params);
        return count;
    }

    @Override
    public Boolean deleteById(Integer id) {
        Map<String,Object> params = new HashMap<>();
        params.put("id",id);
        return dataxDao.delete(params);
    }

    @Override
    public DataxConfig getJobInfo(Integer id) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("id",id);
        List<Map<String, Object>> jobInfos = dataxDao.getJobInfo(params);
        if(ObjectUtils.isEmpty(jobInfos)){
            return null;
        }
        Map<String, Object> map = jobInfos.get(0);
        return TypeUtils.castToJavaBean(map, DataxConfig.class);
    }

    @Override
    public Boolean switchStatus(Integer id, Boolean status) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("id",id);
        Integer s = 1;
        //TODO 检查前台传来的是否和数据库一致
        if(status){
            s=0;
        }
        params.put("status",s);
        try {
            if (dataxDao.update(params)) {
                return !status;
            } else {
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public DataxConfig getJobInfoByJobName(String jobName) {
        Map<String,Object> params = new HashMap<>();
        params.put("jobName",jobName);
        List<Map<String, Object>> jobInfo = dataxDao.getJobInfo(params);
        if(ObjectUtils.isEmpty(jobInfo)){
            throw new DataxException(DataxEnums.JOB_NO_EXIST);
        }
        if(jobInfo.size()!=1){
            throw new DataxException(DataxEnums.MULTI_JOB_ERROR);
        }
        DataxConfig dataxConfig = TypeUtils.castToJavaBean(jobInfo.get(0), DataxConfig.class);
        //转换数据中的链接地址
        String data = dataxConfig.getData();
        JSONObject jsonData = JSON.parseObject(data);
        JSONObject job = jsonData.getJSONObject(Constant.JOB);
        JSONArray content = job.getJSONArray(Constant.CONTENT);
        JSONObject jsonObject = content.getJSONObject(0);
        Map reader = jsonObject.getJSONObject(Constant.READER);
        Map newReader = jobBuilder.translateReaderDatasource(reader);
        jsonObject.put(Constant.READER,newReader);
        Map writer = jsonObject.getJSONObject(Constant.WRITER);
        Map newWriter = jobBuilder.translateWriterDatasource(writer);
        jsonObject.put(Constant.WRITER,newWriter);
        String s = jsonData.toJSONString();
        dataxConfig.setData(s);
        return dataxConfig;
    }
}
