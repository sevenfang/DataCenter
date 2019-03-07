package com.chezhibao.bigdata.datax.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.datax.constant.Constant;
import com.chezhibao.bigdata.datax.dto.DataxConfigDTO;
import com.chezhibao.bigdata.datax.pojo.DataxConfig;
import com.chezhibao.bigdata.datax.pojo.DataxConfigDS;
import com.chezhibao.bigdata.datax.service.DataxConfigDSService;
import com.chezhibao.bigdata.datax.service.DataxService;
import com.chezhibao.bigdata.datax.vo.DataXSearchVO;
import com.chezhibao.bigdata.datax.vo.DataxConfigDSVO;
import com.chezhibao.bigdata.datax.vo.DataxVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/whtc/datax")
@Slf4j
public class DataxControler {


    private DataxService dataxService;

    private DataxConfigDSService  dataxConfigDSService;

    public DataxControler(DataxService dataxService, DataxConfigDSService dataxConfigDSService) {
        this.dataxService = dataxService;
        this.dataxConfigDSService = dataxConfigDSService;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public BigdataResult createTask(String readerData, String writerData, String jobInfo){
        log.debug("readerData:{};writerData:{};jobInfo:{}",readerData,writerData,jobInfo);
        JSONObject job = JSON.parseObject(jobInfo);
        JSONObject writer = JSON.parseObject(writerData);
        JSONObject reader = JSON.parseObject(readerData);

        String result = dataxService.saveTaskInfo(job, reader, writer);
        if(!Constant.SUCCESS.equals(result)){
            return BigdataResult.build(100301,result);
        }
        return BigdataResult.ok();
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public BigdataResult getTasks(Integer page,Integer size){
        log.debug("【DataX页面配置】获取任务信息：页码 {} 、条数 {}",page,size);
        List<Map<String, Object>> tasks = dataxService.getTasks(page, size);
        Integer count = dataxService.count();
        return  BigdataResult.build(200,count+"",tasks);
    }
    @RequestMapping(value = "/get",method = RequestMethod.POST)
    public BigdataResult getTasks(@RequestBody DataXSearchVO dataXSearchVO){
        log.debug("【DataX页面配置】获取任务信息：dataXSearchVO {}",dataXSearchVO);
        List<Map<String, Object>> tasks = dataxService.getTasks(dataXSearchVO);
        Integer count = dataxService.count(dataXSearchVO);
        return  BigdataResult.build(200,count+"",tasks);
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public BigdataResult deleteTask(@PathVariable("id") Integer id){
        log.debug("【DataX页面配置】删除任务的ID：{}",id);
        Boolean aBoolean = dataxService.deleteById(id);
        if(aBoolean){
            return  BigdataResult.ok();
        }
        return  BigdataResult.build(10003,"删除失败！");
    }

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    public BigdataResult queryById(@PathVariable("id") Integer id){
        log.debug("【DataX页面配置】查询ID为{}的任务信息",id);
        DataxConfig jobInfo = dataxService.getJobInfo(id);
        if(ObjectUtils.isEmpty(jobInfo)){
            //TODO 异常处理
        }
        DataxVo dataxVo = DataxConfigDTO.trans2DataxVo(jobInfo);
        return BigdataResult.ok(dataxVo);
    }

    @RequestMapping(value = "/switch",method = RequestMethod.POST)
    public BigdataResult switchStatus( Integer id, Boolean status){
        if(status){
            log.debug("【DataX页面配置】ID为{}的任务当前状态：打开",id);
        }else{
            log.debug("【DataX页面配置】ID为{}的任务当前状态：关闭",id);
        }

        Boolean aBoolean = dataxService.switchStatus(id, status);

        return BigdataResult.ok(aBoolean);
    }

    @RequestMapping(value = "/get/data/{jobName}",method = RequestMethod.GET)
    public String queryJsonDataByName(@PathVariable("jobName") String jobName){
        DataxConfig dataxConfig = dataxService.getJobInfoByJobName(jobName);
        if(dataxConfig.getStatus()==0){
            return JSON.toJSONString(BigdataResult.build(130001,"任务停止配置为关闭！"));
        }
        return dataxConfig.getData();
    }

    @RequestMapping(value = "/get/datasource",method = RequestMethod.POST)
    public BigdataResult getDs(@RequestBody DataxConfigDS dataxConfigDS){
        List<Map<String, Object>> ds = dataxConfigDSService.getDS(dataxConfigDS);
        List<DataxConfigDSVO> dataxConfigDSVOS = ObjectCommonUtils.map2Object(ds, DataxConfigDSVO.class);
        return BigdataResult.ok(dataxConfigDSVOS);
    }

    @RequestMapping(value = "/datasource",method = RequestMethod.POST)
    public BigdataResult saveDs(@RequestBody DataxConfigDS dataxConfigDS){
        Boolean aBoolean = dataxConfigDSService.saveDS(dataxConfigDS);
        return BigdataResult.ok(aBoolean);
    }

    @RequestMapping(value = "/datasource/update",method = RequestMethod.POST)
    public BigdataResult updateDs(@RequestBody DataxConfigDS dataxConfigDS){
        Boolean aBoolean = dataxConfigDSService.updateDS(dataxConfigDS);
        return BigdataResult.ok(aBoolean);
    }

    @RequestMapping(value = "/datasource/{id}",method = RequestMethod.DELETE)
    public BigdataResult delDs(@PathVariable("id") Integer id){
        DataxConfigDS dataxConfigDS = new DataxConfigDS();
        dataxConfigDS.setId(id);
        dataxConfigDSService.delDS(dataxConfigDS);
        return BigdataResult.ok();
    }
}
