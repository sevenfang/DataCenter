package com.chezhibao.bigdata.system.listener;

import com.chezhibao.bigdata.ConfigServerLogEnum;
import com.chezhibao.bigdata.common.constan.DynamicConstant;
import com.chezhibao.bigdata.common.log.LoggerUtils;
import com.chezhibao.bigdata.common.pojo.AppBasicInfo;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.system.service.SystemRuntimeStatusService;
import com.chezhibao.bigdata.system.vo.SysConfigVo;
import org.I0Itec.zkclient.IZkDataListener;
import org.slf4j.Logger;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * 动态参数的监听类，监听参数值是否变化
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/23.
 */
public class DynamicParameterListener implements IZkDataListener {

    private static final Logger DYNAMIC_LOGGER = LoggerUtils.Logger(ConfigServerLogEnum.DYNAMIC_PARAM_LOG);

    private SysConfigVo vo;

    private SystemRuntimeStatusService systemRuntimeStatusService;

    private RestTemplate restTemplate;

    public DynamicParameterListener(SysConfigVo vo) {
        this.vo = vo;
    }

    @Override
    public void handleDataChange(String dataPath, Object data) throws Exception {
        String sysName = vo .getSysName();
        List<AppBasicInfo> systemInfoBySystemName = systemRuntimeStatusService.getSystemInfoBySystemName(sysName);
        DYNAMIC_LOGGER.info("【系统配置】动态修改系统{}的参数路径:{}",sysName,dataPath);
        //判断data是否为空
        if(data == null){ return;}
        for(AppBasicInfo basicInfo: systemInfoBySystemName){
            //判断系统是否在线
            if(basicInfo.getStatus()==0){
                //系统不在线
                continue;
            }
            //请求更改参数

            request(basicInfo,data.toString());
        }
    }

    @Override
    public void handleDataDeleted(String dataPath) throws Exception {
    }

    private void request(AppBasicInfo basicInfo,String data) throws Exception{
        String paramName = vo.getGroupName()+"."+vo.getParamName();
        String paramValue = data.substring(DynamicConstant.DYNAMIC_PREFIX.length());
        DYNAMIC_LOGGER.info("【系统配置】动态修改系统{}的参数{}值为{}",vo.getSysName(),paramName,paramValue);
        //拼接URl
        String host = "http://"+basicInfo.getHost() + ":" + basicInfo.getPort();
        String url = host +DynamicConstant.DYNAMIC_REQUEST_PATH;
        DYNAMIC_LOGGER.info("【系统配置】动态修改系统参数的url:{}",url);
        //组装请求参数
        MultiValueMap<String , Object> request = new LinkedMultiValueMap<>();
        request.set(DynamicConstant.DYNAMIC_PARAM_NAME,paramName);
        request.set(DynamicConstant.DYNAMIC_PARAM_VALUE,paramValue);
        //发送请求
        BigdataResult result = restTemplate.postForObject(URI.create(url),request , BigdataResult.class);
        Integer status = result==null?0:result.getStatus();
        if(status ==200){
            return;
        }
        //不成功则等待100ms重试一次
        Thread.sleep(100);
        result = restTemplate.postForObject(URI.create(url),request , BigdataResult.class);
        status = result==null?0:result.getStatus();
        if(status ==200){
            //动态修改成功
            return;
        }
        //等待100ms重试一次还不成功抛出异常
        Thread.sleep(100);
        result = restTemplate.postForObject(URI.create(url),request , BigdataResult.class);
        status = result==null?0:result.getStatus();
        if(status ==200){
            //动态修改成功
            return;
        }
        DYNAMIC_LOGGER.error("【系统配置】动态参数修改失败：{}",basicInfo);
    }

    public void setSystemRuntimeStatusService(SystemRuntimeStatusService systemRuntimeStatusService) {
        this.systemRuntimeStatusService = systemRuntimeStatusService;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
